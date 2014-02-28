package edu.ucsd.cs.triton.codegen.language;

public class ClassStatement extends BlockStatement {

	private final String _className;
	
	public ClassStatement(final String className) {
		_className = className;
	}
	
	@Override
  protected void translate(int n, LanguageBuilder sb) {
	  // TODO Auto-generated method stub
	  sb.indent(n)
	  	.append(Keyword.CLASS).space()
	  	.append(_className).beginBlock();
	  
	  childrenTranslate(n + PrintStyle.INDENT, sb);
	  
	  sb.indent(n)
	  	.endBlock();
	  sb.newline();
  }


}
