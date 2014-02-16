package edu.ucsd.cs.triton.operator;

import java.util.ArrayList;
import java.util.Set;

import parser.ASTAggregateAttribute;
import parser.ASTAggregateFunction;
import parser.ASTAttribute;
import parser.ASTAttributeDefList;
import parser.ASTAttributeDefinition;
import parser.ASTCmpOp;
import parser.ASTCondAnd;
import parser.ASTCondOr;
import parser.ASTCondPrime;
import parser.ASTCreateRelation;
import parser.ASTCreateStream;
import parser.ASTExpression;
import parser.ASTFloatingLiteral;
import parser.ASTFromClause;
import parser.ASTGroupByClause;
import parser.ASTInsertClause;
import parser.ASTInteger;
import parser.ASTName;
import parser.ASTOrderByClause;
import parser.ASTQuery;
import parser.ASTReName;
import parser.ASTSelectAttribute;
import parser.ASTSelectClause;
import parser.ASTSource;
import parser.ASTStart;
import parser.ASTStreamDef;
import parser.ASTStreamFilter;
import parser.ASTStringLiteral;
import parser.ASTTimePeriod;
import parser.ASTTypeFloat;
import parser.ASTTypeInt;
import parser.ASTTypeString;
import parser.ASTTypeTimestamp;
import parser.ASTUnits;
import parser.ASTWhereClause;
import parser.ASTWinLength;
import parser.ASTWinTimBatch;
import parser.ASTWinTime;
import parser.Node;
import parser.SimpleNode;
import parser.TritonParserVisitor;
import edu.ucsd.cs.triton.resources.AttributeType;
import edu.ucsd.cs.triton.resources.BaseDefinition;
import edu.ucsd.cs.triton.resources.DynamicSource;
import edu.ucsd.cs.triton.resources.RelationDefinition;
import edu.ucsd.cs.triton.resources.ResourceManager;
import edu.ucsd.cs.triton.resources.StaticSource;
import edu.ucsd.cs.triton.resources.StreamDefinition;

public class LogicPlanVisitor implements TritonParserVisitor {

	private ResourceManager _resourceManager;
	private ArrayList<LogicPlan> _logicPlanList;
	
	public LogicPlanVisitor(ResourceManager resourceManager) {
		_resourceManager = resourceManager;
		_logicPlanList = new ArrayList<LogicPlan> ();
	}
	
	public ArrayList<LogicPlan> getLogicPlanList() {
		return _logicPlanList;
	}
	
	@Override
  public Object visit(SimpleNode node, Object data) {
	  // TODO Auto-generated method stub
		System.err.println("you should not come here!");
		System.exit(1);
		return null;
  }

	@Override
  public Object visit(ASTStart node, Object data) {
	  // TODO Auto-generated method stub
		checkNumOfChildren(node, 1, "[Start]");
	
		Node n = node.jjtGetChild(0);
		
		if (n instanceof ASTCreateStream || n instanceof ASTCreateRelation) {
			// create table manager
			n.jjtAccept(this, data);
		} else if (n instanceof ASTQuery) {
			//TODO
			n.jjtAccept(this, data);
		} else {
			System.err.println("Not supported query.");
		}
		
		return null;
	}


	@Override
  public Object visit(ASTCreateStream node, Object data) {
	  
		checkNumOfChildren(node, 2, "[CreateStream]");
		
		StreamDefinition streamDef = new StreamDefinition(node.streamName);
		streamDef = (StreamDefinition) node.childrenAccept(this, streamDef);
		
		_resourceManager.addStream(streamDef);
		
	  return null;
  }

	@Override
  public Object visit(ASTCreateRelation node, Object data) {
		
		checkNumOfChildren(node, 2, "[CreateRelation]");

		RelationDefinition relationDef = new RelationDefinition(node.relationName);
		relationDef = (RelationDefinition) node.childrenAccept(this, relationDef);
		
		_resourceManager.addRelation(relationDef);
				
	  return null;
  }
	
	@Override
  public Object visit(ASTAttributeDefList node, Object data) {
	  // TODO Auto-generated method stub
	  return node.childrenAccept(this, data);
  }

