package edu.ucsd.cs.triton.codegen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.ucsd.cs.triton.expression.ArithmeticExpression;
import edu.ucsd.cs.triton.expression.Attribute;
import edu.ucsd.cs.triton.expression.AttributeExpression;
import edu.ucsd.cs.triton.expression.BaseExpression;
import edu.ucsd.cs.triton.expression.FloatExpression;
import edu.ucsd.cs.triton.expression.IntegerExpression;
import edu.ucsd.cs.triton.expression.StringExpression;
import edu.ucsd.cs.triton.operator.BaseLogicPlan;
import edu.ucsd.cs.triton.resources.ResourceManager;

public final class Util {
	private Util() {
	}
	
	
	public static String translateAttribute(final Attribute attribute, final Attribute[] inputFields) {
		
		int n = inputFields.length;
		for (int i = 0; i < n; i++) {
			if (attribute.equals(inputFields[i])) {
				ResourceManager resourceManager = ResourceManager.getInstance();
				String type = resourceManager.getAttributeType(attribute.getStream(), attribute.getName());
				return ("tuple.get" + type + "(" + i + ")");
			}
		}
		
		return null;
	}
	
	public static void translateArithmeticExpression(
      BaseExpression expression, Attribute[] inputFields, StringBuilder sb) {
	  // TODO Auto-generated method stub
		if (expression instanceof IntegerExpression ||
				expression instanceof FloatExpression ||
				expression instanceof StringExpression) {
			sb.append(expression.toString());
		} else if (expression instanceof AttributeExpression) {
			Attribute attribute = ((AttributeExpression) expression).getAttribute();
			sb.append(translateAttribute(attribute, inputFields));
			
		} else if (expression instanceof ArithmeticExpression) {
			ArithmeticExpression arithmeticExpression = (ArithmeticExpression) expression;

			sb.append("(");
			translateArithmeticExpression(arithmeticExpression.getLeft(), inputFields, sb);
			
			sb.append(" " + arithmeticExpression.getOperator() + " ");
			
			translateArithmeticExpression(arithmeticExpression.getRight(), inputFields, sb);
			sb.append(")");
		} else {
			System.err.println("error in arithmetic expr translation");
		}
  }
	
	
	public static List<BaseLogicPlan> tsort(final List<BaseLogicPlan> list) {
		Set<BaseLogicPlan> visited = new HashSet<BaseLogicPlan> ();
		Deque<BaseLogicPlan> stack = new ArrayDeque<BaseLogicPlan>();
		for (BaseLogicPlan cur : list) {
			if (!visited.contains(cur)) {
				visited.add(cur);
				tsortHelp(list, visited, stack, cur);
			}
		}
		
		List<BaseLogicPlan> res = new ArrayList<BaseLogicPlan> ();
		while (!stack.isEmpty()) {
			res.add(stack.pop());
		}
		return res;
	}
	
	private static <T> void tsortHelp(final List<BaseLogicPlan> list, Set<BaseLogicPlan> visited, Deque<BaseLogicPlan> stack, BaseLogicPlan cur) {
		List<BaseLogicPlan> neighbor = cur.getDependenceList();
		for (BaseLogicPlan n : neighbor) {
			if (!visited.contains(n)) {
				tsortHelp(list, visited, stack, n);
			}
		}
		stack.push(cur);
	}
}
