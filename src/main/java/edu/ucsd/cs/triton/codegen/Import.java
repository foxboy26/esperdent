package edu.ucsd.cs.triton.codegen;

public final class Import {
	public static final String[] DEFAULT = new String[] {
		"edu.ucsd.cs.triton.codegen.SimpleQuery", 
    "edu.ucsd.cs.triton.spout.TwitterSpout",
    "edu.ucsd.cs.triton.util.PrintFilter",
    "edu.ucsd.cs.triton.window.FixedLengthSlidingWindow",
    "edu.ucsd.cs.triton.window.SlidingWindowUpdater",
    "storm.trident.tuple.TridentTuple"
  };
	
	public static final String[] FILTER = new String[] {
		"storm.trident.operation.Filter",
		"storm.trident.operation.TridentOperationContext",
		"java.util.Map"
	};

	public static final String[] EACH = new String[] {
			"storm.trident.operation.BaseFunction",
			"storm.trident.operation.TridentCollector",
			"backtype.storm.tuple.Values"
	};
	
	private Import() {}
}
