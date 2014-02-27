package edu.ucsd.cs.triton.codegen.language;

public class ClassStatement extends BaseJavaStatement {

	private JavaProgram _program;
	private final String _className;
	
	public ClassStatement(JavaProgram program, final String className) {
		_program = program;
		_className = className;
	}
	
	@Override
  protected void translate(String prefix, StringBuilder sb) {
	  // TODO Auto-generated method stub
	  sb.append("class ")
	  	.append(_className)
	  	.append(" {\n");
	  
	  childrenTranslate(prefix + PrintStyle.INDENT, sb);
	  
	  sb.append("}\n");
  }

	public JavaProgram endClass() {
	  // TODO Auto-generated method stub
		_program.addChild(this);
	  return _program;
  }

}
