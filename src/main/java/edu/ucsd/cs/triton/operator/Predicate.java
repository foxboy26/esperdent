package operator;

public class Predicate {
	int op;
	Predicate left;
	Predicate right;
	
	Predicate(int op) {
		this.op = op;
	}
}
