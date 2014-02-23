package edu.ucsd.cs.triton.expression;

public abstract class BaseExpression {
	protected BaseExpression _left;
	protected BaseExpression _right;
	protected boolean _isLeaf;

	abstract public BaseExpression clone();
	abstract public void dump(String prefix);
	
	public BaseExpression() {
		_left = _right = null;
		_isLeaf = false;
	}
	
	public BaseExpression getLeft() {
		return _left;
	}
	
	public BaseExpression getRight() {
		return _right;
	}
	
	public String toString() {
		return _left.toString() + " " + _right.toString();
	}
}
