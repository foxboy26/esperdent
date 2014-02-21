package edu.ucsd.cs.triton.expression;

public class PredicateExpression extends BaseExpression {
	
	private ComparisonOperator _op;
	
	public PredicateExpression(ComparisonOperator op) {
		_op = op;
	}
	
	public PredicateExpression(ComparisonOperator op, BaseExpression left, BaseExpression right) {
		_left = left;
		_right = right;
	}
}
