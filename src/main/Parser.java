package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import operator.BasicOperator;
import operator.LogicPlanVisitor;

import parser.ASTStart;
import parser.EsperdentParser;
import parser.ParseException;

public class Parser {
	public static void main(String[] args) {
		String fileName = "test.esp";

		try {
			EsperdentParser esperdentParser;
			esperdentParser = new EsperdentParser(new FileInputStream(new File(
			    fileName)));

			ASTStart root;

			root = esperdentParser.Start();
			root.dump(">");
			
			System.out.println("Logic query plan");
			System.out.println("----------------");
			
			LogicPlanVisitor logicPlanVisitor = new LogicPlanVisitor();
			BasicOperator op = (BasicOperator) root.jjtAccept(logicPlanVisitor, null);
			
			op.dump(">");
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
