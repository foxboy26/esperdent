package edu.ucsd.cs.triton.operator;

import parser.ASTAttribute;
import parser.ASTAttributeDefList;
import parser.ASTAttributeDefinition;
import parser.ASTAttributeType;
import parser.ASTCondAnd;
import parser.ASTCondEq;
import parser.ASTCondGt;
import parser.ASTCondOr;
import parser.ASTCreateClause;
import parser.ASTFloat;
import parser.ASTFromClause;
import parser.ASTFromList;
import parser.ASTInsertClause;
import parser.ASTInteger;
import parser.ASTName;
import parser.ASTQuery;
import parser.ASTSelectClause;
import parser.ASTSelectList;
import parser.ASTSlideClause;
import parser.ASTSource;
import parser.ASTStart;
import parser.ASTStream;
import parser.ASTStreamFilter;
import parser.ASTString;
import parser.ASTUnits;
import parser.ASTValueSpec;
import parser.ASTWhereClause;
import parser.ASTWindowFrame;
import parser.ASTWindowFrameStart;
import parser.ASTWindowFrameUnits;
import parser.ASTWindowSpec;
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
  public Object visit(ASTStream node, Object data) {
	  // TODO Auto-generated method stub
		int numOfNodes = node.jjtGetNumChildren();
		
		Window windowOp = new Window(OperatorConstants.WINDOW);
		
		if (numOfNodes == 1) {
			
			return windowOp;
		} else if (numOfNodes == 2) {

			return windowOp;
		} else if (numOfNodes == 3) {

			Selection selectionOp = new Selection(OperatorConstants.SELECTION);
			selectionOp.setParent(windowOp);
			windowOp.addChild(selectionOp, 0);

			return windowOp;
		}
		
	  return null;
  }

	@Override
  public Object visit(ASTWindowSpec node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTWindowFrame node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTWindowFrameUnits node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTWindowFrameStart node, Object data) {
	  // TODO Auto-generated method stub
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
  public Object visit(ASTCondEq node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTCondGt node, Object data) {
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
  public Object visit(ASTValueSpec node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTSlideClause node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
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
  public Object visit(ASTString node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTFloat node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }
}
