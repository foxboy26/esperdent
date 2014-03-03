package edu.ucsd.cs.triton.codegen;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cs.triton.codegen.language.BlockStatement;
import edu.ucsd.cs.triton.codegen.language.ClassStatement;
import edu.ucsd.cs.triton.codegen.language.JavaProgram;
import edu.ucsd.cs.triton.codegen.language.MemberFunction;

public final class TridentProgram {
	private final String _programName;
	private List<String> _importList;
	private List<ClassStatement> _innerClassList;
	private List<String> _spoutDefList;
	private MemberFunction _buildQuery;
	private MemberFunction _defaultMain;
	
	public TridentProgram(String programName) {
	  _programName = programName;
		_importList = new ArrayList<String> ();
	  _innerClassList = new ArrayList<ClassStatement> ();
	  _spoutDefList = new ArrayList<String> ();
	  _buildQuery = new MemberFunction("public void buildQuery()");
	}
	
	public void addImport(String importString) {
		_importList.add(importString);
	}
	
	public void addInnerClass(ClassStatement cs) {
		_innerClassList.add(cs);
	}
	
	public void addSpoutDefinition(String def) {
		_spoutDefList.add(def);
	}
	
	public void addStmtToBuildQuery(String stmt) {
		_buildQuery.SimpleStmt(stmt);
	}

	public JavaProgram toJava() {
		return new JavaProgram(_programName)
			.Import()
				.add(Import.DEFAULT_IMPORT_LIST)
				.add(_importList)
			.EndImport()
			.Class()
				.addSimpleStmt(_spoutDefList)
				.addInnerClass(_innerClassList)
				.addMemberFunction(_buildQuery)
				.addMemberFunction(_defaultMain)
			.EndClass();
	}

	public void setDefaultMain(MemberFunction mainEntry) {
	  // TODO Auto-generated method stub
		_defaultMain = mainEntry; 
  }
}
