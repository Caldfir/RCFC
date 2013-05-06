package org.caldfir.rawxml.main;


import java.io.FileNotFoundException;
import java.io.IOException;

import org.caldfir.rawxml.exceptions.UnrecognizedTagException;
import org.caldfir.rawxml.iterators.TreeBuilder;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TreeBuilder t;
		try {
			t = new TreeBuilder("out/b_detail_plan_default.xml");
			System.out.print(t.getRoot().toRawString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
