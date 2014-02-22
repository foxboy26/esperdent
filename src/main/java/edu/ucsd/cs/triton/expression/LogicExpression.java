package edu.ucsd.cs.triton.expression;

public class LogicExpression extends BaseExpression {
	private LogicOperator _op;
	
	public LogicExpression(LogicOperator op) {
		_op = op;
	}
	
	public LogicExpression(LogicOperator op, BaseExpression left, BaseExpression right) {
		_left = left;
		_right = right;
	}
}
