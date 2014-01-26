package edu.ucsd.cs.triton.operator;

import parser.ASTAggregateAttribute;
import parser.ASTAggregateFunction;
import parser.ASTAttribute;
import parser.ASTAttributeDefList;
import parser.ASTAttributeDefinition;
import parser.ASTAttributeType;
import parser.ASTCmpOp;
import parser.ASTCondAnd;
import parser.ASTCondOr;
import parser.ASTCondPrime;
import parser.ASTCreateClause;
import parser.ASTExpression;
import parser.ASTFloatingLiteral;
import parser.ASTFromClause;
import parser.ASTFromList;
import parser.ASTGroupByClause;
import parser.ASTInsertClause;
import parser.ASTInteger;
import parser.ASTName;
import parser.ASTOrderByClause;
import parser.ASTQuery;
import parser.ASTReName;
import parser.ASTSelectAttribute;
import parser.ASTSelectClause;
import parser.ASTSelectList;
import parser.ASTSource;
import parser.ASTStart;
import parser.ASTStreamDef;
import parser.ASTStreamFilter;
import parser.ASTStringLiteral;
import parser.ASTTimePeriod;
import parser.ASTUnits;
import parser.ASTWhereClause;
import parser.ASTWinLength;
import parser.ASTWinTimBatch;
import parser.ASTWinTime;
import parser.SimpleNode;
import parser.TritonParserVisitor;

public class LogicPlanVisitor implements TritonParserVisitor {

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
		SimpleNode firstChild = (SimpleNode) node.jjtGetChild(0);
				
		return firstChild.jjtAccept(this, data);
  }

	@Override
  public Object visit(ASTCreateClause node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTAttributeDefList node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTAttributeDefinition node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTAttributeType node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTInsertClause node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTQuery node, Object data) {
	  // TODO Auto-generated method stub
		int numOfNodes = node.jjtGetNumChildren();
		
		if (numOfNodes == 2) {
			Projection projectionOp = (Projection) node.jjtGetChild(0).jjtAccept(this, data);
			Product productOp = (Product) node.jjtGetChild(1).jjtAccept(this, data);
			IStream iStreamOp = new IStream(OperatorConstants.ISTREAM);
			iStreamOp.addChild(projectionOp, 0);
			projectionOp.setParent(iStreamOp);
			projectionOp.addChild(productOp, 0);
			productOp.setParent(projectionOp);
			return iStreamOp;
		} else if (numOfNodes == 3) {	
			Projection projectionOp = (Projection) node.jjtGetChild(0).jjtAccept(this, data);
			Product productOp = (Product) node.jjtGetChild(1).jjtAccept(this, data);
			Selection selectionOp = (Selection) node.jjtGetChild(2).jjtAccept(this, data);
			
			IStream iStreamOp = new IStream(OperatorConstants.ISTREAM);
			iStreamOp.addChild(projectionOp, 0);
			projectionOp.setParent(iStreamOp);
			projectionOp.addChild(selectionOp, 0);
			selectionOp.setParent(projectionOp);
			selectionOp.addChild(productOp, 0);
			productOp.setParent(selectionOp);
			
			return iStreamOp;
		}

		
	  return null;
  }

	@Override
  public Object visit(ASTSelectClause node, Object data) {
	  // TODO Auto-generated method stub
		Projection projectionOp = new Projection(OperatorConstants.PROJECTION);
	  return projectionOp;
  }

	@Override
  public Object visit(ASTSelectList node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTAttribute node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTFromClause node, Object data) {
	  // TODO Auto-generated method stub
		Product productOp = new Product(OperatorConstants.PRODUCT);
		node.jjtGetChild(0).jjtAccept(this, productOp);
	  return productOp;
  }

	@Override
  public Object visit(ASTFromList node, Object data) {
	  // TODO Auto-generated method stub
		int numOfNodes = node.jjtGetNumChildren();
		Product productOp = (Product) data;
		for (int i = 0; i < numOfNodes; i++) {
			Window w = (Window) node.jjtGetChild(i).jjtAccept(this, data);
			productOp.addChild(w, i);
			w.setParent(productOp);
		}
		return null;
  }

	@Override
  public Object visit(ASTUnits node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTWhereClause node, Object data) {
	  // TODO Auto-generated method stub
		Selection selectionOp = new Selection(OperatorConstants.SELECTION);
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
	  return null;
  }

	@Override
  public Object visit(ASTInteger node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }
	
	void checkNumOfChildren(SimpleNode node, int num, String errMsg) {
		int numOfChild = node.jjtGetNumChildren();
		if (numOfChild != num) {
			System.err.println("Error: " + errMsg + " should have " + num
					+ " children, but only have " + numOfChild);
		}
	}

	@Override
  public Object visit(ASTSource node, Object data) {
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
  public Object visit(ASTStreamDef node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTWinLength node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTWinTime node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTWinTimBatch node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTTimePeriod node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
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
	  return null;
  }

	@Override
  public Object visit(ASTStringLiteral node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTFloatingLiteral node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }
}
