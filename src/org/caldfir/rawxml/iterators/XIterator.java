package org.caldfir.rawxml.iterators;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import org.caldfir.rawxml.tag.Tag;
import org.caldfir.rawxml.tools.ErrorWriter;


public class XIterator extends TagIterator{

	private FileReader reader;
	private Scanner in;
	private static Pattern p = Pattern.compile("(<)(([^><])*)(>)");
	
	public XIterator(String filename) throws FileNotFoundException{
		reader = new FileReader(filename);
		in = new Scanner(reader);
		in.useDelimiter("<");
	}

	@Override
	public Tag next() {
		String t = null;
		if(in.hasNext()){
			t = in.findInLine(p);
			if(in.hasNextLine()){
				in.nextLine();
				lineNum++;
			}
			return Tag.xTag(t);
		}
		return null;
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
