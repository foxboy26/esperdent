package edu.ucsd.cs.triton.codegen;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.ucsd.cs.triton.codegen.language.BlockStatement;
import edu.ucsd.cs.triton.codegen.language.JavaProgram;
import edu.ucsd.cs.triton.codegen.language.Keyword;
import edu.ucsd.cs.triton.codegen.language.MemberFunction;
import edu.ucsd.cs.triton.operator.LogicPlan;
import edu.ucsd.cs.triton.operator.Start;
import edu.ucsd.cs.triton.resources.BaseDefinition;
import edu.ucsd.cs.triton.resources.ResourceManager;

public final class CodeGenerator {
	private final ResourceManager _resourceManager;
	private final List<LogicPlan> _planList;
	
	private final String _className;
	private TridentProgram _program;
	
	public CodeGenerator(List<LogicPlan> planList, final String fileName) {
	  // TODO Auto-generated constructor stub
		_resourceManager = ResourceManager.getInstance();
		_planList = planList;
		_program = new TridentProgram(fileName);
		_className = fileName;
	}
	
	public JavaProgram generate() {
		
		generateSpoutsDefinition();
		
	  generateTopology();
	  
	  generateDefaultMainEntry();
		
		return _program.toJava();
	}

	private void generateSpoutsDefinition() {
		Map<String, BaseDefinition> defList = _resourceManager.getDefinitions();
		
		for (Entry<String, BaseDefinition> def : defList.entrySet()) {
			// def.getValue()
			
			// _program.addSpoutDefinition("private " + def)
		}
	}
	
	private void generateTopology() {
	  // TODO Auto-generated method stub
		
		// Map<Integer, List<Integer>> dependencyGraph = buildDependencyGraph();
		
		// List<Integer> orders = Util.tsort(dependencyGraph);
		
		for (LogicPlan logicPlan : _planList) {
			StringBuilder sb = new StringBuilder();
			Start plan = logicPlan.generatePlan();
			QueryTranslator translator = new QueryTranslator(logicPlan, _program);
			translator.visit(plan, sb);
			_program.addStmtToBuildQuery(sb.toString());
		}
		
		/*
		for (int i : orders) {
			StringBuilder sb = new StringBuilder();
			Start plan = _planList.get(i).generatePlan();
			QueryTranslator translator = new QueryTranslator(_planList.get(i), _resourceManager);
			translator.visit(plan, sb);
			_program.addStmtToBuildQuery(sb.toString());
		}*/
  }

	// TODO
	private void generateDefaultMainEntry() {
	  // TODO Auto-generated method stub
		String classStmt = _className + " " + _className.toLowerCase() + " = " + Keyword.NEW + " " + _className + "()";
	  BlockStatement mainEntry = new MemberFunction("public static void main(String[] args)")
	  	.SimpleStmt(classStmt)
	  	.SimpleStmt(_className.toLowerCase() + ".execute(args)");
	  
	  _program.setDefaultMain((MemberFunction) mainEntry);
  }

	private Map<Integer, List<Integer>> buildDependencyGraph() {
		return null;
	}
}
