package edu.ucsd.cs.triton.operator;

import java.util.List;

public class Join extends BasicOperator {
	private String _leftDefinition;
	private String _rightDefinition;
	private List<KeyPair> _joinFields;
	
	public Join() {
		
	}

	public void setJoinFields(final List<KeyPair> joinFields) {
	  // TODO Auto-generated method stub
	  _joinFields = joinFields;
  }
}
