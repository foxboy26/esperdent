package edu.ucsd.cs.triton.operator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.ucsd.cs.triton.resources.BaseDefinition;
import edu.ucsd.cs.triton.resources.ResourceManager;

public class LogicPlan {
	private Map<String, String> _renameTable;
	private Set<String> _relations;
	private Map<String, BasicOperator> _inputStreams;
	private Projection _projection;
	public LogicPlan() {
		_renameTable = new HashMap<String, String> ();
		_inputStreams = new HashMap<String, BasicOperator> ();
		_relations = new HashSet<String> ();
		_projection = null;
	}
	
	public boolean addInputStream(final String name, final BasicOperator inputStream) {
		return _inputStreams.put(name, inputStream) != null;
	}
	
	public boolean addRelation(final String name) {
		return _relations.add(name);
	}
	
	public String addRenameDefinition(final String name, final String rename) {
		return _renameTable.put(rename, name);
	}
	
	public String lookupRename(final String rename) {
		return _renameTable.get(rename);
	}
	
	public boolean containsDefinition(final String name) {
		return _inputStreams.containsKey(name);
	}

	public Set<String> getInputStreams() {
		return _inputStreams.keySet();
	}
	
	public String unifiyDefinitionId(final String id) {
		// check rename, if exists replace it by the original name
		String originalId = _renameTable.get(id);
		if (originalId != null) {
			return originalId;
		} else if (containsDefinition(id)) {
			return id;
		} else {
			return null;
		}
	}
	
	public Attribute unifiyAttribute(final String attribute) {
		String[] res = attribute.split(".");
		String name = unifiyDefinitionId(res[0]);
		if (name == null) {
			System.err.println("stream def [" + name + "] not found!");
		}
		
		Attribute attr = null;
		ResourceManager resourceManager = ResourceManager.getInstance();
		BaseDefinition definition = resourceManager.getDefinitionByName(name);
		if (definition.containsAttribute(res[1])) {
			attr = new Attribute(name, res[1]);
		} else {
			System.err.println("attribute [" + res[1] + "] not found in [" + name + "]");
		}
			
		return attr;
	}

	public void addProjection(Projection projectionOperator) {
	  // TODO Auto-generated method stub
	  _projection = projectionOperator;
  }
	
	public void generatePlan() {
		
	}
}
