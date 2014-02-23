package edu.ucsd.cs.triton.expression;

public class ComparisonExpression extends BooleanExpression {
	
	private ComparisonOperator _op;
	
	public ComparisonExpression(ComparisonOperator op) {
		super();
		_op = op;
	}
	
	public ComparisonExpression(ComparisonOperator op, BaseExpression left, BaseExpression right) {
		_left = left;
		_right = right;
		_op = op;
	}
	
	@Override
	public void dump(String prefix) {
		System.out.println(prefix + _op);
		_left.dump(prefix + " ");
		_right.dump(prefix + " ");
	}
	
	public String toString() {
		return "";
	}
}
