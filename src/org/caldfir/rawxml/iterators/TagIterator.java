package org.caldfir.rawxml.iterators;

import org.caldfir.rawxml.tag.Tag;

public abstract class TagIterator {

	protected int lineNum = 0;
	
	public abstract Tag next();
	
	public int getLine(){
		return lineNum;
	}
	
	public abstract void close();
	
}
