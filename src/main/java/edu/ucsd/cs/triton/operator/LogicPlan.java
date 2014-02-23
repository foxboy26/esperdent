package edu.ucsd.cs.triton.operator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.ucsd.cs.triton.expression.Attribute;
import edu.ucsd.cs.triton.expression.AttributeExpression;
import edu.ucsd.cs.triton.expression.BooleanExpression;
import edu.ucsd.cs.triton.expression.ComparisonExpression;
import edu.ucsd.cs.triton.expression.LogicExpression;
import edu.ucsd.cs.triton.expression.LogicOperator;
import edu.ucsd.cs.triton.resources.BaseDefinition;
import edu.ucsd.cs.triton.resources.ResourceManager;

public class LogicPlan {

	private Map<String, String> _renameTable;
	private Set<String> _relations;

	private Map<String, BasicOperator> _inputStreams;
	private Projection _projection;
	private Selection _selection;
	private List<Aggregator> _aggregatorList;
	private List<Attribute> _groupByList;

	private JoinPlan _joinPlan;
	
	public LogicPlan() {
		_renameTable = new HashMap<String, String>();
		_inputStreams = new HashMap<String, BasicOperator>();
		_relations = new HashSet<String>();
		_projection = new Projection();
		_selection = new Selection();
		_aggregatorList = new ArrayList<Aggregator>();
		_groupByList = new ArrayList<Attribute>();
		_joinPlan = new JoinPlan();
	}

	public boolean addInputStream(final String name,
	    final BasicOperator inputStream) {
		return _inputStreams.put(name, inputStream) != null;
	}

	public boolean addRelation(final String name) {
		return _relations.add(name);
	}

	public String addRenameDefinition(final String name, final String rename) {
		return _renameTable.put(rename, name);
	}

	public String lookupRename(final String rename) {
		return _renameTable.get(rename);
	}

	public boolean containsDefinition(final String name) {
		return _inputStreams.containsKey(name);
	}

	public Set<String> getInputStreams() {
		return _inputStreams.keySet();
	}

