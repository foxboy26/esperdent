package edu.ucsd.cs.triton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import parser.ASTStart;
import parser.ParseException;
import parser.TritonParser;
import edu.ucsd.cs.triton.codegen.Translator;
import edu.ucsd.cs.triton.operator.LogicPlan;
import edu.ucsd.cs.triton.operator.LogicPlanVisitor;
import edu.ucsd.cs.triton.operator.Start;
import edu.ucsd.cs.triton.resources.ResourceManager;

public class Compiler {
	private static final Logger LOGGER = LoggerFactory.getLogger(Compiler.class);

	public static void main(String[] args) {
		
		String inputFileName = "src/test/jjtree/codegen.esp";
		String outputFileName = "test";
		try {
			TritonParser tritonParser;
			tritonParser = new TritonParser(new FileInputStream(new File(
			    inputFileName)));

			ASTStart root;

			root = tritonParser.Start();
			// root.dump(">");
			
			ResourceManager resourceManager = ResourceManager.getInstance();
			
			LogicPlanVisitor logicPlanVisitor = new LogicPlanVisitor(resourceManager);
			
			root.childrenAccept(logicPlanVisitor, resourceManager);
			
			//System.out.println(resourceManager.getStreamByName("s1"));
			
//			CodeGenerator codeGen = new CodeGenerator(resourceManager);
//			
//			JavaProgram program = codeGen.generate(outputFileName);
//			System.out.println(program.toString());
			
			ArrayList<LogicPlan> logicPlanList = logicPlanVisitor.getLogicPlanList();
			LogicPlan lp = logicPlanList.get(0);
      
			Start plan = lp.generatePlan();
			plan.dump("");

			Translator translator = new Translator(lp, resourceManager);
			StringBuilder sb = new StringBuilder();
			
			translator.visit(plan, sb);
			System.out.println(sb.toString());

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
