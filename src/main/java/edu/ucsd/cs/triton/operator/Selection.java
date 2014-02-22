package edu.ucsd.cs.triton.operator;

import edu.ucsd.cs.triton.expression.BooleanExpression;

public class Selection extends BasicOperator {

	private BooleanExpression _filter;

	public Selection(final BooleanExpression filter) {
	  // TODO Auto-generated constructor stub
		_filter = filter;
  }

	public void setFilter(final BooleanExpression filter) {
	  // TODO Auto-generated method stub
	  _filter = filter;
  }
}
