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
  protected void translate(String prefix, LanguageBuilder sb) {
	  // TODO Auto-generated method stub
	  sb.append(Keyword.CLASS).space()
	  	.append(_className).space()
	  	.append('{').newline();
	  
	  childrenTranslate(prefix + PrintStyle.INDENT, sb);
	  
	  sb.append('}').newline();
  }

	public JavaProgram endClass() {
	  return (JavaProgram) _parent;
  }

}
