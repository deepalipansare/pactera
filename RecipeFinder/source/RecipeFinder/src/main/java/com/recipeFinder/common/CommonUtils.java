package com.recipeFinder.common;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.recipeFinder.excepion.RecipeFinderException;

/**
 * Common utility functions 
 * @author Deepali
 *
 */
public class CommonUtils {
	/**
	 * Reads a CSV file 
	 * @param fileName
	 * @return
	 * @throws RecipeFinderException
	 */
	public static List<String> readCSVFile(String fileName) throws RecipeFinderException{
		String line = "";
		List<String> output = new ArrayList<String>();
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
			for(line=br.readLine();!StringUtils.isBlank(line);line = br.readLine()){
				output.add(line);
			}
			return output;
		}catch (IOException iEx){
			throw new RecipeFinderException("Error Reading File",iEx);
		}
	}
	
}
