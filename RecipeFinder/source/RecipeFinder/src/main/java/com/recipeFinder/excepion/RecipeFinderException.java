package com.recipeFinder.excepion;

public class RecipeFinderException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public RecipeFinderException(){
		super();
	}
	
	public RecipeFinderException(Exception ex){
		super(ex);
	}
	
	public RecipeFinderException(String message){
		super(message);
	}
	
	public RecipeFinderException(String message,Exception ex){
		super(message,ex);
	}
}
