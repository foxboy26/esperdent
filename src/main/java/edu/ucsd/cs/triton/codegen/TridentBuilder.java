package edu.ucsd.cs.triton.codegen;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import edu.ucsd.cs.triton.codegen.language.Keyword;

public final class TridentBuilder {
	
	public static String newFunction(String func, String... args) {
		return Keyword.NEW + " " + func + "(" + StringUtils.join(args, ", ") + ")"; 
	}
	
	public static String newFunction(String func, Object[] args) {
		return Keyword.NEW + " " + func + "(" + StringUtils.join(args, ", ") + ")"; 
	}
	
	public static String newFunction(String func, Collection args) {
		return Keyword.NEW + " " + func + "(" + StringUtils.join(args, ", ") + ")"; 
	}
	
	public static String newString(String arg) {
		return "\"" + arg + "\"";
	}
	
	/**
	 * 
	 * @param args
	 * @return each(arg1, arg2, ...)
	 */
	public static String each(String... args) {
		return newTridentFunction("each", args);
	}
	
	public static String groupby(String... args) {
		return newTridentFunction("groupby", args);
	}
	
	public static String newStream(String... args) {
		return newTridentFunction("newStream", args); 
	}
	
	public static String newValuesStream(String... args) {
		return newTridentFunction("newValuesStream", args);
	}
	
	public static String partitionAggregate(String... args) {
		return newTridentFunction("partitionAggregate", args);
	}	

	public static String partitionPersist(String... args) {
		return newTridentFunction("partitionPersist", args);
	}
	
	public static String aggregate(String... args) {
		return newTridentFunction("aggregate", args);
	}
	
	public static String chainedAgg(String... args) {
		return newTridentFunction("chainedAgg", args);
	}
	
	public static String chainEnd(String... args) {
		return newTridentFunction("chainEnd", args);
	}
	
	public static String project(String fields) {
		return newTridentFunction("project", fields);
	}
	
	public static String stateQuery(String... args) {
		return newTridentFunction("stateQuery", args);
	}
	
	public static String newValues(String... args) {
		return newFunction("Values", args);
	}
	
	public static String newValues(Collection args) {
		return newFunction("Values", args);
	}
	
	/**
	 * 
	 * @param args
	 * @return new Fields("f1", "f2", "f3")
	 */
	public static String newFields(Object[] args) {
		return newStringValueFunction("Fields", args);
	}
	
	/**
	 * 
	 * @param args
	 * @return new Fields("f1", "f2", "f3")
	 */
	public static String newFields(Collection args) {
		return newStringValueFunction("Fields", args);
	}
	
	/**
	 * 
	 * @param args
	 * @return new Fields("f1", "f2", "f3")
	 */
	public static String newFields(String... args) {
		return newStringValueFunction("Fields", args);
	}
	
	
	private static String newTridentFunction(String func, String... args) {
		return "." + func + "(" + StringUtils.join(args, ", ") + ")\n"; 
	}
	
	private static String newStringValueFunction(String func, String... args) {
		return Keyword.NEW + " " + func + "(\"" + StringUtils.join(args, "\", \"") + "\")"; 
	}
	
	private static String newStringValueFunction(String func, Object[] args) {
		return Keyword.NEW + " " + func + "(\"" + StringUtils.join(args, "\", \"") + "\")"; 
	}
	
	private static String newStringValueFunction(String func, Collection args) {
		return Keyword.NEW + " " + func + "(\"" + StringUtils.join(args, "\", \"") + "\")"; 
	}
}
