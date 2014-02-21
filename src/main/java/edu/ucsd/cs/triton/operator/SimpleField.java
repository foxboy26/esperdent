package edu.ucsd.cs.triton.operator;

import edu.ucsd.cs.triton.expression.Attribute;

public class SimpleField extends ProjectionField {
	private Attribute _attribute;

	public SimpleField(String streamName, String attribute) {
		_attribute = new Attribute(streamName, attribute);
  }

	/**
	 * Set default outputField as concatenation of streamName and fieldName
	 * @param attribute
	 */
	public SimpleField(final Attribute attribute) {
		_attribute = attribute;
		_outputField = attribute.toString();
  }

	public String getStream() {
		return _attribute.getStream();
	}
	
	public String getAttributeName() {
		return _attribute.getName();
	}
}
