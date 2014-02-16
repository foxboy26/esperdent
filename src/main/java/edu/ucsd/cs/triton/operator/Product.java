package edu.ucsd.cs.triton.operator;

import java.util.ArrayList;

public class Product extends BasicOperator {
	
	private ArrayList<String> streamList;
	
	public Product() {
		super(OperatorConstants.PRODUCT);
		streamList = new ArrayList<String> ();
  }
	
	public void addStream(String stream) {
		streamList.add(stream);
	}
	
} 
