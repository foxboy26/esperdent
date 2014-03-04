package edu.ucsd.cs.triton.operator;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseLogicPlan {
	protected final String _planName;
	protected List<BaseLogicPlan> _dependenceList;
	
	BaseLogicPlan(final String planName) {
		_planName = planName;
		_dependenceList = new ArrayList<BaseLogicPlan> ();
	}
	
	public List<BaseLogicPlan> getDependenceList() {
		return _dependenceList;
	}
	
	public void addDependency(BaseLogicPlan plan) {
		_dependenceList.add(plan);
	}
	
	public String getPlanName() {
		return _planName;
	}
	
	
	@Override
	public String toString() {
		return _planName;
	}
	
	public abstract Start generatePlan();

	public void dump() {
	  // TODO Auto-generated method stub
	  
  }
}
