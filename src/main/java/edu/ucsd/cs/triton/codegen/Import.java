package edu.ucsd.cs.triton.codegen;

import java.util.HashMap;
import java.util.Map;

public final class Import {
	public static final String[] DEFAULT_IMPORT_LIST = new String[] {
	    "storm.trident.Stream", 
	    "storm.trident.TridentTopology",
	    "backtype.storm.Config", 
	    "backtype.storm.LocalCluster",
	    "backtype.storm.LocalDRPC", 
	    "backtype.storm.StormSubmitter",
	    "backtype.storm.generated.AlreadyAliveException",
	    "backtype.storm.generated.InvalidTopologyException",
	    "backtype.storm.generated.StormTopology", 
	    "backtype.storm.tuple.Fields",
	    "edu.ucsd.cs.triton.util.PrintFilter" };

	public static final Map<String, String[]> IMPORT_LIST;
	
	// "edu.ucsd.cs.triton.window.FixedLengthSlidingWindow"
	// "edu.ucsd.cs.triton.window.SlidingWindowUpdater"
	
	
	// "storm.trident.operation.builtin.Count",
	// "edu.ucsd.cs.triton.builtin.Max

	static
  {
		IMPORT_LIST = new HashMap<String, String[]>();
		
		IMPORT_LIST.put("default", new String[] {
    	    "storm.trident.Stream", 
    	    "storm.trident.TridentTopology",
    	    "backtype.storm.Config", 
    	    "backtype.storm.LocalCluster",
    	    "backtype.storm.LocalDRPC", 
    	    "backtype.storm.StormSubmitter",
    	    "backtype.storm.generated.AlreadyAliveException",
    	    "backtype.storm.generated.InvalidTopologyException",
    	    "backtype.storm.generated.StormTopology", 
    	    "backtype.storm.tuple.Fields",
    	    "edu.ucsd.cs.triton.util.PrintFilter" });
		
		IMPORT_LIST.put("each", new String[] {
				"storm.trident.operation.BaseFunction",
				"storm.trident.operation.TridentCollector",
				"backtype.storm.tuple.Values"
		});
		
		IMPORT_LIST.put("filter", new String[] {
				"storm.trident.operation.Filter",
				"storm.trident.operation.TridentOperationContext"
		});
  }
	
	public static String[] getDefault() {
		return IMPORT_LIST.get("default");
	}
	
	private Import() {}
}
