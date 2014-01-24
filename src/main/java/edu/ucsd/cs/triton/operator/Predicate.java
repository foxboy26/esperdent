package edu.ucsd.cs.triton.operator;

public class Predicate {
	int op;
	Predicate left;
	Predicate right;
	
	Predicate(int op) {
		this.op = op;
	}
}
