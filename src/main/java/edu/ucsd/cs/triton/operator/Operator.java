package edu.ucsd.cs.triton.operator;

public interface Operator {

	/**
	 * This pair of methods are used to inform the node of its parent.
	 */
	public void setParent(Operator n);

	public Operator getParent();

	/**
	 * This method tells the node to add its argument to the node's list of
	 * children.
	 */
	public void addChild(Operator n, int i);

	/**
	 * This method returns a child node. The children are numbered from zero, left
	 * to right.
	 */
	public Operator getChild(int i);

	/** Return the number of children the node has. */
	public int getNumChildren();
}
