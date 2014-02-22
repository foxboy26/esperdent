package edu.ucsd.cs.triton.operator;

import edu.ucsd.cs.triton.expression.BaseExpression;

public class Selection extends BasicOperator {

	private BaseExpression _filter;

	public Selection(final BaseExpression filter) {
	  // TODO Auto-generated constructor stub
		_filter = filter;
  }

	public void setFilter(final BaseExpression filter) {
	  // TODO Auto-generated method stub
	  _filter = filter;
  }
}
