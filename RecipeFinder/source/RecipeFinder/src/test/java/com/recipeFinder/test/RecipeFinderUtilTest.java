package com.recipeFinder.test;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.recipeFinder.RecipeFinderUtil;
import com.recipeFinder.common.CommonUtils;
import com.recipeFinder.common.valueObject.FridgeItem;
import com.recipeFinder.common.valueObject.Recipe;
import com.recipeFinder.excepion.RecipeFinderException;

public class RecipeFinderUtilTest {
	
	private static String filename;
	private static int validFridgeItemSize;
	private static int noOfRecipes;
	private static List<String> fileData;
	private static String json;
	private static String recommendedRecipe;
	
	@BeforeClass
	public static void init() throws RecipeFinderException{
		filename="C:\\Data\\Work\\pactera\\input\\fridge.csv";
		validFridgeItemSize=3;
		noOfRecipes=2;
		fileData = CommonUtils.readCSVFile(filename);
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("{\"name\": \"grilled cheese on toast\",");
		sb.append("\"ingredients\": [");
		sb.append("{ \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},");
		sb.append("{ \"item\":\"cheese\", \"amount\":\"2\", \"unit\":\"slices\"}]},");
		sb.append("{\"name\": \"salad sandwich\",");
		sb.append("\"ingredients\": [");
		sb.append("{ \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},");
		sb.append("{ \"item\":\"mixed salad\", \"amount\":\"100\", \"unit\":\"grams\"}]}");
		sb.append("]");
		json = sb.toString();
		recommendedRecipe = "salad sandwich";
	}
	
	@Test
	public void getAvailableFridgeItems() throws RecipeFinderException{
		List<FridgeItem> fridgeItems= RecipeFinderUtil.getAvailableFridgeItems(fileData);
		
		assertEquals(validFridgeItemSize, fridgeItems.size());
	}
	
	@Test
	public void getRecipes() throws RecipeFinderException{
		List<Recipe> recipes = RecipeFinderUtil.getRecipes(json);
		
		assertEquals(noOfRecipes, recipes.size());
	}
	
	@Test
	public void getRecommendedRecipe() throws RecipeFinderException{
		List<FridgeItem> fridgeItems= RecipeFinderUtil.getAvailableFridgeItems(fileData);
		
		List<Recipe> recipes = RecipeFinderUtil.getRecipes(json);
		
		Recipe recipe = RecipeFinderUtil.getRecommendedRecipe(recipes, fridgeItems);
		
		assertEquals(recommendedRecipe, recipe.getName());
	}
}
