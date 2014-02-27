package edu.ucsd.cs.triton.codegen.language;

public class SimpleStatement extends BaseJavaStatement {

	private final String _stmt;
	
	public SimpleStatement(final String stmt) {
		_stmt = stmt;
	}
	
	@Override
  protected void translate(String prefix, StringBuilder sb) {
	  // TODO Auto-generated method stub
	  sb.append(_stmt + ";\n");
  }

}
