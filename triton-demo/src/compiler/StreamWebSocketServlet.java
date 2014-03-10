package compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import parser.ASTStart;
import parser.TritonParser;

import com.google.gson.Gson;


public class StreamWebSocketServlet extends WebSocketServlet {
	
	private final Gson gson = new Gson(); 
	
	private final AtomicInteger connectionIds = new AtomicInteger(0);
	
  private final Map<Integer, StreamWebSocketServlet> connections = new Hashtable<Integer, StreamWebSocketServlet>();
  
  private Map<String, Process> processMap = new Hashtable<String, Process>(); 
  
  @Override
  protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
      return new CompilerMessageInbound(connectionIds.incrementAndGet());
  }
	
	private final class CompilerMessageInbound extends MessageInbound {

    private final int connectionId;
  
    private CompilerMessageInbound(int id) {
      this.connectionId = id;
    }

		
		@Override
    protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
	    // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Binary message not supported.");
    }

		@Override
    protected void onTextMessage(CharBuffer arg0) {
			System.out.println("message received: " + arg0.toString());
			
		  Message message = gson.fromJson(arg0.toString(), Message.class);
		  if (message.action != null && message.action.equals("start")) {
		  	ProcessBuilder pb = new ProcessBuilder("/Users/zhihengli/libs/apache-maven-3.1.1/bin/mvn", "-f", "pom.xml", "compile", "exec:java", "-Dstorm.topology=simple.Simple");
				pb.directory(new File("/Users/zhihengli/UCSD/project/triton/codegen/"));
				pb.redirectErrorStream(true);
				final Process p;
				try {
					p = pb.start();
					String processId = message.content;
					new Thread(processId) {
						public void run() {
							InputStream stdout = p.getInputStream();
							BufferedReader reader = new BufferedReader(new InputStreamReader(
							    stdout));
							String line;
							try {
								while ((line = reader.readLine()) != null) {
									sendMessage(line);
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
								System.out.println("stream closed!");
							}
						}
					}.start();
					System.out.println("started");
					processMap.put(processId, p);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		  } else if (message.action != null && message.action.equals("stop")) {
		  	stopProcess(message.content);
		  } else {
		  	System.err.println("unknown action: [" + message.action + "]");
		  	sendErrorMessage("unknown action: [" + message.action + "]");
		  }
    }

		private void stopProcess(final String processId) {
	  	Process p = processMap.get(processId);
	  	if (p != null) {
	  		p.destroy();
		  	processMap.remove(processId);
	  		sendInfoMessage("process [" + processId + "] has been stopped");
	  	} else {
		  	sendErrorMessage("unknown process: [" + processId + "]");
	  	}
		}
		
    private void sendMessage(String message) {
      CharBuffer buffer = CharBuffer.wrap(message);
      try {
        this.getWsOutbound().writeTextMessage(buffer);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    private void sendInfoMessage(String message) {
      Message errorMsg = new Message("server", "info", message);
      sendMessage(gson.toJson(errorMsg));
    }
    
    private void sendErrorMessage(String message) {
      Message errorMsg = new Message("server", "error", message);
      sendMessage(gson.toJson(errorMsg));
    }
    
    private void broadcast(String message) {

    }
	}
}
