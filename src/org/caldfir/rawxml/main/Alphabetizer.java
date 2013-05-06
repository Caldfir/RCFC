package org.caldfir.rawxml.main;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;

import org.caldfir.rawxml.graphics.FileProgressFrame;
import org.caldfir.rawxml.iterators.TreeBuilder;
import org.caldfir.rawxml.tag.Tag;
import org.caldfir.rawxml.tools.ErrorWriter;


public class Alphabetizer {
	
	private static class TagSecondArgStringComparator implements Comparator<Tag> {

		@Override
		public int compare(Tag t1, Tag t2) {
			if(t1.tagLength() > 1 && t2.tagLength() > 1){
				return (t1.getArgument(1)).compareTo(t2.getArgument(1));
			}
			//TODO this should probably fail harder?
			return 0;
		}
		
	}

	public static void main(String[] args){

		final String SRC_FOLDER = "in";
		final String DST_FOLDER = "out";

		File folder = new File(SRC_FOLDER);
		File[] fileList = folder.listFiles();
		FileProgressFrame display = new FileProgressFrame("Raw Alphabetizer",2*fileList.length);
		FileWriter writer = null;     
		PrintWriter out;
		TreeBuilder t;
		Tag root;
		TagSecondArgStringComparator tComp = new TagSecondArgStringComparator();
		
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
						root.sortChildren(tComp);
						if( root != null ){
							output = shortName + "\n\n";
							for(int j=0; j<root.getChildCount(); j++)
								output = output + "\t" + root.getChildAt(j).getArgument(1) + "\n";
							output = output + "\n" + root.toRawString();
							
							
							display.set("writing " + shortName + ".txt", 2*i + 2);

							//write
							writer = new FileWriter(DST_FOLDER + "/" + shortName + ".txt");
							out = new PrintWriter(writer);
							out.print(output);

							out.close();
							writer.close();
						}
						else er.write("invalid or empty file: " + shortName + ".txt");
					}
				} 
				catch (IOException e0) {
					e0.printStackTrace();
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
