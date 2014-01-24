package edu.ucsd.cs.triton.operator;

import java.util.ArrayList;

public class BasicOperator implements Operator {
	private ArrayList<Operator> children;
	private Operator parent;
	private int id;
	
	public BasicOperator(int id) {
		this.id = id;
		children = new ArrayList<Operator> ();
	}
	
	public Operator getParent() {
		return this.parent;
	}

	@Override
  public void setParent(Operator op) {
	  // TODO Auto-generated method stub
	  this.parent = op;
  }

	
	@Override
  public void addChild(Operator n, int i) {
	  // TODO Auto-generated method stub
	  this.children.add(i, n);
  }

	@Override
  public Operator getChild(int i) {
	  // TODO Auto-generated method stub
	  return this.children.get(i);
  }

	@Override
  public int getNumChildren() {
	  // TODO Auto-generated method stub
	  return this.children.size();
  }
	
	public void dump(String prefix) {
    System.out.println(toString(prefix));
    if (children != null) {
      for (int i = 0; i < children.size(); ++i) {
      	BasicOperator n = (BasicOperator) children.get(i);
        if (n != null) {
          n.dump(prefix + " ");
        }
      }
    }
  }
	
  public String toString(String prefix) { return prefix + toString(); }
	
	public String toString() {
		return OperatorConstants.OperatorName[this.id];
	}
}
