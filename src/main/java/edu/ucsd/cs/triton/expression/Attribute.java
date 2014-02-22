package edu.ucsd.cs.triton.expression;

public class Attribute {
	private final String _stream;
	private final String _name;
	
	public Attribute(final String stream, final String name) {
		_stream = stream;
		_name = name;
	}

	public String getStream() {
	  // TODO Auto-generated method stub
	  return _stream;
  }
	
	public String getName() {
		return _name;
	}
	
	public String getDefaultOutputField() {
		return toString();
	}
	
	@Override
	public String toString() {
		return _stream + "." + _name;
	}
}
