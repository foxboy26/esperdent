package edu.ucsd.cs.trition.operator.language;

import edu.ucsd.cs.triton.codegen.language.JavaProgram;

public class TestJavaProgram {
	public static void main(String[] args) {
		JavaProgram program = new JavaProgram("name");
		
		program
			.newImport()
				.add("hahah")
				.add("lolo")
			.endImport();
	}
}
