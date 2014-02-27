package edu.ucsd.cs.triton.codegen.language;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseJavaStatement implements IStatement {
	protected List<IStatement> _children;
	
	protected abstract void translate(String prefix, StringBuilder sb);
	
	public BaseJavaStatement() {
		_children = new ArrayList<IStatement> ();
	}
	
	@Override
  public void addChild(IStatement n) {
	  // TODO Auto-generated method stub
	  this._children.add(n);
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
	
	@Override
	public String translate() {
		StringBuilder sb = new StringBuilder();
		translate("", sb);
		return sb.toString();
	}
	
	protected void childrenTranslate(String prefix, StringBuilder sb) {
		for (IStatement statement: _children) {
			((BaseJavaStatement) statement).translate(prefix, sb);
		}
	}
}
