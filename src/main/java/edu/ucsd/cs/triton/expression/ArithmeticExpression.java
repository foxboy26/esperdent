package edu.ucsd.cs.triton.expression;

public class ArithmeticExpression extends BaseExpression {
	private ArithmeticOperator _op;
	
	public ArithmeticExpression(ArithmeticOperator op) {
		super();
		_op = op;
	}
	
	public ArithmeticExpression(ArithmeticOperator op, BaseExpression left, BaseExpression right) {
		_left = left;
		_right = right;
	}
}