	@Override
	/**
	 * input:  BaseDefinition
	 * output: 
	 */
  public Object visit(ASTAttributeDefinition node, Object data) {
	  // TODO Auto-generated method stub
		BaseDefinition definition = (BaseDefinition) data;

		// attribute name
		String name = (String) node.jjtGetChild(0).jjtAccept(this, data);
		// attribute type
		AttributeType type = (AttributeType) node.jjtGetChild(1).jjtAccept(this, data);
		definition.addAttribute(name, type);
		
	  return null;
  }
	
	@Override
	/**
	 * input:
	 * output: INT
	 */
  public Object visit(ASTTypeInt node, Object data) {
	  // TODO Auto-generated method stub
	  return AttributeType.INT;
  }

	@Override
  public Object visit(ASTTypeFloat node, Object data) {
	  // TODO Auto-generated method stub
	  return  AttributeType.FLOAT;
  }

	@Override
  public Object visit(ASTTypeString node, Object data) {
	  // TODO Auto-generated method stub
	  return AttributeType.STRING;
  }

	@Override
  public Object visit(ASTTypeTimestamp node, Object data) {
	  // TODO Auto-generated method stub
	  return AttributeType.TIMESTAMP;
  }

	@Override
  public Object visit(ASTSource node, Object data) {
	  // TODO Auto-generated method stub
		
		BaseDefinition definition = (BaseDefinition) data;

		int numOfChildren = node.jjtGetNumChildren();
		
		switch (numOfChildren) {
		case 0:
			// static source from database or data set
			definition.setSource(new StaticSource(node.uri));
			break;
		case 1:
			// dynamic source from a stream query result
			ResourceManager resoureManager = ResourceManager.getInstance();
			String unnamedStream = resoureManager.allocateUnnamedStream();
			definition.setSource(new DynamicSource(unnamedStream));
			break;
		default:
			System.err.println("Error in source node!");
		}
		
	  return null;
  }

	@Override
  public Object visit(ASTQuery node, Object data) {
	  // TODO Auto-generated method stub
		LogicPlan logicPlan = new LogicPlan();
		
		node.childrenAccept(this, logicPlan);
		
		logicPlan.generatePlan();
		
		_logicPlanList.add(logicPlan);
		
	  return null;
  }

	@Override
  public Object visit(ASTSelectClause node, Object data) {
	  // TODO Auto-generated method stub
		LogicPlan logicPlan = (LogicPlan) data;
		Projection projectionOperator = new Projection();

		int numOfChildren = node.jjtGetNumChildren();
		String attributeName;
		for (int i = 0; i < numOfChildren; i++) {
			Node childNode = node.jjtGetChild(i);
			if (childNode instanceof ASTAttribute) {
				attributeName = ((ASTAttribute) childNode).name;
				projectionOperator.addAttribute(logicPlan.unifiyAttribute(attributeName));
			} else if (childNode instanceof ASTAggregateAttribute) {
				
			}
		}
	  
		// expand select *
		if (numOfChildren == 0) {
			Set<String> inputStreams = logicPlan.getInputStreams();
			for (String streamName : inputStreams) {
				BaseDefinition definiton = _resourceManager.getDefinitionByName(streamName);
				Set<String> attributes = definiton.getAttributes().keySet();
				for (String attribute : attributes)
				projectionOperator.addAttribute(new Attribute(streamName, attribute));
			}
		}
		
		logicPlan.addProjection(projectionOperator);
		
		return null;
	}

	@Override
  public Object visit(ASTAttribute node, Object data) {
	  // TODO Auto-generated method stub
	  return node.name;
  }

	@Override
  public Object visit(ASTFromClause node, Object data) {
	  // TODO Auto-generated method stub
		node.childrenAccept(this, data);
		
		return null;
	}

