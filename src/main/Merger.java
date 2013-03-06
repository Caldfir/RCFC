package main;

import graphics.FileProgressFrame;
import iterators.TreeBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import tag.Tag;
import tools.ErrorWriter;

public class Merger {

	public static void main(String[] args){

		final String SRC_FOLDER = "in";
		final String DST_FOLDER = "out";

		File folder = new File(SRC_FOLDER);
		File[] fileList = folder.listFiles();
		FileProgressFrame display = new FileProgressFrame("Raw Checker",2*fileList.length);
		FileWriter writer = null;     
		PrintWriter out;
		TreeBuilder t;
		Tag root, bigroot = null;
		
		ErrorWriter er = ErrorWriter.getInstance();

		try {
			display.setVisible(true);

			String inName = null;
			String shortName;
			String extension;

			String output = "";

			for(int i=0; i<fileList.length; i++){
				try {
					inName = fileList[i].getName();
					shortName = inName.substring(0,inName.length()-4);
					extension = inName.substring(inName.length()-3,inName.length());
					//read and parse
					t = new TreeBuilder(SRC_FOLDER + "/" + inName);
					display.set("reading " + inName, 2*i + 1);
					//write
					if(extension.equals("txt")){
						
						root = t.getRoot();
						if( root != null ){
							if(bigroot == null) bigroot = new Tag(root);
							
							display.set("merging " + shortName + ".txt", 2*i + 2);
							for(int j=0; j<root.getChildCount(); j++)
								bigroot.addChild(root.getChildAt(j));
						}
						else er.write("invalid or empty file: " + shortName + ".txt");
						
						
					}
				} 
				catch (IOException e0) {
					e0.printStackTrace();
				}
			}
			
			if( bigroot != null ){
				output = "all" + "\n\n";
				for(int j=0; j<bigroot.getChildCount(); j++)
					output = output + "\t" + bigroot.getChildAt(j).getArgument(1) + "\n";
				output = output + "\n" + bigroot.toRawString();

				//write
				writer = new FileWriter(DST_FOLDER + "/" + "all" + ".txt");
				out = new PrintWriter(writer);
				out.print(output);

				out.close();
				writer.close();
			}
			
			display.setVisible(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			er.close();
			System.exit(0);
		}
	}
}
