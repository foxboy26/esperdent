package edu.ucsd.cs.triton.codegen;

import storm.trident.TridentTopology;
import storm.trident.spout.IBatchSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;

public class SimpleQuery implements IQuery {
	
	protected IBatchSpout _spout;
	
	protected TridentTopology _topology;
	
  protected Config _conf;

  public void init() {
  
  }

	@Override
  public StormTopology buildTopology() {
	  // TODO Auto-generated method stub
	  return _topology.build();
  }

	@Override
  public void execute(String[] args) {
	  // TODO Auto-generated method stub
	  // local mode
		if (args.length == 0) {
	    LocalCluster cluster = new LocalCluster();
	    cluster.submitTopology("twitter_demo", _conf, buildTopology());
	  } else {
	  	// cluster mode
	    try {
	      StormSubmitter.submitTopology(args[0], _conf, buildTopology());
      } catch (AlreadyAliveException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      } catch (InvalidTopologyException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
	  }
  }
}

