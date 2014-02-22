package edu.ucsd.cs.triton.expression;

public abstract class BaseExpression {
	protected BaseExpression _left;
	protected BaseExpression _right;
	protected boolean _isLeaf;
	
	public BaseExpression() {
		_left = _right = null;
		_isLeaf = false;
	}
	
	public void dump(String prefix) {
		_left.dump(prefix + " ");
		_right.dump(prefix + " ");
	}
	
	public String toString() {
		return _left.toString() + " " + _right.toString();
	}
}
