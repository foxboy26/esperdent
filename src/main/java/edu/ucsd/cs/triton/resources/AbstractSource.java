package edu.ucsd.cs.triton.resources;

public abstract class AbstractSource implements ISource {
	String _uri;
	
	public AbstractSource(final String uri) {
		_uri = uri;
	}
	
	@Override
	public String toString() {
		return _uri;
	}
}
