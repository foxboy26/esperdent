package edu.ucsd.cs.triton.codegen.language;

import java.util.List;

public class ClassStatement extends BlockStatement {

	private final String _className;
	
	public ClassStatement(final String className) {
		_className = className;
	}
	
	public void addInnerClass(ClassStatement cs) {
		_children.add(cs);
	}
	
	public ClassStatement addInnerClass(List<ClassStatement> list) {
		for (ClassStatement cs : list) {
			_children.add(cs);
		}
		return this;
	}
	
	public ClassStatement addMemberFunction(MemberFunction mf) {
		if (mf != null) {
			_children.add(mf);
		}
		
		return this;
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

	public ClassStatement addSimpleStmt(List<String> list) {
	  // TODO Auto-generated method stub
		for (String stmt : list) {
			_children.add(new SimpleStatement(stmt));
		}
		return this;
  }


}
