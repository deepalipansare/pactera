package com.recipeFinder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.recipeFinder.common.Constants;

import static com.recipeFinder.common.Constants.Unit;

import com.recipeFinder.common.valueObject.FridgeItem;
import com.recipeFinder.common.valueObject.Item;
import com.recipeFinder.common.valueObject.Recipe;
import com.recipeFinder.excepion.RecipeFinderException;

/**
 * This class provides application specific utility methods.
 * @author Deepali
 *
 */
public class RecipeFinderUtil {
	/**
	 * The method iterates over fridge contents 
	 * and returns the fridge items that are not yet expired.
	 * @param fridgeContent
	 * @return
	 * @throws RecipeFinderException
	 */
	public static List<FridgeItem> getAvailableFridgeItems(List<String> fridgeContent) 
			throws RecipeFinderException{
		try{
			List<FridgeItem> fridgeItems = new ArrayList<FridgeItem>();
			Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
			
			if(CollectionUtils.isEmpty(fridgeContent)){
				throw new RecipeFinderException("Fridge is Empty.");
			}
			for(String line : fridgeContent){
				String[] tokens = line.split(Constants.CSV_DELIMITER);
				if(!validateFridgeItems(tokens)){
					throw new RecipeFinderException("One or more data from Fridge CSV is missing");
				}
				FridgeItem item = new FridgeItem();
				item.setName(tokens[0]);
				item.setAmount(NumberUtils.toInt(tokens[1]));
				item.setUnit(Unit.getUnitfromString(tokens[2]));
				item.setUseBy(DateUtils.parseDate(tokens[3].trim(),
						Constants.DATE_FORMAT));
				
				if(!today.after((item.getUseBy()))
						&& item.getUnit() != null
						&& item.getAmount() > 0){
					fridgeItems.add(item);
				}
			}
			return fridgeItems;
		}catch(ParseException pEx){
			throw new RecipeFinderException("Error reading date from fridge csv.",pEx);
		}
	}
	
	/**
	 * The method parses json string to return recipes
	 * @param jsonData
	 * @return
	 * @throws RecipeFinderException
	 */
	public static List<Recipe> getRecipes(String jsonData) 
	throws RecipeFinderException{
		List<Recipe> recipes = new ArrayList<Recipe>();
		JSONParser parser=new JSONParser();
		try{
			Object rootObj = parser.parse(jsonData);
			JSONArray array = (JSONArray)rootObj;
			for(Object obj: array){
				Recipe recipe = new Recipe();
				List<Item> recipeItems = new ArrayList<Item>();
				recipe.setIngredients(recipeItems);
				JSONObject jsonObj = (JSONObject)obj;
				
				recipe.setName((String)jsonObj.get(Constants.RECIPE_NAME));
				
				JSONArray jsonArr2 = (JSONArray)jsonObj.get(Constants.RECIPE_INGREDIENTS);
				for(Object obj2:jsonArr2){
					Item item = new Item();
					JSONObject jsonObj2 = (JSONObject)obj2;
					item.setName((String)jsonObj2.get(Constants.RECIPE_ITEM));
					String amount = (String)jsonObj2.get(Constants.RECIPE_AMOUNT);
					item.setAmount(new Integer(amount));
					Unit itemUnit = Unit.getUnitfromString((String)jsonObj2.get(Constants.RECIPE_UNIT));
					item.setUnit(itemUnit);
					recipeItems.add(item);
				}
				recipes.add(recipe);
			}
			return recipes;
		}catch(org.json.simple.parser.ParseException pEx){
			throw new RecipeFinderException("Unable to parse JSON string",pEx);
		}
	}
	
	/**
	 * The method checks available fridge items and recipes to come up with recommended recipe
	 * @param recipes
	 * @param fridgeItems
	 * @return
	 */
	public static Recipe getRecommendedRecipe(List<Recipe> recipes, List<FridgeItem> fridgeItems){
		Recipe recommendedRecipe = null;
		Date minUseBy = null;
		for(Recipe recipe:recipes){
			List<Item> ingredients = recipe.getIngredients();
			Date recipeMinUseBy = null;
			boolean itemExists = true;
			for(Item item : ingredients){
				FridgeItem fr = getItemFromFridge(item, fridgeItems);
				if(fr!=null){
					itemExists = true;
					if(recipeMinUseBy == null) recipeMinUseBy = fr.getUseBy();
					else if(recipeMinUseBy.after(fr.getUseBy())){
						recipeMinUseBy = fr.getUseBy();
					}
					continue;
				}else{
					itemExists = false;
					break;
				}
			}
			if(itemExists){
				if(minUseBy == null || minUseBy.after(recipeMinUseBy)) {
					minUseBy = recipeMinUseBy;
					recommendedRecipe = recipe;
				}
			}
		}
		
		
		return recommendedRecipe;
	}
	
	private static boolean validateFridgeItems(String[] tokens){
		if(	tokens.length < 4
				|| StringUtils.isBlank(tokens[0])
				|| StringUtils.isBlank(tokens[1])
				|| StringUtils.isBlank(tokens[2])
				|| StringUtils.isBlank(tokens[3])
				){
			return false;
		}
		return true;
	}
	
	private static FridgeItem getItemFromFridge(Item item, List<FridgeItem> fridgeItems){
		for(FridgeItem fridgeItem: fridgeItems){
			if (item.getName().equals(fridgeItem.getName())
					&& item.getAmount() < fridgeItem.getAmount()
					&& item.getUnit().equals(fridgeItem.getUnit())
					)
				return fridgeItem;
		}
		return null;
	}
}