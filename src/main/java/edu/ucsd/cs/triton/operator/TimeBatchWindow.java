package edu.ucsd.cs.triton.operator;

public class TimeBatchWindow extends TimeWindow {

	public TimeBatchWindow(long duration) {
	  super(duration);
	  // TODO Auto-generated constructor stub
		_type = OperatorType.TIME_BATCH_WINDOW;
  }

}
