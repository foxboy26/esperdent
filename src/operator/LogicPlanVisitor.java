package operator;

import java.util.ArrayList;

import org.w3c.dom.Node;

import parser.ASTAttribute;
import parser.ASTAttributeDefList;
import parser.ASTAttributeDefinition;
import parser.ASTAttributeType;
import parser.ASTCondAnd;
import parser.ASTCondEq;
import parser.ASTCondGt;
import parser.ASTCondOr;
import parser.ASTCreateClause;
import parser.ASTFromClause;
import parser.ASTFromList;
import parser.ASTInsertClause;
import parser.ASTInteger;
import parser.ASTName;
import parser.ASTQuery;
import parser.ASTSelectClause;
import parser.ASTSelectList;
import parser.ASTStart;
import parser.ASTStream;
import parser.ASTUnits;
import parser.ASTWhereClause;
import parser.ASTWindowFrame;
import parser.ASTWindowFrameStart;
import parser.ASTWindowFrameUnits;
import parser.ASTWindowSpec;
import parser.EsperdentParserVisitor;
import parser.SimpleNode;

public class LogicPlanVisitor implements EsperdentParserVisitor {

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
		
		System.out.println(firstChild.jjtAccept(this, data));
		
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
			projectionOp.addChild(productOp, 0);
			productOp.setParent(projectionOp);
			return projectionOp;
		} else if (numOfNodes == 3) {
			Projection projectionOp = (Projection) node.jjtGetChild(0).jjtAccept(this, data);
			System.out.println(projectionOp);
			Product productOp = (Product) node.jjtGetChild(1).jjtAccept(this, data);
			System.out.println(productOp);
			Selection selectionOp = (Selection) node.jjtGetChild(2).jjtAccept(this, data);
			System.out.println(selectionOp);
			projectionOp.addChild(selectionOp, 0);
			selectionOp.setParent(projectionOp);
			selectionOp.addChild(productOp, 0);
			productOp.setParent(selectionOp);
			return projectionOp;
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
	  return productOp;
  }

	@Override
  public Object visit(ASTFromList node, Object data) {
	  // TODO Auto-generated method stub
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
}
