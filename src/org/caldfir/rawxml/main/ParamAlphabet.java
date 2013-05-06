package org.caldfir.rawxml.main;

import java.io.*;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Scanner;

public class ParamAlphabet {

	public static void main(String[] args) {

		final String FOLDER = "Paramaters";
		
		File folder = new File(FOLDER);
		File[] fileList = folder.listFiles();
		FileReader reader;
		FileWriter writer;
		Scanner in;             
		PrintWriter out;
		HashSet<String> lines;
		String[] linesArray;
		for(int i=0; i<fileList.length; i++){
			try {
			//read
				reader = new FileReader(fileList[i]);
				lines = new HashSet<String>();
				in = new Scanner(reader);
				while(in.hasNextLine()){
					lines.add(in.nextLine());
				}
				in.close();
			
			//sort
				linesArray = lines.toArray(new String[0]);
				Arrays.sort(linesArray);
			
			//write
				writer = new FileWriter(fileList[i]);
				out = new PrintWriter(writer);
				for(int j=0; j<linesArray.length; j++){
					out.println(linesArray[j]);
				}
				out.close();
				
			} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		}
	}
}
