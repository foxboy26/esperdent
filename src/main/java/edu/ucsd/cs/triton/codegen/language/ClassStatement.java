package edu.ucsd.cs.triton.codegen.language;

public class ClassStatement extends BlockStatement {

	private final String _className;
	
	public ClassStatement(final String className) {
		_className = className;
	}
	
	public void addInnerClass(ClassStatement cs) {
		_children.add(cs);
	}
	
	public void addMemberFunction(MemberFunction mf) {
		_children.add(mf);
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
