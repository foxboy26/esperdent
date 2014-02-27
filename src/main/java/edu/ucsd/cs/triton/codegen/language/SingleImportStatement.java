package edu.ucsd.cs.triton.codegen.language;

public class SingleImportStatement extends BaseJavaStatement {

	private final String _importString; 
	
	public SingleImportStatement(String importString) {
		_importString = importString;
	}

	@Override
  protected void translate(String prefix, StringBuilder sb) {
	  // TODO Auto-generated method stub
	  sb.append(prefix)
	  	.append("import ")
	  	.append(_importString)
	  	.append(";\n");
  }

}
