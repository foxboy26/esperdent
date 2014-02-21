package edu.ucsd.cs.triton.expression;

public class IntegerExpression extends BaseExpression {
	private int _value;
	
	public IntegerExpression(int value) {
		super();
		_value = value;
		_isLeaf = true;
	}
	
	public int getValue() {
		return _value;
	}
}
