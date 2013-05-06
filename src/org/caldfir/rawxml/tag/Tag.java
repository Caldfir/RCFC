package org.caldfir.rawxml.tag;

import java.util.*;

import javax.swing.tree.TreeNode;

public class Tag implements TreeNode{

	public static final byte RAW = 0;
	public static final byte ONELINE = 1;
	public static final byte ENDTAG = 2;
	public static final byte NONE = 3;
	
	private Tag parent;
	private ArrayList<Tag> children;
	private ArrayList<String> tag;
	private byte xData;

	private Tag(){
		parent = null;
		children = new ArrayList<Tag>();
		tag = new ArrayList<String>();
	}
	
	/**
	 * Creates a soft copy of the tag - current implementation is unsafe.
	 */
	public Tag(Tag t){
		parent = null;
		children = new ArrayList<Tag>();
		tag = t.tag;//TODO properly clone this
	}
	
	public static Tag xTag(String s){
		Tag t = new Tag();
		t.xParse(s);
		return t;
	}
	
	public static Tag rawTag(String s){
		Tag t = new Tag();
		t.rawParse(s);
		return t;
	}

	private void xParse(String s){
		if(s.charAt(1) == '/'){
			s = s.substring(2,s.length()-1);
			xData = ENDTAG;
		}
		else if(s.charAt(s.length()-2) == '/'){
			s = s.substring(1,s.length()-2);
			xData = ONELINE;
		}
		else{
			s = s.substring(1,s.length()-1);
			xData = NONE;
		}

		Scanner parser = new Scanner(s);
		String temp;
		temp = parser.next();
		tag.add(temp);
		
		temp = parser.findInLine("(\")([^\"])+(\")");

		while(temp != null){
			tag.add(temp.substring(1,temp.length()-1));
			temp = parser.findInLine("(\")([^\"])+(\")");
		}
	}

	private void rawParse(String s){
		s = s.substring(1,s.length()-1);

		Scanner parser = new Scanner(s);
		parser.useDelimiter("\\:");

		while(parser.hasNext()){
			tag.add(parser.next());
		}

		xData = RAW;
	}

	private void setParent(Tag p){
		parent = p;
	}

	public void addChild(Tag c){
		children.add(c);
		c.setParent(this);
	}

	public Tag getParent() {
		return parent;
	}

	public String tagName(){
		return tag.get(0);
	}
	
	public int tagLength(){
		return tag.size();
	}

	public String getArgument(int i){
		return tag.get(i);
	}

	public short getDepth() {
		if(parent == null){
			return 0;
		}
		return (short) (parent.getDepth() + 1);
	}

	public String toRawString(){
		String raw = "";

		for(int i=0; i<getDepth(); i++){
			raw += "\t";
		}

		raw += "[";
		Iterator<String> tagIter = tag.iterator();
		while(tagIter.hasNext()){
			raw += tagIter.next();
			if(tagIter.hasNext()){
				raw += ":";
			}
		}
		raw += "]";

		if(children.size() != 0){
			Iterator<Tag> childIter = children.iterator();
			while(childIter.hasNext()){
				raw += "\n" + childIter.next().toRawString();
			}
		}
		return raw;
	}

	public String toXString(){
		String xml = "";

		for(int i=0; i<getDepth(); i++){
			xml += "\t";
		}

		xml += "<";

		Iterator<String> tagIter = tag.iterator();
		xml += tagIter.next();

		for(int j=0; tagIter.hasNext(); j++){
			xml += " var" + j + "=\"" + tagIter.next() + "\"";
		}

		if(children.size() == 0){
			xml += "/>";
		}
		else {
			xml += ">";

			Iterator<Tag> childIter = children.iterator();
			while(childIter.hasNext()){
				xml += "\n" + childIter.next().toXString();
			}

			xml += "\n";

			for(int i=0; i<getDepth(); i++){
				xml += "\t";
			}

			xml += "</" + tagName() + ">";
		}
		return xml;
	}
	
	public byte xData(){
		return xData;
	}

	@Override
	public Enumeration<Tag> children() {
		class TagEnumerator implements Enumeration<Tag>{
			
			private ArrayList<Tag> children;
			private int index;

			public TagEnumerator(ArrayList<Tag> children){
				this.children = children;
				index = 0;
			}
			
			@Override
			public boolean hasMoreElements() {
				return (index < children.size());
			}

			@Override
			public Tag nextElement() {
				return children.get(index++);
			}
			
		}
		return new TagEnumerator(children);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public Tag getChildAt(int i) {
		return children.get(i);
	}

	@Override
	public int getChildCount() {
		return children.size();
	}
	
	public boolean hasChild(Tag child){
		boolean match = true;
		for(int j=0; j<children.size(); j++){
			for(int i=0; i<child.tag.size(); i++){
				//look at children of this tag recursively
				if(children.get(j).children.size()>0 && children.get(j).hasChild(child))
					return true;
				
				//search this tag directly
				if(child.tag.get(i) . compareTo( children.get(j).tag.get(i) ) != 0){
					match = false;
					break;
				}
				else match = true;
			}
			if(match) return true;
		}
		
		return false;
	}

	@Override
	public int getIndex(TreeNode tNod) {
		Iterator<Tag> tIter = children.iterator();
		for(int i=0; tIter.hasNext(); i++){
			if(tNod == tIter.next()){
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean isLeaf() {
		if (children.size() == 0){
			return true;
		}
		return false;
	}
	
	public void sortChildren(Comparator<Tag> tComp) {
		Collections.sort(children,tComp);
	}
}
