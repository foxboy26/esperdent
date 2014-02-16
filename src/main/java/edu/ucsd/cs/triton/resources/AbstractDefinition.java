package edu.ucsd.cs.triton.resources;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDefinition {
	protected String _name;
	protected AbstractSource _source;
	protected Map<String, AttributeType> _attributes;
	
	public AbstractDefinition(final String name) {
		_name = name;
		_attributes = new HashMap<String, AttributeType> ();
	}
	
	public AbstractDefinition(final String name, final AbstractSource source) {
		_name = name;
		_source = source;
		_attributes = new HashMap<String, AttributeType> ();
	}
	
	public void setSource(final AbstractSource source) {
		_source = source;
	}
	
	public AttributeType addAttribute(final String name, final AttributeType type) {
		return _attributes.put(name, type);
	}
	
	public String getName() {
		return _name;
	}
	
	public AbstractSource getSource() {
		return _source;
	}
	
	public Map<String, AttributeType> getAttributes() {
		return _attributes;
	}
	
	@Override
	public String toString() {
		return "{name: " + _name + " attributes: " + _attributes + " source: " + _source.toString() + "}";
	}
}
