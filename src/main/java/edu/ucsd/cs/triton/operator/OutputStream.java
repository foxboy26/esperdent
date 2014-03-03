package edu.ucsd.cs.triton.operator;

public class OutputStream extends BasicOperator {
	private static final String STDOUT = "stdout";
	private final String _fileName;
	
	public OutputStream(final String fileName) {
		_type = OperatorType.OUTPUT_STREAM;
		_fileName = fileName;
	}
	
	public String getFileName() {
		return _fileName;
	}

	public static OutputStream newStdoutStream() {
	  // TODO Auto-generated method stub
	  return new OutputStream(STDOUT);
  }
	
	public boolean isStdout() {
		return _fileName != null && _fileName.equals(STDOUT);
	}
	
  /** Accept the visitor. **/
  @Override
	public Object accept(OperatorVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
