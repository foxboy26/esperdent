package edu.ucsd.cs.triton.codegen;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;

public class MainGenerator {

}

"public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException {
	
  Config conf = new Config();
  conf.setMaxSpoutPending(20);
  if (args.length == 0) {
    LocalDRPC drpc = new LocalDRPC();
    LocalCluster cluster = new LocalCluster();
    cluster.submitTopology("twitter_demo", conf, buildTopology(drpc));
  }
  else {
    conf.setNumWorkers(3);
    StormSubmitter.submitTopology(args[0], conf, buildTopology(null));
  }
}"