package org.caldfir.rawxml.iterators;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import org.caldfir.rawxml.tag.Tag;
import org.caldfir.rawxml.tools.ErrorWriter;


public class RawIterator extends TagIterator{

	private FileReader reader;
	private Scanner in;
	private static Pattern p = Pattern.compile("(\\[)(([^\\]\\[])*)(\\])");

	public RawIterator(String filename) throws FileNotFoundException{
		reader = new FileReader(filename);
		in = new Scanner(reader);
	}

	@Override
	public Tag next() {
		String t = null;
		t = in.findInLine(p);
		while(t == null){
			try{
				in.nextLine();
				lineNum++;
			} 
			catch (NoSuchElementException e){
				return null;
			}
			t = in.findInLine(p);
		}
		return Tag.rawTag(t);
	}

	@Override
	public void close() {
		try {
			in.close();
			reader.close();
			
		} catch (IOException e) {
			ErrorWriter er = ErrorWriter.getInstance();
			er.write("failure in attempting to close file: " + reader);
		}
	}
}
