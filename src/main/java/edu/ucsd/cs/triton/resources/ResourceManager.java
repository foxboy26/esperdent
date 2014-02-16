package edu.ucsd.cs.triton.resources;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
	private Map<String, StreamDefinition> _streamDefinitions;
	private Map<String, RelationDefinition> _relationDefinitions;
	
	private static final ResourceManager INSTANCE = new ResourceManager();

	private static final String UNNAMED_PRIFEX = "unnamed";
	
	private static int unnamedCount = 0;
	
	private ResourceManager() {
		this._streamDefinitions = new HashMap<String, StreamDefinition> ();
		this._relationDefinitions = new HashMap<String, RelationDefinition> ();
	}
	
	public static ResourceManager getInstance() {
		return INSTANCE;
	}
	
	public boolean addStream(final StreamDefinition stream) {
		return _streamDefinitions.put(stream.getName(), stream) != null;
	}
	
	public boolean addRelation(final RelationDefinition stream) {
		return _relationDefinitions.put(stream.getName(), stream) != null;
	}

	public StreamDefinition getStreamByName(final String name) {
		return _streamDefinitions.get(name);
	}
	
	public RelationDefinition getRelationByName(final String name) {
		return _relationDefinitions.get(name);
	}
	
	public String allocateUnnamedStream() {
	  // TODO Auto-generated method stub
	  return (UNNAMED_PRIFEX + (unnamedCount++));
  }
}
