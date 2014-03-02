package edu.ucsd.cs.triton.codegen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import storm.trident.TridentTopology;

import edu.ucsd.cs.triton.codegen.language.ClassStatement;
import edu.ucsd.cs.triton.codegen.language.JavaProgram;
import edu.ucsd.cs.triton.codegen.language.MemberFunction;
import edu.ucsd.cs.triton.operator.LogicPlan;
import edu.ucsd.cs.triton.resources.ResourceManager;

public final class CodeGenerator {
	//final LogicPlan _logicPlan;
	ResourceManager _resourceManager;
	List<LogicPlan> _planList;
	JavaProgram _program;
	
//	public CodeGenerator(final LogicPlan logicPlan, String targetName) {
//		_program = new JavaProgram(targetName);
//		_logicPlan = logicPlan;
//	}
	
	public CodeGenerator(ResourceManager resourceManager, LogicPlan plan) {
	  // TODO Auto-generated constructor stub
		_resourceManager = resourceManager;
		//_plan = plan;
  }

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
		
		//Map<Integer, List<Integer>> dependencyGraph = buildDependencyGraph();
		//List<Integer> orders = Util.tsort(dependencyGraph);
		
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
	  ClassStatement c = _program.Class();
	  	  
	  generateTopology(c);
	  
	  generateDefaultMainEntry(c);
	  
	  c.EndClass();
  }

	private void generateTopology(ClassStatement c) {
	  // TODO Auto-generated method stub
	  MemberFunction mf = c.MemberFunction("public static StormTopology buildTopology(LocalDRPC drpc)");
	  
	  mf.SimpleStmt("TridentTopology topology = new TridentTopology()");
	  
	  Translator translator = new Translator();
	  translator.visit(operator, data);
	  
	  mf.Return("topology.build()")
	  	.EndMemberFunction();
  }

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
