package com.recipeFinder.test.common;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.recipeFinder.common.CommonUtils;
import com.recipeFinder.excepion.RecipeFinderException;

public class CommonUtilsTest {
	
	private String filename;
	private int size;
	
	@Before
	public void init(){
		filename="C:\\Data\\Work\\pactera\\input\\fridge.csv";
		size=6;
	}
	
	@Test
	public void readCSVFile() throws RecipeFinderException{
		List<String> data = CommonUtils.readCSVFile(filename);
		assertEquals(size, data.size());
	}
}
