package edu.ucsd.cs.triton.expression;

public class FloatExpression extends BaseExpression {
	private float _value;
	
	public FloatExpression(float value) {
		super();
		_value = value;
		_isLeaf = true;
	}
	
	public float getValue() {
		return _value;
	}
}
