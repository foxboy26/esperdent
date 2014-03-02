package edu.ucsd.cs.triton.codegen;

import java.util.List;

import backtype.storm.tuple.Fields;

import edu.ucsd.cs.triton.expression.Attribute;
import edu.ucsd.cs.triton.operator.Aggregation;
import edu.ucsd.cs.triton.operator.Aggregator;
import edu.ucsd.cs.triton.operator.BasicOperator;
import edu.ucsd.cs.triton.operator.FixedLengthWindow;
import edu.ucsd.cs.triton.operator.InputStream;
import edu.ucsd.cs.triton.operator.Join;
import edu.ucsd.cs.triton.operator.OperatorVisitor;
import edu.ucsd.cs.triton.operator.OutputStream;
import edu.ucsd.cs.triton.operator.Product;
import edu.ucsd.cs.triton.operator.Projection;
import edu.ucsd.cs.triton.operator.Selection;
import edu.ucsd.cs.triton.operator.TimeBatchWindow;
import edu.ucsd.cs.triton.operator.TimeWindow;
import edu.ucsd.cs.triton.util.PrintFilter;

public class Translator implements OperatorVisitor {
	
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
		StringBuilder sb = (StringBuilder) data;
		
		// group by
		if (operator.containsGroupBy()) {
			String fields = TridentBuilder.newFields(operator.getGroupByList());
			sb.append(TridentBuilder.groupby(fields));
		}
		
		// aggregation
		sb.append(TridentBuilder.chainedAgg());
		for (Aggregator agg : operator.getAggregatorList()) {
			String aggregator = TridentBuilder.newFunction(agg.getName());
			String output = TridentBuilder.newFields(agg.getOutputField());
			if (/* check input */) {
				sb.append(TridentBuilder.aggregate(aggregator, output));
			} else {
				String input = TridentBuilder.newFields(agg.getInputField());
				sb.append(TridentBuilder.aggregate(input, aggregator, output));
			}
		}
		sb.append(".chainEnd()\n");
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
		StringBuilder sb = (StringBuilder) data;

		if (operator.isStdout()) {
			String printer = TridentBuilder.newFunction("PrintFilter");
			String fields = TridentBuilder.newFields();
			sb.append(TridentBuilder.each(fields, printer));
		}
		
	  return null;
  }
}
