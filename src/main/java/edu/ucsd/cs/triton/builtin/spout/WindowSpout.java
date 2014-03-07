package edu.ucsd.cs.triton.builtin.spout;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ucsd.cs.triton.window.RingBuffer;

import storm.trident.operation.TridentCollector;
import storm.trident.spout.IBatchSpout;
import storm.trident.tuple.TridentTuple;
import backtype.storm.Config;
import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Fields;

/**
 * Read line-based data from file
 * format: 1st line: data type, seperated by comma
 * second - n: data
 * 
 * @author zhihengli
 *
 */
public class WindowSpout implements IBatchSpout {

	
  Fields _fields;
  List<Object>[] outputs;
  private String[] _typeList;
	private BufferedReader _br;
	private String _filename;
	protected RingBuffer<TridentTuple> _slidingWindow;

  public WindowSpout(String fileName, Fields fields) {
      _fields = fields;
      _filename = fileName;
  }
  
  boolean cycle = false;
  
  public void setCycle(boolean cycle) {
      this.cycle = cycle;
  }
  
  @Override
  public void open(Map conf, TopologyContext context) {
  	try {
	    _br = new BufferedReader(new FileReader(_filename));
      String line;
      try {
	      line = _br.readLine();
		    _typeList = line.split(",");
      } catch (IOException e) {
  	    // TODO Auto-generated catch block
  	    e.printStackTrace();
      }    
    } catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
  }

  @Override
  public void emitBatch(long batchId, TridentCollector collector) {
      //Utils.sleep(2000);
		for (TridentTuple tuple : _slidingWindow) {
    	collector.emit(values);
		}
  }

  @Override
  public void ack(long batchId) {
      
  }

  @Override
  public void close() {
      try {
	      _br.close();
      } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
  }

  @Override
  public Map getComponentConfiguration() {
      Config conf = new Config();
      conf.setMaxTaskParallelism(1);
      return conf;
  }

  @Override
  public Fields getOutputFields() {
      return _fields;
  }
  
}
