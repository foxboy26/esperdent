package edu.ucsd.cs.triton.operator;

import java.util.ArrayList;
import java.util.List;

public class BasicOperator implements IOperator {
	protected OperatorType _type;
	protected List<IOperator> _children;
	protected IOperator _parent;
	
	public BasicOperator() {
		_children = new ArrayList<IOperator> ();
		_type = OperatorType.BASIC;
	}
	
	public IOperator getParent() {
		return this._parent;
	}

	@Override
  public void setParent(IOperator op) {
	  // TODO Auto-generated method stub
	  this._parent = op;
  }

	
	@Override
  public void addChild(IOperator n, int i) {
	  // TODO Auto-generated method stub
	  this._children.add(i, n);
  }

	@Override
  public IOperator getChild(int i) {
	  // TODO Auto-generated method stub
	  return this._children.get(i);
  }

	@Override
  public int getNumChildren() {
	  // TODO Auto-generated method stub
	  return this._children.size();
  }
	
	public void dump(String prefix) {
    System.out.println(toString(prefix));
    if (_children != null) {
      for (int i = 0; i < _children.size(); ++i) {
      	IOperator n = _children.get(i);
        if (n != null) {
          n.dump(prefix + " ");
        }
      }
    }
  }
	
  public String toString(String prefix) { return prefix + toString(); }
	
  @Override
	public String toString() {
		return _type.toString();
	}
}
