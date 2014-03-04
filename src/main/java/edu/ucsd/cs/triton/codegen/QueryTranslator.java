package edu.ucsd.cs.triton.codegen;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ucsd.cs.triton.codegen.language.ClassStatement;
import edu.ucsd.cs.triton.operator.Aggregation;
import edu.ucsd.cs.triton.operator.Aggregator;
import edu.ucsd.cs.triton.operator.BaseLogicPlan;
import edu.ucsd.cs.triton.operator.BasicOperator;
import edu.ucsd.cs.triton.operator.ExpressionField;
import edu.ucsd.cs.triton.operator.FixedLengthWindow;
import edu.ucsd.cs.triton.operator.InputStream;
import edu.ucsd.cs.triton.operator.Join;
import edu.ucsd.cs.triton.operator.LogicQueryPlan;
import edu.ucsd.cs.triton.operator.OperatorVisitor;
import edu.ucsd.cs.triton.operator.OutputStream;
import edu.ucsd.cs.triton.operator.Product;
import edu.ucsd.cs.triton.operator.Projection;
import edu.ucsd.cs.triton.operator.ProjectionField;
import edu.ucsd.cs.triton.operator.Register;
import edu.ucsd.cs.triton.operator.Selection;
import edu.ucsd.cs.triton.operator.Start;
import edu.ucsd.cs.triton.operator.TimeBatchWindow;
import edu.ucsd.cs.triton.operator.TimeWindow;
import edu.ucsd.cs.triton.resources.AbstractSource;
import edu.ucsd.cs.triton.resources.DynamicSource;
import edu.ucsd.cs.triton.resources.ResourceManager;
import edu.ucsd.cs.triton.resources.StaticSource;

