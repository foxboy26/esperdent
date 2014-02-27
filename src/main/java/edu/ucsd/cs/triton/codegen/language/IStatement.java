package edu.ucsd.cs.triton.codegen.language;

public interface IStatement {

	public void addChild(IStatement n);

	/**
	 * This method returns a child node. The children are numbered from zero, left
	 * to right.
	 */
	public IStatement getChild(int i);

	/** Return the number of children the node has. */
	public int getNumChildren();
	
	public String translate();
}
