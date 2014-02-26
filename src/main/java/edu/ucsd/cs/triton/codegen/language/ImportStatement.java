package edu.ucsd.cs.triton.codegen.language;

import java.util.ArrayList;
import java.util.List;

public class ImportStatement {
	
	private JavaProgram _program;
	
	private List<String> _importList;
	
	public ImportStatement(JavaProgram program) {
		_program = program;
		_importList = new ArrayList<String> ();
	}
	
	public ImportStatement add(final String importString) {
		_importList.add(importString);
		return this;
	}
	
	public ImportStatement add(final List<String> importList) {
		for (String importString : importList) {
			_importList.add(importString);
		}
		
		return this;
	} 
	
	public JavaProgram endImport() {
		return _program;
	}
}
