package edu.ucsd.cs.triton.codegen.language;

public class ClassStatement extends BaseJavaStatement {

	private final String _className;
	
	public ClassStatement(final String className) {
		_className = className;
	}
	
	public ClassStatement addSimpleStatement(final String stmt) {
		addChild(new SimpleStatement(stmt));
		return this;
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
	  return (JavaProgram) _parent;
  }

}
