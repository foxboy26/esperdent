package edu.ucsd.cs.triton.expression;

public class LogicExpression extends BooleanExpression {
	private LogicOperator _op;
	
	public LogicExpression(LogicOperator op) {
		_op = op;
	}
	
	public LogicExpression(LogicOperator op, BooleanExpression left, BooleanExpression right) {
		_left = left;
		_right = right;
	}
	
	@Override
	public void dump(String prefix) {
		System.out.println(prefix + _op);
		_left.dump(prefix + " ");
		_right.dump(prefix + " ");
	}
}
