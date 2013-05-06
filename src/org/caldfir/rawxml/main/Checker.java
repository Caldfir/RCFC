package org.caldfir.rawxml.main;


import java.io.File;
import java.io.IOException;

import org.caldfir.rawxml.graphics.FileProgressFrame;
import org.caldfir.rawxml.iterators.TreeBuilder;
import org.caldfir.rawxml.tools.ErrorWriter;


public class Checker {

	public static void main(String[] args){

		final String SRC_FOLDER = "in";

		File folder = new File(SRC_FOLDER);
		File[] fileList = folder.listFiles();
		FileProgressFrame display = new FileProgressFrame("Raw Checker",fileList.length);
		TreeBuilder t;
		
		ErrorWriter er = ErrorWriter.getInstance();

		try {
			display.setVisible(true);
			
			String inName = null;

			for(int i=0; i<fileList.length; i++){
				try {
					inName = fileList[i].getName();
					//read and parse
					t = new TreeBuilder(SRC_FOLDER + "/" + inName);
					display.set(inName, i + 1);
				} 
				catch (IOException e) {
					e.printStackTrace();
				} 
			}
			display.setVisible(false);
		}
		finally {
			er.close();
			System.exit(0);
		}
	}
}
