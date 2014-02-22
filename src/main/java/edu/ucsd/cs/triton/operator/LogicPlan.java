package edu.ucsd.cs.triton.operator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.ucsd.cs.triton.expression.Attribute;
import edu.ucsd.cs.triton.resources.BaseDefinition;
import edu.ucsd.cs.triton.resources.ResourceManager;

public class LogicPlan {
	
	private Map<String, String> _renameTable;
	private Set<String> _relations;
	
	private Map<String, BasicOperator> _inputStreams;
	private Projection _projection;
	private Selection _selection;
	private List<Aggregator> _aggregatorList;
	private List<Attribute> _groupByList;
	
	public LogicPlan() {
		_renameTable = new HashMap<String, String> ();
		_inputStreams = new HashMap<String, BasicOperator> ();
		_relations = new HashSet<String> ();
		_projection = new Projection();
		_aggregatorList = new ArrayList<Aggregator> ();
		_groupByList = new ArrayList<Attribute> ();
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
	
	
	
	/**
	 * 
	 * @param attribute
	 * @return String[2] = {stream, attribute}
	 */
	public String[] unifiyAttribute(final String attribute) {
		String[] res = attribute.split(".");
		
		ResourceManager resourceManager = ResourceManager.getInstance();
		
		// "attr" only
		if (res.length == 1) {
			String attributeName = res[0];
			boolean isFound = false;
			final Map<String, BaseDefinition> definitions = resourceManager.getDefinitions();
			for (Entry<String, BaseDefinition> definition : definitions.entrySet()) {
				if (definition.getValue().containsAttribute(res[0])) {
					res = new String[2];
					res[0] = definition.getKey();
					res[1] = attributeName;
					isFound = true;
					break;
				}
			}
			
			if (!isFound) {
				System.err.println("attribute [" + attributeName + "] not found!");
			}
		} else if (res.length == 2) {
			// "s.id"
			String name = unifiyDefinitionId(res[0]);
			if (name == null) {
				System.err.println("stream def [" + name + "] not found!");
			}
			
			BaseDefinition definition = resourceManager.getDefinitionByName(name);
			if (definition.containsAttribute(res[1])) {
				res[0] = name;
				return res;
			} else {
				System.err.println("attribute [" + res[1] + "] not found in [" + name + "]");
			}
		} else {
			System.err.println("error format in attribute!");
		}
		
		return res;
	}

	public void setProjection(Projection projectionOperator) {
	  // TODO Auto-generated method stub
	  _projection = projectionOperator;
  }
	
	public Projection getProjection() {
		return _projection;
	}
	
	public boolean addGroupByAttribute(Attribute attribute) {
		return _groupByList.add(attribute);
	}
	
	public boolean addAggregator(final Aggregator aggregator) {
		return _aggregatorList.add(aggregator);
	}
	
	public void generatePlan() {
		//TODO
	}

	public Selection getSelection() {
	  // TODO Auto-generated method stub
	  return _selection;
  }
}