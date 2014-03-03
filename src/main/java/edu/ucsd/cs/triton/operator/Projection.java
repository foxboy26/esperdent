package edu.ucsd.cs.triton.operator;

import java.util.ArrayList;
import java.util.List;

public class Projection extends BasicOperator {
	private List<ProjectionField> _projectionFieldList;
	
	public Projection() {
		_type = OperatorType.PROJECTION;
		_projectionFieldList = new ArrayList<ProjectionField> ();
	}
	
	public void addField(ProjectionField attribute) {
		_projectionFieldList.add(attribute);
	}
	
	public List<ProjectionField> getProjectionFieldList() {
		return _projectionFieldList;
	}
	
	public String toString() {
		return super.toString() + _projectionFieldList.toString();
	}
	
  /** Accept the visitor. **/
  @Override
	public Object accept(OperatorVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
