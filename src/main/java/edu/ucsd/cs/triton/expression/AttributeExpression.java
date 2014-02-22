package edu.ucsd.cs.triton.expression;


public class AttributeExpression extends BaseExpression {
	private Attribute _attribute;
	
	public AttributeExpression(final Attribute attribute) {
		super();
		_attribute = attribute;
		_isLeaf = true;
	}
	
	public Attribute getAttribute() {
		return _attribute;
	}
	
	@Override
	public String toString() {
		return _attribute.toString();
	}
}
