package edu.ucsd.cs.triton.resources;

import java.util.HashMap;

public class RelationDefinition extends BaseDefinition {
	
	public RelationDefinition(final String relationName) {
		super(relationName);
	}
	
	public RelationDefinition(final String relationName, final AbstractSource source) {
		super(relationName, source);
	}
}
