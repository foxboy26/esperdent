package edu.ucsd.cs.triton.resources;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
	private Map<String, BaseDefinition> _definitions;
	
	private static final ResourceManager INSTANCE = new ResourceManager();

	private static final String UNNAMED_PRIFEX = "unnamed";
	
	private static int unnamedCount = 0;
	
	private ResourceManager() {
		this._definitions = new HashMap<String, BaseDefinition> ();
	}
	
	public static ResourceManager getInstance() {
		return INSTANCE;
	}
	
	public boolean addStream(final StreamDefinition stream) {
		return _definitions.put(stream.getName(), stream) != null;
	}
	
	public boolean addRelation(final RelationDefinition stream) {
		return _definitions.put(stream.getName(), stream) != null;
	}

	public StreamDefinition getStreamByName(final String name) {
		BaseDefinition def = _definitions.get(name);
		
		return (def instanceof StreamDefinition)? (StreamDefinition) def : null;
	}
	
	public RelationDefinition getRelationByName(final String name) {
		BaseDefinition def = _definitions.get(name);
		
		return (def instanceof RelationDefinition)? (RelationDefinition) def : null;
	}
	
	public BaseDefinition getDefinitionByName(final String name) {
		return _definitions.get(name);
	}
	
	public boolean containsStream(final String name) {
		return _definitions.containsKey(name);
	}
	
	public boolean containsRelation(final String name) {
		return _definitions.containsKey(name);
	}
	
	public String allocateUnnamedStream() {
	  // TODO Auto-generated method stub
	  return (UNNAMED_PRIFEX + (unnamedCount++));
  }
}
