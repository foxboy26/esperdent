package edu.ucsd.cs.triton.codegen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Util {
	private Util() {
	}
	
	public static List<Integer> tsort(Map<Integer, List<Integer>> graph) {
		Set<Integer> visited = new HashSet<Integer> ();
		Deque<Integer> stack = new ArrayDeque<Integer>();
		for (int i : graph.keySet()) {
			if (!visited.contains(i)) {
				visited.add(i);
				tsortHelp(graph, visited, stack, i);
			}
		}
		
		List<Integer> res = new ArrayList<Integer> ();
		while (!stack.isEmpty()) {
			res.add(stack.pop());
		}
		return res;
	}
	
	private static void tsortHelp(Map<Integer, List<Integer>> graph, Set<Integer> visited, Deque<Integer> stack, int i) {
		List<Integer> neighbor = graph.get(i);
		for (int n : neighbor) {
			if (!visited.contains(i)) {
				tsortHelp(graph, visited, stack, n);
			}
		}
		stack.push(i);
	}
}
