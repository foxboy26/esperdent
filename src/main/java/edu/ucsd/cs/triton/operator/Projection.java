package edu.ucsd.cs.triton.operator;

import java.util.ArrayList;
import java.util.List;

public class Projection extends BasicOperator {
	private List<ProjectionField> _projectionField;
	
	public Projection() {
		_projectionField = new ArrayList<ProjectionField> ();
	}
	
	public void addField(ProjectionField attribute) {
		_projectionField.add(attribute);
	}	
}
