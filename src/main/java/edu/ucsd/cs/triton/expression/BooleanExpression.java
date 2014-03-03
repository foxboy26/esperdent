package edu.ucsd.cs.triton.expression;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public abstract class BooleanExpression extends BaseExpression {

	protected String _definition = null;
	
	public abstract BooleanExpression clone();

	public boolean isFromSameDefiniton() {
		  // TODO Auto-generated method stub
		  String definition = null;
		  BaseExpression cur = this;
			Stack<BaseExpression> stack = new Stack<BaseExpression> ();
		  stack.push(cur);
		  
		  while (!stack.empty()) {
		  	cur = stack.pop();
		  	if (cur instanceof AttributeExpression) {
		  		String def = ((AttributeExpression) cur).getAttribute().getStream();
		  		if (definition == null || definition.equals(def)) {
		  			definition = def;
		  		} else {
		  			return false;
		  		}
		  	}
		  	
		  	if (cur._left != null) {
		  		stack.push(cur._left);
		  	}
		  	
		  	if (cur._right != null) {
		  		stack.push(cur._right);
		  	}
		  }
			
		  _definition = definition;
		  
		  return true;
	}
	
	public String getDefinition() {
		return _definition;
	}
	
	public Attribute[] getInputFields() {
	  // TODO Auto-generated method stub
	  Set<Attribute> inputFields = new HashSet<Attribute> ();
	  BaseExpression cur = this;
		Stack<BaseExpression> stack = new Stack<BaseExpression> ();
	  stack.push(cur);
	  
	  while (!stack.empty()) {
	  	cur = stack.pop();
	  	if (cur instanceof AttributeExpression) {
	  		Attribute attribute = ((AttributeExpression) cur).getAttribute();
	  		if (!inputFields.contains(attribute))
	  		inputFields.add(attribute);
	  	}
	  	
	  	if (cur._left != null) {
	  		stack.push(cur._left);
	  	}
	  	
	  	if (cur._right != null) {
	  		stack.push(cur._right);
	  	}
	  }
	  return inputFields.toArray (new Attribute[inputFields.size ()]);
	}
}
