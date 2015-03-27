package com.recipeFinder.common;

import org.apache.commons.lang3.StringUtils;

/**
 * Constants used in the application
 * @author Deepali
 *
 */
public class Constants {
	public static enum Unit {
		OF("of"),GRAMS("grams"),ML("ml"),SLICES("slices");
		
		private String unit;
		
		Unit(String a_unit){
			unit = a_unit;
		}
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		
		public static Unit getUnitfromString(String text) {
		    if (!StringUtils.isBlank(text)) {
		      for (Unit a : Unit.values()) {
		        if (text.equalsIgnoreCase(a.getUnit())) {
		          return a;
		        }
		      }
		    }
		    return null;
		  }
	}
	
	public static String CSV_DELIMITER = ",";
	public static String DATE_FORMAT = "dd/MM/yyyy";
	public static String RECIPE_NAME = "name";
	public static String RECIPE_ITEM = "item";
	public static String RECIPE_AMOUNT = "amount";
	public static String RECIPE_UNIT = "unit";
	public static String RECIPE_INGREDIENTS = "ingredients";
	public static String NO_RECIPE_FOUND_MESSAGE = "Order Takeout";
}
