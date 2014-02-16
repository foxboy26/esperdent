package edu.ucsd.cs.triton.operator;

import java.util.ArrayList;
import java.util.HashMap;

public class Projection extends BasicOperator {
	private ArrayList<Attribute> attributeList;
	private HashMap<String, String> aggreatorAttributeList;
	
	public void addAttribute(Attribute attribute) {
		attributeList.add(attribute);
	}	
}
