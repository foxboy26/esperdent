package edu.ucsd.cs.triton.codegen;

import java.util.Map;

public class Import {
	private static final String[] DEFAULT_IMPORT_LIST = new String[] {
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

	private Map<String, String> dynamicImportList;
	// "edu.ucsd.cs.triton.window.FixedLengthSlidingWindow"
	// "edu.ucsd.cs.triton.window.SlidingWindowUpdater"
	
	
	// "storm.trident.operation.builtin.Count",
	// "edu.ucsd.cs.triton.builtin.Max

	public Import() {
		DYNAMIC_IMPORT_LIST = new 
	}
}
