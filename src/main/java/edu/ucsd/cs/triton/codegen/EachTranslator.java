package edu.ucsd.cs.triton.codegen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.ucsd.cs.triton.codegen.language.ClassStatement;
import edu.ucsd.cs.triton.expression.Attribute;
import edu.ucsd.cs.triton.expression.BaseExpression;
import edu.ucsd.cs.triton.operator.ExpressionField;
import edu.ucsd.cs.triton.operator.LogicPlan;


/**
 * filter looks like this
 * 
  public static class MyFunction extends BaseFunction {
    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
    	collector.emit(new Values(tuple.getInteger(0) * 2, tuple.getString(1) + "!!", tuple.getInteger(2) + 2));
    }
  }
 */
public class EachTranslator {
	
	private static final String FILTER_PREFIX = "Each";
	
	private static int filterCount = 0;
	
	private final LogicPlan _logicPlan;
	private final Attribute[] _inputFields;
	private final String[] _outputFields;
	private final String _name;
	private final List<ExpressionField> _exprFieldList;
	
	public EachTranslator(LogicPlan logicPlan, List<ExpressionField> exprList) {
		_logicPlan = logicPlan;
		_exprFieldList = exprList;

		// gather input fields;
		Set<Attribute> inputFields = new HashSet<Attribute> ();
		for (ExpressionField exprField : _exprFieldList) {
			BaseExpression baseExpr = exprField.getBaseExpression();
			Attribute[] attrs = baseExpr.getInputFields();
			for (Attribute attr : attrs) {
				inputFields.add(attr);
			}
		}
		_inputFields = inputFields.toArray(new Attribute[inputFields.size()]);

		// gather output fields
		_outputFields = new String[_exprFieldList.size()];
		for (int i = 0; i < exprList.size(); i++) {
			_outputFields[i] = exprList.get(i).getOutputField();
		}
		
		// allocate class name
		_name = _logicPlan.getPlanName() + FILTER_PREFIX + (filterCount++);
	}

	/**
	 * translate each function
	 * @return
	 */
	public ClassStatement translate() {
	  // TODO Auto-generated method stub
	  List<String> exprStmtList = new ArrayList<String> ();
		for (ExpressionField exprField : _exprFieldList) {
		  StringBuilder sb = new StringBuilder();
			Util.translateArithmeticExpression(exprField.getBaseExpression(), _inputFields, sb);
			exprStmtList.add(sb.toString());
		}
	  
		return new ClassStatement("public static class " + _name + " extends BaseFunction")
			.MemberFunction("public void execute(TridentTuple tuple, TridentCollector collector)")
				.SimpleStmt("collector.emit(" + TridentBuilder.newValues(exprStmtList) + ")")
			.EndMemberFunction();
	}
	
	public Object[] getFilterInputFields() {
	  // TODO Auto-generated method stub
	  return null;
  }

	public String getName() {
	  // TODO Auto-generated method stub
	  return _name;
  }

	public Attribute[] getInputFields() {
	  // TODO Auto-generated method stub
	  return _inputFields;
  }

	public String[] getOutputFields() {
	  // TODO Auto-generated method stub
	  return _outputFields;
  }
}