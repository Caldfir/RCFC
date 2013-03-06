package main;

import iterators.TreeBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;

import exceptions.UnrecognizedTagException;

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