	@Override
  public Object visit(ASTStreamDef node, Object data) {
		
		LogicPlan logicPlan = (LogicPlan) data;
		
		int numOfChildren = node.jjtGetNumChildren();
		
		// get stream name
		String name = (String) node.jjtGetChild(0).jjtAccept(this, data);
		if (!_resourceManager.containsRelation(name)) {
			System.err.println("stream def [" + name + "] not found!");
			return null;
		}
	  
		// create input stream
		InputStream inputStream = new InputStream(name);
		
		// check rename
		Node lastNode = node.jjtGetChild(numOfChildren - 1);
		int searchRange = numOfChildren;
		if (lastNode instanceof ASTReName) {
			logicPlan.addRenameDefinition(name, ((ASTReName) lastNode).rename);
			searchRange--;
		}
		
		// get pre-window filter and window spec
		BaseWindow windowOperator = null;
		Selection selectionOperator = null;
		for (int i = 1; i < searchRange; i++) {
			Node childNode = node.jjtGetChild(i);
			if (childNode instanceof ASTStreamFilter) {
				selectionOperator = (Selection) childNode.jjtAccept(this, data);
			} else if (childNode instanceof ASTWinLength || 
					       childNode instanceof ASTWinTime || 
					       childNode instanceof ASTWinTimBatch) {
				windowOperator = (BaseWindow) childNode.jjtAccept(this, data);
			} else {
				System.err.println("Err");
				return null;
			}
		}
		
		BasicOperator currentOperator = inputStream;
		if (selectionOperator != null) {
			inputStream.setParent(selectionOperator);
			currentOperator = selectionOperator;
		}
		
		if (windowOperator != null) {
			inputStream.setParent(windowOperator);
			currentOperator = windowOperator;
		}
		
		logicPlan.addInputStream(name, currentOperator);
	  
		return null;
  }
	
	@Override
  public Object visit(ASTUnits node, Object data) {
	  // TODO Auto-generated method stub
	  return Unit.valueOf(node.unit);
  }

	@Override
  public Object visit(ASTWhereClause node, Object data) {
	  // TODO Auto-generated method stub
		Selection selectionOp = new Selection();
	  return selectionOp;
  }

	@Override
  public Object visit(ASTCondAnd node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTCondOr node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTName node, Object data) {
	  // TODO Auto-generated method stub
	  return node.name;
  }

	@Override
  public Object visit(ASTInteger node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }
	
	@Override
  public Object visit(ASTStreamFilter node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTSelectAttribute node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTWinLength node, Object data) {
	  // TODO Auto-generated method stub
		int length = ((ASTInteger) node.jjtGetChild(0)).value;
	  return new FixedLengthWindow(length);
  }

	@Override
  public Object visit(ASTWinTime node, Object data) {
	  // TODO Auto-generated method stub
		long timePeriod = (Long) node.jjtGetChild(0).jjtAccept(this, data);
		
	  return new TimeWindow(timePeriod);
  }
	
	@Override
  public Object visit(ASTWinTimBatch node, Object data) {
	  // TODO Auto-generated method stub
		long timePeriod = (Long) node.jjtGetChild(0).jjtAccept(this, data);
		
	  return new TimeBatchWindow(timePeriod);
  }

	@Override
  public Object visit(ASTTimePeriod node, Object data) {
	  // TODO Auto-generated method stub
		int length = ((ASTInteger) node.jjtGetChild(0)).value;
		Unit unit = (Unit) node.jjtGetChild(1).jjtAccept(this, data);
	  return (length * unit.getValue());
  }

	@Override
  public Object visit(ASTGroupByClause node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTCondPrime node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTExpression node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTAggregateAttribute node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTOrderByClause node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTCmpOp node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTAggregateFunction node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTReName node, Object data) {
	  // TODO Auto-generated method stub
	  return node.rename;
  }

	@Override
  public Object visit(ASTStringLiteral node, Object data) {
	  // TODO Auto-generated method stub
	  return node.value;
  }

	@Override
  public Object visit(ASTFloatingLiteral node, Object data) {
	  // TODO Auto-generated method stub
	  return node.value;
  }
	
	@Override
  public Object visit(ASTInsertClause node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }
	
	
	private void checkNumOfChildren(SimpleNode node, int num, String errMsg) {
		int numOfChild = node.jjtGetNumChildren();
		if (numOfChild != num) {
			System.err.println("Error: " + errMsg + " should have " + num
					+ " children, but only have " + numOfChild);
		}
	}
}
