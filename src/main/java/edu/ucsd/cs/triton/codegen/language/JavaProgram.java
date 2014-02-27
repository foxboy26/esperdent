package edu.ucsd.cs.triton.codegen.language;

public class JavaProgram extends BaseJavaStatement {
	
	private final String _programName;
	
	public JavaProgram(final String programName) {
		_programName = programName;
	}

	public ImportStatement newImport() {
		return new ImportStatement(this);
	}

	public ClassStatement newClass() {
	  return new ClassStatement(this, _programName);
  }
	
	@Override
  protected void translate(String prefix, StringBuilder sb) {
	  // TODO Auto-generated method stub
		childrenTranslate(prefix, sb);
  }
}
