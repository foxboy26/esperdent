package edu.ucsd.cs.triton.codegen.language;

public class JavaProgram {
	
	private String _programName;
	
	public JavaProgram(String programName) {
		_programName = programName;
	}

	public ImportStatement newImport() {
		return new ImportStatement(this);
	}
}
