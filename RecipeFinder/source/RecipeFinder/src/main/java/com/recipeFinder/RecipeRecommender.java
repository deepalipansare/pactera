package com.recipeFinder;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.recipeFinder.common.CommonUtils;
import com.recipeFinder.common.Constants;
import com.recipeFinder.common.valueObject.FridgeItem;
import com.recipeFinder.common.valueObject.Recipe;
import com.recipeFinder.excepion.RecipeFinderException;

/**
 * This is main class that takes commandline input. 
 * Input - 
 * Filename containing fridge items
 * json string
 * Output - 
 * Recommended Recipe
 * @author Deepali
 *
 */
public class RecipeRecommender {
	
	private static Logger logger = Logger.getLogger(RecipeRecommender.class);
	
	public static void main(String[] args) {
		String fileName = null;
		String json = null;
		try(Scanner sc = new Scanner(System.in)){
			System.out.println("Please enter path of fridge csv file.");
			fileName = validInput(sc);
			System.out.println("Please enter json recipe data.");
			json = validInput(sc);
		}
		try{
			List<String> fridgeContent = CommonUtils.readCSVFile(fileName);
			
			List<FridgeItem> fridgeItems= RecipeFinderUtil.getAvailableFridgeItems(fridgeContent);
			
			List<Recipe> recipes = RecipeFinderUtil.getRecipes(json);
			
			Recipe recipe = RecipeFinderUtil.getRecommendedRecipe(recipes,fridgeItems);
			
			if(recipe != null){
				System.out.println(recipe.getName());
			}else{
				System.out.println(Constants.NO_RECIPE_FOUND_MESSAGE);
			}
			
		}catch(RecipeFinderException ex){
			logger.error("Error Occurred while Processing",ex);
		}
	}
	
	private static String validInput(Scanner sc){
		String input = null;
		StringBuffer sb = new StringBuffer();
		String nextLine = null;
		while ((nextLine= sc.nextLine()) != null
				&& nextLine.length()>0){
			sb.append(nextLine);
		}
		input = sb.toString();
		while(StringUtils.isBlank(input)){
			System.out.println("Please enter valid input");
			input = sc.nextLine();
		}
		return input.trim();
	}
}
