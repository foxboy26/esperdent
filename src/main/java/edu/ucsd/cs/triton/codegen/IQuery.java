package edu.ucsd.cs.triton.codegen;

import backtype.storm.generated.StormTopology;

public interface IQuery {
	public void init();
	public StormTopology buildTopology();
	public void execute(String[] args);
}
