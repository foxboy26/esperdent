package edu.ucsd.cs.triton.operator;

public class Aggregator extends BasicOperator {
	private String _name;
	
	private String _inputField;
	
	private String _outputField;
	
	public Aggregator(final String aggregateFunction, final String inputField) {
		_name = aggregateFunction;
		_inputField = inputField;
		// default output field
		_outputField = _name + "(" + _inputField + ")";
	}
	
	public String getName() {
		return _name;
	}
	
	public void setOutputField(final String outputField) {
		_outputField = outputField;
	}
	
	public String toString() {
		return "{name: " + _name + ", inputField: " + _inputField + ", outputField: " + _outputField + "}";
	}
}
