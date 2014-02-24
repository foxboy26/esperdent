package edu.ucsd.cs.triton.operator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ucsd.cs.triton.expression.Attribute;
import edu.ucsd.cs.triton.expression.AttributeExpression;
import edu.ucsd.cs.triton.expression.BooleanExpression;
import edu.ucsd.cs.triton.expression.ComparisonExpression;
import edu.ucsd.cs.triton.expression.LogicExpression;
import edu.ucsd.cs.triton.expression.LogicOperator;
import edu.ucsd.cs.triton.resources.BaseDefinition;
import edu.ucsd.cs.triton.resources.ResourceManager;

public class LogicPlan {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogicPlan.class);
	
	private Map<String, String> _renameTable;
	private Set<String> _relations;

	private Map<String, BasicOperator> _inputStreams;
	private Projection _projection;
	private Selection _selection;
	private Aggregation _aggregation;

	private JoinPlan _joinPlan;
	
	public LogicPlan() {
		_renameTable = new HashMap<String, String>();
		_inputStreams = new HashMap<String, BasicOperator>();
		_relations = new HashSet<String>();
		_projection = new Projection();
		_selection = new Selection();
		_aggregation = new Aggregation();
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

	public Selection getSelection() {
		// TODO Auto-generated method stub
		return _selection;
	}

	/**
	 * join detect
	 */
	public BasicOperator rewriteJoin() {
		BooleanExpression filter = _selection.getFilter();

		if (filter instanceof ComparisonExpression) {
			return null;
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

		// set new filter into selection operator
		if (newFilterList.isEmpty()) {
			_selection.setFilter(null);
		} else {
			_selection.setFilter(LogicExpression.fromAndList(newFilterList));
		}
		
		// set local filter for each definition
		for (Map.Entry<String, List<BooleanExpression>> entry : localSelectionMap.entrySet()) {
			BasicOperator op = _inputStreams.get(entry.getKey());
			Selection selection = new Selection(LogicExpression.fromAndList(entry.getValue()));
			selection.addChild(op, 0);
			op.setParent(selection);
			_inputStreams.put(entry.getKey(), selection);
		}
		
		// create join
		// step 2: partition graph into join cluster
		List<List<String>> partition = _joinPlan.getPartition();
		
		LOGGER.info("Find partition: " + partition);
		
		// build join operator
		BasicOperator plan = constructProduct(partition);
		
		return plan;
	}
	
	public BasicOperator generatePlan() {
		
		LOGGER.info("Generating plan...");
		
		BasicOperator logicPlan = null;
		
		if (_inputStreams.size() == 1) {
			logicPlan = _inputStreams.values().iterator().next();
		} else {
			logicPlan = new Product();
			int i = 0;
			for (BasicOperator op : _inputStreams.values()) {
				logicPlan.addChild(op, i++);
				op.setParent(logicPlan);
			}
		}
		
		if (_selection.getFilter() != null && _inputStreams.size() > 1) {
			logicPlan = rewriteJoin();
		}
		//order: projection
		//           |
		//       aggregation (group by)
		//           |
		//        selection
		//           |
		//         join
		
		// selection
		if (!_selection.isEmpty()) {
			_selection.addChild(logicPlan, 0);
			logicPlan.setParent(_selection);
			logicPlan = _selection;
		}
		
		// aggregation
		if (!_aggregation.isEmpty()) {
			_aggregation.addChild(logicPlan, 0);
			logicPlan.setParent(_aggregation);
			logicPlan = _aggregation;
		}	
		
		// projection
		if (_projection != null) {
			_projection.addChild(logicPlan, 0);
			logicPlan.setParent(_projection);
			logicPlan = _projection;
		}
		
		return logicPlan;
	}

	public void dump() {
		System.out.println("rename table: " + _renameTable);
		System.out.println("input stream: " + _inputStreams);
		System.out.println("relations: " + _relations);
		System.out.println("projection: " + _projection);
		System.out.println("selection: ");
		_selection.dump();
		System.out.println("aggregation: " + _aggregation);
	}

	public Aggregation getAggregation() {
	  // TODO Auto-generated method stub
	  return _aggregation;
  }
	

	private BasicOperator constructProduct(final List<List<String>> partition) {
		BasicOperator product = constructJoin(partition.get(0));
		for (int i = 1; i < partition.size(); i++) {
			Product newProduct = new Product();
			newProduct.addChild(product, 0);
			product.setParent(newProduct);
			BasicOperator right = constructJoin(partition.get(i));
			newProduct.addChild(right, 1);
			right.setParent(newProduct);
			product = newProduct;
		}
		
		return product;
	}
	
	private BasicOperator constructJoin(final List<String> joinList) {
		BasicOperator join = _inputStreams.get(joinList.get(0));
		for (int i = 1; i < joinList.size(); i++) {
			String leftStream;
			String rightStream = joinList.get(i);
			if (i == 1) {
				leftStream = joinList.get(0);
			} else {
				leftStream = ((Join) join).getOutputDefinition();
			}
			Join newJoin = new Join(leftStream, rightStream);
			List<KeyPair> joinFields = _joinPlan.getJoinFields(joinList.get(i-1), joinList.get(i));
			newJoin.setJoinFields(joinFields);
			newJoin.addChild(join, 0);
			join.setParent(newJoin);
			BasicOperator right = _inputStreams.get(rightStream);
			newJoin.addChild(right, 1);
			right.setParent(newJoin);
			join = newJoin;
		}
		
		return join;
	}
}