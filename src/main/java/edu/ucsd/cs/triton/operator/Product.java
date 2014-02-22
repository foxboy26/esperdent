package edu.ucsd.cs.triton.operator;

import java.util.ArrayList;

public class Product extends BasicOperator {
	
	private ArrayList<String> streamList;
	
	public void addStream(String stream) {
		streamList.add(stream);
	}
	
} 
