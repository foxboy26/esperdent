package edu.ucsd.cs.triton.codegen;

public final class Import {
	public static final String[] DEFAULT = new String[] {
		"edu.ucsd.cs.triton.builtin.*",
    "edu.ucsd.cs.triton.spout.*",
		"edu.ucsd.cs.triton.codegen.SimpleQuery",
    "edu.ucsd.cs.triton.util.PrintFilter",
    "edu.ucsd.cs.triton.window.FixedLengthSlidingWindow",
    "edu.ucsd.cs.triton.window.SlidingWindowUpdater",
    "storm.trident.tuple.TridentTuple",
    "storm.trident.Stream",
    "storm.trident.operation.builtin.*",
    "backtype.storm.tuple.Fields",
		"backtype.storm.tuple.Values"
  };
	
	public static final String[] FILTER = new String[] {
		"storm.trident.operation.Filter",
		"storm.trident.operation.TridentOperationContext",
		"java.util.Map"
	};

	public static final String[] EACH = new String[] {
			"storm.trident.operation.BaseFunction",
			"storm.trident.operation.TridentCollector"
	};
	
	private Import() {}
}
