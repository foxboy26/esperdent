package edu.ucsd.cs.triton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import parser.ASTStart;
import parser.ParseException;
import parser.TritonParser;
import edu.ucsd.cs.triton.codegen.CodeGenerator;
import edu.ucsd.cs.triton.codegen.language.JavaProgram;
import edu.ucsd.cs.triton.operator.BaseLogicPlan;
import edu.ucsd.cs.triton.operator.LogicPlanVisitor;
import edu.ucsd.cs.triton.resources.ResourceManager;

public class Compiler {
	private static final Logger LOGGER = LoggerFactory.getLogger(Compiler.class);

	public static void main(String[] args) {
		
		String inputFileName = "src/test/jjtree/codegen.esp";
		try {
			TritonParser tritonParser;
			tritonParser = new TritonParser(new FileInputStream(new File(
			    inputFileName)));

			ASTStart root = tritonParser.Start();
			// root.dump(">");
			
			ResourceManager resourceManager = ResourceManager.getInstance();
			
			LogicPlanVisitor logicPlanVisitor = new LogicPlanVisitor(resourceManager);
			
			root.childrenAccept(logicPlanVisitor, resourceManager);
			//System.out.println(resourceManager.getStreamByName("s1"));

			List<BaseLogicPlan> logicPlanList = logicPlanVisitor.getLogicPlanList();

			String className = FilenameUtils.removeExtension(inputFileName);
			className = FilenameUtils.getBaseName(className);
			className = WordUtils.capitalize(className);
			CodeGenerator codeGen = new CodeGenerator(logicPlanList, className);
			
			JavaProgram program = codeGen.generate();
			
			String res = program.translate();
			
			System.out.println(res);

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