public class QueryTranslator implements OperatorVisitor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryTranslator.class);
	
	private final BaseLogicPlan _logicPlan;
	private final String _planName;
	private final ResourceManager _resourceManager;
	private TridentProgram _program;
	
	public QueryTranslator(final BaseLogicPlan logicPlan, TridentProgram program) {
		_logicPlan = logicPlan;
		_planName = logicPlan.getPlanName();
		_resourceManager = ResourceManager.getInstance();
		_program = program;
	}
	
	@Override
  public Object visit(Start operator, Object data) {
		StringBuilder sb = (StringBuilder) data;

		operator.childrenAccept(this, sb);
		
		//TODO fix me! This is a hack on the style. trim the last '\n'
		if (sb.charAt(sb.length() - 1) == '\n') {
			sb.deleteCharAt(sb.length()-1);
		}
		
		return null;
  }

	@Override
  public Object visit(Register operator, Object data) {
	  // TODO Auto-generated method stub
		StringBuilder sb = (StringBuilder) data;
		
		String instance = operator.getDefinition().getName();
		
		AbstractSource source = operator.getDefinition().getSource();
		if (source instanceof StaticSource) {
			String fileName = source.toString();
			sb.append(TridentBuilder.newInstance("StaticFileSpout", instance, TridentBuilder.newString(fileName)));
		} else if (source instanceof DynamicSource) {
			String spout = source.toString();
			sb.append(TridentBuilder.newInstance(spout, instance));
		}
		
	  return null;
  }

	@Override
  public Object visit(Projection operator, Object data) {
	  // TODO Auto-generated method stub
		operator.childrenAccept(this, data);

		StringBuilder sb = (StringBuilder) data;
		
		List<ProjectionField> fieldList = operator.getProjectionFieldList();
		
		// check if projection fields contains expression
		List<ExpressionField> exprFieldList = new ArrayList<ExpressionField> ();
		for (ProjectionField field : fieldList) {
			if (field instanceof ExpressionField) {
				exprFieldList.add((ExpressionField) field);
			}
		}
		
		if (!exprFieldList.isEmpty()) {
			// generate each function 
			EachTranslator eachTranslator = new EachTranslator(_planName, exprFieldList);
			ClassStatement eachFunction = eachTranslator.translate();
			_program.addInnerClass(eachFunction);
			
			// add each
			String inputFields = TridentBuilder.newFields(eachTranslator.getInputFields());
			String outputFields = TridentBuilder.newFields(eachTranslator.getOutputFields());
			String funcName = TridentBuilder.newFunction(eachTranslator.getName());
			sb.append(TridentBuilder.each(inputFields, funcName, outputFields));
		}
		
		// add project
		String[] projectionFields = new String[fieldList.size()];
		for (int i = 0; i < fieldList.size(); i++) {
			projectionFields[i] = fieldList.get(i).getOutputField();
		}
		String fields = TridentBuilder.newFields(projectionFields);
		sb.append(TridentBuilder.project(fields));
	  
		return null;
  }

	@Override
  public Object visit(Selection operator, Object data) {
	  // TODO Auto-generated method stub
		operator.childrenAccept(this, data);

		StringBuilder sb = (StringBuilder) data;

		//TODO generate Filter
		FilterTranslator filterTranslator = new FilterTranslator(_planName, operator.getFilter());
		
		ClassStatement filterClass = filterTranslator.translate();
		
		_program.addInnerClass(filterClass);
		
		// add filter
		String fields = TridentBuilder.newFields(filterTranslator.getFilterInputFields());
		String filter = TridentBuilder.newFunction(filterTranslator.getFilterName());
		sb.append(TridentBuilder.each(fields, filter));
	  return null;
  }

	@Override
  public Object visit(InputStream operator, Object data) {
	  // TODO Auto-generated method stub
		operator.childrenAccept(this, data);
		
		StringBuilder sb = (StringBuilder) data;
		
		if (_logicPlan instanceof LogicQueryPlan) {
			LogicQueryPlan logicPlan = (LogicQueryPlan) _logicPlan;
			if (logicPlan.isNamedQuery()) {
				sb.append("Stream " + _planName + " = ");
			} //else if (_resourceManager.getStreamByName(operator.getName()).) //TODO query def
			sb.append("_topology\n");
		}
		
		String streamName = TridentBuilder.newString(_planName);
		sb.append(TridentBuilder.newStream(streamName, operator.getName().toLowerCase()));
		
	  return null;
  }

	@Override
  public Object visit(Aggregation operator, Object data) {
	  // TODO Auto-generated method stub
		operator.childrenAccept(this, data);
		
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
			//TODO fix the condition check
			if (aggregator.equals("count")) {
				sb.append(TridentBuilder.aggregate(aggregator, output));
			} else {
				String input = TridentBuilder.newFields(agg.getInputField());
				sb.append(TridentBuilder.aggregate(input, aggregator, output));
			}
		}
		sb.append(TridentBuilder.chainEnd());
	  return null;
  }

	@Override
  public Object visit(FixedLengthWindow operator, Object data) {
	  // TODO Auto-generated method stub
		operator.childrenAccept(this, data);

		return null;
  }

	@Override
  public Object visit(TimeWindow operator, Object data) {
	  // TODO Auto-generated method stub
		operator.childrenAccept(this, data);

		return null;
  }

	@Override
  public Object visit(TimeBatchWindow operator, Object data) {
	  // TODO Auto-generated method stub
		operator.childrenAccept(this, data);

	  return null;
  }

	@Override
  public Object visit(OutputStream operator, Object data) {
	  // TODO Auto-generated method stub
		operator.childrenAccept(this, data);
		
		StringBuilder sb = (StringBuilder) data;

		String outputFilter;
		if (operator.isStdout()) {
			outputFilter = TridentBuilder.newFunction("PrintFilter");
		} else {
			String fileName = TridentBuilder.newString(operator.getFileName());
			outputFilter = TridentBuilder.newFunction("FileFilter", fileName);
		}
		
		String fields = TridentBuilder.newFields(operator.getOutputFieldList());
		sb.append(TridentBuilder.each(fields, outputFilter));		
	  
		return null;
  }
	
	@Override
  public Object visit(Join operator, Object data) {
	  // TODO Auto-generated method stub
		operator.childrenAccept(this, data);

	  return null;
  }

	@Override
  public Object visit(Product operator, Object data) {
	  // TODO Auto-generated method stub
		operator.childrenAccept(this, data);

	  return null;
  }

	@Override
  public Object visit(BasicOperator operator, Object data) {
	  // TODO Auto-generated method stub
		
		operator.childrenAccept(this, data);

	  return null;
  }
}
