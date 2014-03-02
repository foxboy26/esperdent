package edu.ucsd.cs.triton.operator;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cs.triton.expression.Attribute;

public class Aggregation extends BasicOperator {
	private List<Aggregator> _aggregatorList;
	private List<Attribute> _groupByList;
	
	public Aggregation() {
		_type = OperatorType.AGGREGATION;
		_groupByList = new ArrayList<Attribute> ();
		_aggregatorList = new ArrayList<Aggregator> ();
	}

	public boolean addGroupByAttribute(Attribute attribute) {
		return _groupByList.add(attribute);
	}

	public boolean addAggregator(final Aggregator aggregator) {
		return _aggregatorList.add(aggregator);
	}
	
	public List<Aggregator> getAggregatorList() {
		return _aggregatorList;
	}
	
	public List<Attribute> getGroupByList() {
		return _groupByList;
	}
	
	public boolean containsGroupBy() {
		return _groupByList.size() > 0;
	}
	
	public boolean isEmpty() {
		return _aggregatorList.isEmpty() || _groupByList.isEmpty();
	}
}
