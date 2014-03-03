package edu.ucsd.cs.triton.codegen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ucsd.cs.triton.codegen.language.ClassStatement;
import edu.ucsd.cs.triton.codegen.language.JavaProgram;
import edu.ucsd.cs.triton.operator.LogicPlan;
import edu.ucsd.cs.triton.operator.Start;
import edu.ucsd.cs.triton.resources.ResourceManager;

public final class CodeGenerator {
	//final LogicPlan _logicPlan;
	ResourceManager _resourceManager;
	List<LogicPlan> _planList;
	JavaProgram _program;

	public CodeGenerator(ResourceManager resourceManager) {
	  // TODO Auto-generated constructor stub
		_resourceManager = resourceManager;
		_planList = new ArrayList<LogicPlan> ();
	}

	public void setLogicPlanList(List<LogicPlan> planList) {
		_planList = planList;
	}
	
	public void addLogicPlan(LogicPlan plan) {
		_planList.add(plan);
	}
	
	public JavaProgram generate(String fileName) {
		
		_program = new JavaProgram(fileName);
		
		generateImport();
		
		generateClass();
//		for (int i : orders) {
//			Translator t = new Translator(_planList[i]);
//			t.translate(outputStream);
//		}
//		
//		if (defaultMain) {
//			generateMain();
//		}
//		
		return _program;
	}
	
	private void generateClass() {
	  // TODO Auto-generated method stub
		ClassStatement classStmt = _program.Class();
		
		// TODO generate spout list
		//classStatement.SimpleStmt("private " + _logicPlan.getInputStreams() + "_spout")
	  generateSpoutsList(classStmt);
	  
	  generateTopology(classStmt);
	  
	  generateDefaultMainEntry(classStmt);
	  
	  classStmt.EndClass();
  }

	// TODO
	private void generateSpoutsList(ClassStatement classStmt) {
	  // TODO Auto-generated method stub
		for (LogicPlan logicPlan : _planList) {
			
			classStmt.SimpleStmt(sb.toString());
		}
  }

	private void generateTopology(ClassStatement c) {
	  // TODO Auto-generated method stub
		
		//Map<Integer, List<Integer>> dependencyGraph = buildDependencyGraph();
		//List<Integer> orders = Util.tsort(dependencyGraph);
		
		c.MemberFunction("public void buildQuery()");

		for (LogicPlan logicPlan : _planList) {
			StringBuilder sb = new StringBuilder();
			Start plan = logicPlan.generatePlan();
			Translator translator = new Translator(logicPlan, _resourceManager);
			translator.visit(plan, sb);
			c.SimpleStmt(sb.toString());
		}
		
		c.EndMemberFunction();
  }

	// TODO
	private void generateDefaultMainEntry(ClassStatement c) {
	  // TODO Auto-generated method stub
	  c.MemberFunction("public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException")
	  	.SimpleStmt("Config conf = new Config()")
	  	.SimpleStmt("conf.setMaxSpoutPending(20)")
	  	.If("args.length == 0")
	  		.SimpleStmt("LocalDRPC drpc = new LocalDRPC()")
	    	.SimpleStmt("LocalCluster cluster = new LocalCluster()")
	    	.SimpleStmt("cluster.submitTopology(\"twitter_demo\", conf, buildTopology(drpc))")
	    .EndIf();
  }

	private void generateImport() {
	  // TODO Auto-generated method stub
		_program
			.Import()
				.add(Import.DEFAULT_IMPORT_LIST)
			.EndImport();
  }

	private Map<Integer, List<Integer>> buildDependencyGraph() {
		return null;
	}
}
