package edu.ucsd.cs.triton.operator;

public enum Unit {
	SECOND(1), 
	MINITUE(1 * 60), 
	HOUR(1 * 60 * 60), 
	DAY(1 * 60 * 60 * 24);
	
	private int _value;
	
	private Unit(int value) {
		_value = value;
	}
	
	public int getValue() {
		return _value;
	}
}
