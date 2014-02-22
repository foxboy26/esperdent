package edu.ucsd.cs.triton.expression;

public enum ArithmeticOperator {
	PLUS, MINUS, MULT, DIVIDE;
	
	public static ArithmeticOperator fromString(final String op) {
		if (op.equals("+")) {
			return PLUS;
		} else if (op.equals("-")) {
			return MINUS;
		} else if (op.equals("*")) {
			return MULT;
		} else if (op.equals("/")) {
			return DIVIDE;
		} else {
			throw new IllegalArgumentException("Invalid logic opeartor [" + op + "]");
		}
	}
}
