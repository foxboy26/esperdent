package edu.ucsd.cs.triton.codegen;

import edu.ucsd.cs.triton.operator.Aggregation;
import edu.ucsd.cs.triton.operator.BasicOperator;
import edu.ucsd.cs.triton.operator.FixedLengthWindow;
import edu.ucsd.cs.triton.operator.InputStream;
import edu.ucsd.cs.triton.operator.OperatorVisitor;
import edu.ucsd.cs.triton.operator.OutputStream;
import edu.ucsd.cs.triton.operator.Join;
import edu.ucsd.cs.triton.operator.Product;
import edu.ucsd.cs.triton.operator.Projection;
import edu.ucsd.cs.triton.operator.Selection;
import edu.ucsd.cs.triton.operator.TimeBatchWindow;
import edu.ucsd.cs.triton.operator.TimeWindow;

public class Translator implements OperatorVisitor {

	BasicOperator _plan;
	
	public Translator(BasicOperator plan) {
	  // TODO Auto-generated constructor stub
		_plan = plan;
  }

	public void translate() {
	}

	@Override
  public Object visit(BasicOperator operator, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(Projection operator, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(Selection operator, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(InputStream operator, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(Join operator, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(Product operator, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(Aggregation operator, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(FixedLengthWindow operator, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(TimeWindow operator, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(TimeBatchWindow operator, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(OutputStream operator, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }
}
