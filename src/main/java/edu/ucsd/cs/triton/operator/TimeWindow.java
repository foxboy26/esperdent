package edu.ucsd.cs.triton.operator;

public class TimeWindow extends BaseWindow {

	private long _duration;

	public TimeWindow(long duration) {
		_duration = duration;
		_type = OperatorType.TIME_WINDOW;
	}
	
	public TimeWindow(int duration, Unit unit) {
		super();
		_duration = duration * unit.getValue();
	}
	
	public long getDuration() {
		return _duration;
	}
}
