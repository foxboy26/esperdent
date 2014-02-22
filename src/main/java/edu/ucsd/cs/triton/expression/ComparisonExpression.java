package edu.ucsd.cs.triton.expression;

public class ComparisonExpression extends BaseExpression {
	
	private ComparisonOperator _op;
	
	public ComparisonExpression(ComparisonOperator op) {
		_op = op;
	}
	
	public ComparisonExpression(ComparisonOperator op, BaseExpression left, BaseExpression right) {
		_left = left;
		_right = right;
	}
}