	public String unifiyDefinitionId(final String id) {
		// check rename, if exists replace it by the original name
		String originalId = _renameTable.get(id);
		if (originalId != null) {
			return originalId;
		} else if (containsDefinition(id)) {
			return id;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param attribute
	 * @return String[2] = {stream, attribute}
	 */
	public String[] unifiyAttribute(final String attribute) {
		String[] res = attribute.split("\\.");

		ResourceManager resourceManager = ResourceManager.getInstance();

		// "attr" only
		if (res.length == 1) {
			String attributeName = attribute;
			boolean isFound = false;
			final Map<String, BaseDefinition> definitions = resourceManager
			    .getDefinitions();
			for (Entry<String, BaseDefinition> definition : definitions.entrySet()) {
				if (definition.getValue().containsAttribute(attributeName)) {
					res = new String[2];
					res[0] = definition.getKey();
					res[1] = attributeName;
					isFound = true;
					break;
				}
			}

			if (!isFound) {
				System.err.println("attribute [" + attributeName + "] not found!");
			}
		} else if (res.length == 2) {
			// "s.id"
			String name = unifiyDefinitionId(res[0]);
			if (name == null) {
				System.err.println("stream def [" + name + "] not found!");
			}

			BaseDefinition definition = resourceManager.getDefinitionByName(name);
			if (definition.containsAttribute(res[1])) {
				res[0] = name;
				return res;
			} else {
				System.err.println("attribute [" + res[1] + "] not found in [" + name
				    + "]");
			}
		} else {
			System.err.println("error format in attribute!");
		}

		return res;
	}

	public void setProjection(Projection projectionOperator) {
		// TODO Auto-generated method stub
		_projection = projectionOperator;
	}

	public Projection getProjection() {
		return _projection;
	}

	public boolean addGroupByAttribute(Attribute attribute) {
		return _groupByList.add(attribute);
	}

	public boolean addAggregator(final Aggregator aggregator) {
		return _aggregatorList.add(aggregator);
	}

	public Selection getSelection() {
		// TODO Auto-generated method stub
		return _selection;
	}

	/**
	 * join detect
	 */
	public void rewriteJoin() {
		BooleanExpression filter = _selection.getFilter();

		if (filter instanceof ComparisonExpression) {
			return;
		}

		List<BooleanExpression> andList = ((LogicExpression) filter).toAndList();
		List<BooleanExpression> newFilterList = new ArrayList<BooleanExpression>();
		Map<String, List<BooleanExpression>> localSelectionMap = new HashMap<String, List<BooleanExpression>>();
		
		for (BooleanExpression boolExp : andList) {

			if (boolExp.isFromSameDefiniton()) {
				// TODO: push down to stream selection
				String definition = boolExp.getDefinition();
				if (localSelectionMap.containsKey(definition)) {
					List<BooleanExpression> localFilterList = localSelectionMap
					    .get(definition);
					localFilterList.add(boolExp);
				} else {
					List<BooleanExpression> localFilterList = new ArrayList<BooleanExpression>();
					localFilterList.add(boolExp);
					localSelectionMap.put(definition, localFilterList);
				}
			} else {
				if (boolExp instanceof ComparisonExpression) {
					ComparisonExpression cmpExp = (ComparisonExpression) boolExp;
					if (cmpExp.isJoinExpression()) {
						// add key pair to join plan
						Attribute left = ((AttributeExpression) cmpExp.getLeft())
						    .getAttribute();
						Attribute right = ((AttributeExpression) cmpExp.getRight())
						    .getAttribute();

						_joinPlan.addKeyPair(new KeyPair(left, right));
					} else {
						// insert into new Selection
						newFilterList.add(cmpExp);
					}
				} else if (boolExp instanceof LogicExpression) {
					LogicExpression logicExp = (LogicExpression) boolExp;
					if (logicExp.getOperator() != LogicOperator.OR) {
						System.err.println("error in rewrite join, and appear!");
						System.exit(1);
					} else {
						// insert into new Selection
						newFilterList.add(logicExp);
					}
				}
			}
		}

		// set local filter for each definition
		for (Map.Entry<String, List<BooleanExpression>> entry : localSelectionMap.entrySet()) {
			BasicOperator op = _inputStreams.get(entry.getKey());
			Selection selection = new Selection(LogicExpression.fromAndList(entry.getValue()));
			selection.addChild(op, 0);
			op.setParent(selection);
			op = selection;
		}
		
		// create join
		// step 2: partition graph into join cluster
		List<List<String>> partition = _joinPlan.getPartition();
		
		System.out.println(partition);
		
		// build join operator
		BasicOperator product = constructProduct(partition);
		product.dump("");
		
		// set new filter into selection operator
		_selection.setFilter(LogicExpression.fromAndList(newFilterList));
	}

	private BasicOperator constructProduct(final List<List<String>> partition) {
		BasicOperator product = constructJoin(partition.get(0));
		for (int i = 1; i < partition.size(); i++) {
			Product newProduct = new Product();
			newProduct.addChild(product, 0);
			BasicOperator right = constructJoin(partition.get(i));
			newProduct.addChild(right, 1);
			product = newProduct;
		}
		
		return product;
	}
	
	private BasicOperator constructJoin(final List<String> joinList) {
		BasicOperator join = _inputStreams.get(joinList.get(0));
		for (int i = 1; i < joinList.size(); i++) {
			Join newJoin = new Join();
			List<KeyPair> joinFields = _joinPlan.getJoinFields(joinList.get(i-1), joinList.get(i));
			newJoin.setJoinFields(joinFields);
			newJoin.addChild(join, 0);
			BasicOperator right = _inputStreams.get(joinList.get(i));
			newJoin.addChild(right, 1);
			join = newJoin;
		}
		
		return join;
	}
	
	public void generatePlan() {
		// TODO
		rewriteJoin();
		
		//order: projection
		//           |
		//       aggregation (group by)
		//           |
		//        selection
		//           |
		//         join
		
	}

	public void dump() {
		System.out.println("rename table: " + _renameTable);
		System.out.println("input stream: " + _inputStreams);
		System.out.println("relations: " + _relations);
		System.out.println("projection: " + _projection);
		System.out.println("selection: ");
		_selection.dump();
		System.out.println("aggregate list:" + _aggregatorList);
		System.out.println("groupby list:" + _groupByList);
	}
}