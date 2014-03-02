package edu.ucsd.cs.triton.codegen;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

public final class TridentBuilder {
	
	public static String newFunction(String func, String... args) {
		return "." + func + "(" + StringUtils.join(args, ",") + ")\n"; 
	}
	
	public static String each(String... args) {
		return newFunction("each", args);
	}
	
	public static String groupby(String... args) {
		return newFunction("groupby", args);
	}
	
	public static String newStream(String... args) {
		return newFunction("newStream", args); 
	}
	
	public static String newValuesStream(String... args) {
		return newFunction("newValuesStream", args);
	}
	
	public static String partitionAggregate(String... args) {
		return newFunction("partitionAggregate", args);
	}	

	public static String partitionPersist(String... args) {
		return newFunction("partitionPersist", args);
	}
	
	public static String aggregate(String... args) {
		return newFunction("aggregate", args);
	}
	
	public static String chainedAgg(String... args) {
		return newFunction("chainedAgg", args);
	}
	
	public static String chainEnd(String... args) {
		return newFunction("chainEnd", args);
	}
	
	public static String stateQuery(String... args) {
		return newFunction("stateQuery", args);
	}
	
	public static String newFields(Object[] args) {
		return "new Fields(\"" + StringUtils.join(args, "\",") + "\")";
	}
	
	public static String newFields(Collection args) {
		return "new Fields(\"" + StringUtils.join(args, "\",") + "\")";
	}
	
	public static String newFields(String... args) {
		return "new Fields(\"" + StringUtils.join(args, "\",") + "\")";
	}
}
