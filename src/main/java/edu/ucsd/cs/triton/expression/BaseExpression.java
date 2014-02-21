package edu.ucsd.cs.triton.expression;

public class BaseExpression {
	protected BaseExpression _left;
	protected BaseExpression _right;
	protected boolean _isLeaf;
	
	public BaseExpression() {
		_left = _right = null;
		_isLeaf = false;
	}
}
