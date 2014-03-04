package edu.ucsd.cs.triton.resources;

public abstract class AbstractSource implements ISource {
	String _uri;
	
	public AbstractSource(final String uri) {
		_uri = uri.substring(1, uri.length()-1);
	}
	
	@Override
	public String toString() {
		return _uri;
	}
}
