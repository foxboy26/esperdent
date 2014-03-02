package edu.ucsd.cs.trition.twitter;

import storm.trident.operation.builtin.Count;
import backtype.storm.tuple.Fields;
import edu.ucsd.cs.triton.builtin.Max;
import edu.ucsd.cs.triton.codegen.SimpleQuery;
import edu.ucsd.cs.triton.spout.TwitterSpout;
import edu.ucsd.cs.triton.util.PrintFilter;
import edu.ucsd.cs.triton.window.FixedLengthSlidingWindow;
import edu.ucsd.cs.triton.window.SlidingWindowUpdater;

/*
 * select count(*) as tps, max(retweetCount) as maxRetweets from Tweets.win:time_batch(1 sec)
 */
public class TwitterDemo extends SimpleQuery {

	private TwitterSpout _spout;
  
  public TwitterDemo(TwitterSpout spout) {
  	init();
  	_spout = spout;
  }
	
	public void buildQuery() {
    _topology.newStream("s1", _spout) 
		.partitionPersist(new FixedLengthSlidingWindow.Factory(3), _spout.getOutputFields(), new SlidingWindowUpdater(), new Fields("windowId", "createdAt", "retweetCount"))
    .newValuesStream() // win:time_batch(1 sec)
  	.each(new Fields("windowId", "createdAt", "retweetCount"), new PrintFilter())
  	.groupBy(new Fields("windowId")) // no group by, so only groupby windowId
  	.chainedAgg()
  	.aggregate(new Count(), new Fields("tps")) // count(*) as tps
  	.aggregate(new Fields("retweetCount"), new Max(), new Fields("maxRetweets")) // max(retweetCount) as maxRetweets
  	.chainEnd()
  	.each(new Fields("tps", "maxRetweets"), new PrintFilter()); // output
    //.partitionPersist(stateFactory, updater)
	}
  
	public static void main(String[] args) {
		
		TwitterSpout spout = new TwitterSpout();
		TwitterDemo demo = new TwitterDemo(spout);
		demo.buildQuery();
    demo.execute(args);
	}
}
