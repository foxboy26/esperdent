package edu.ucsd.cs.triton.operator;

import java.util.List;

public class Join extends BasicOperator {
	
	private String _leftDefinition;
	private String _rightDefinition;
	private String _outputDefinition;
	private List<KeyPair> _joinFields;
	
	public Join(final String left, final String right) {
		_leftDefinition = left;
		_rightDefinition = right;
		_outputDefinition = left + "##" + right;
		_type = OperatorType.JOIN;
	}
	
	public void setJoinFields(final List<KeyPair> joinFields) {
	  // TODO Auto-generated method stub
	  _joinFields = joinFields;
  }
	
	public List<KeyPair> getJoinFiedl() {
		return _joinFields;
	}

	public String getOutputDefinition() {
	  // TODO Auto-generated method stub
	  return _outputDefinition;
  }
}
