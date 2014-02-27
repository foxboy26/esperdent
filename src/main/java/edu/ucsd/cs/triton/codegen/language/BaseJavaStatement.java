package edu.ucsd.cs.triton.codegen.language;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseJavaStatement implements IStatement {
	protected List<IStatement> _children;
	protected IStatement _parent;
	
	protected abstract void translate(String prefix, StringBuilder sb);
	
	public BaseJavaStatement() {
		_children = new ArrayList<IStatement> ();
	}

	@Override
	public IStatement getParent() {
		return this._parent;
	}

	@Override
  public void setParent(IStatement _statement) {
	  // TODO Auto-generated method stub
	  this._parent = _statement;
  }

	
	@Override
  public void addChild(IStatement n) {
	  // TODO Auto-generated method stub
	  this._children.add(n);
	  n.setParent(this);
  }

	@Override
  public IStatement getChild(int i) {
	  // TODO Auto-generated method stub
	  return this._children.get(i);
  }

	@Override
  public int getNumChildren() {
	  // TODO Auto-generated method stub
	  return this._children.size();
  }
	
	public IStatement end() {
		return _parent;
	}
	
	@Override
	public String translate() {
		StringBuilder sb = new StringBuilder();
		translate("", sb);
		return sb.toString();
	}
	
	protected void childrenTranslate(String prefix, StringBuilder sb) {
		for (IStatement statement: _children) {
			sb.append(prefix);
			((BaseJavaStatement) statement).translate(prefix, sb);
		}
	}
}
