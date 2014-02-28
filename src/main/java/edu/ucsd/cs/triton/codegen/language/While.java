package edu.ucsd.cs.triton.codegen.language;

public final class While extends BlockStatement {

	private final String _condition;
	
	public While(final String condition) {
		_condition = condition;
	}
	
	@Override
  protected void translate(int n, LanguageBuilder sb) {
	  // TODO Auto-generated method stub
	  sb.indent(n)
	  	.append(Keyword.WHILE).space().append('(').append(_condition).append(')').beginBlock();
	  
	  childrenTranslate(n + PrintStyle.INDENT, sb);
	  
	  sb.indent(n).endBlock();
	}

}
