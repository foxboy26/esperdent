package edu.ucsd.cs.triton.operator;

import edu.ucsd.cs.triton.resources.BaseDefinition;

public class Register extends BasicOperator {
	private final BaseDefinition _definition;
	
	Register(final BaseDefinition def) {
		_type = OperatorType.REGISTER;
		_definition = def;
	}
	
	public BaseDefinition getDefinition() {
		return _definition;
	}
}
