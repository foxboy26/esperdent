package edu.ucsd.cs.triton.codegen.language;

import java.util.List;

public class ImportStatement extends BaseJavaStatement {
	
	public ImportStatement add(final String importString) {
		_children.add(new SingleImportStatement(importString));
		return this;
	}
	
	public ImportStatement add(final List<String> importList) {
		for (String importString : importList) {
			_children.add(new SingleImportStatement(importString));
		}
		
		return this;
	} 
	
	public JavaProgram endImport() {
		return (JavaProgram) _parent;
	}

	@Override
  protected void translate(String prefix, StringBuilder sb) {
	  // TODO Auto-generated method stub
	  childrenTranslate(prefix, sb);
	  sb.append("\n");
  }
}
