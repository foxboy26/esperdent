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
		System.out.println(_stream);
	  return _stream;
  }
	
	public String getName() {
		return _name;
	}
	
	public String getDefaultOutputField() {
		return toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if (o == null || !(o instanceof Attribute)) {
			return false;
		}
		
		Attribute attribute = (Attribute) o;
		
		return attribute._name.equals(_name) && attribute._stream.equals(_stream); 
	}
	
	@Override
	public String toString() {
		return _name;
	}

	public String getAttributeName() {
	  // TODO Auto-generated method stub
	  return _name.split("\\.")[1];
  }

	public String getOriginalStream() {
	  // TODO Auto-generated method stub
	  return _name.split("\\.")[0];
  }
}
