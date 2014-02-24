package edu.ucsd.cs.triton.operator;

public class InputStream extends BasicOperator {
	private String _name;

	public InputStream(final String name) {
		_type = OperatorType.INPUT_STREAM;
		_name = name;		
	}

	public void setName(String name) {
		_name = name;
	}
	
	public String getName() {
		return _name;
	}
}
