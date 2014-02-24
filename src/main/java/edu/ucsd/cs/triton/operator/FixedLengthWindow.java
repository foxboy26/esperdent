package edu.ucsd.cs.triton.operator;

public class FixedLengthWindow extends BaseWindow {
	private int _row;
	
	public FixedLengthWindow(final int row) {
		super();
		_type = OperatorType.LENGTH_WINDOW;
		_row = row;
	}
	
	public int getLength() {
		return _row;
	}
}
