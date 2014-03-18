package compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import parser.ASTStart;
import parser.ParseException;
import parser.TritonParser;

import com.google.gson.Gson;

import edu.ucsd.cs.triton.codegen.CodeGenerator;
import edu.ucsd.cs.triton.codegen.language.JavaProgram;
import edu.ucsd.cs.triton.operator.BaseLogicPlan;
import edu.ucsd.cs.triton.operator.LogicPlanVisitor;
import edu.ucsd.cs.triton.resources.ResourceManager;


public class CompilerWebSocketServlet extends WebSocketServlet {
	
	private final String TRITON_DIR = "/Users/zhihengli/UCSD/project/triton/";
	
	private final Gson gson = new Gson(); 
	
	private final AtomicInteger connectionIds = new AtomicInteger(0);
	
  private final Map<Integer, CompilerWebSocketServlet> connections = new Hashtable<Integer, CompilerWebSocketServlet>();

  @Override
  protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
      return new CompilerMessageInbound(connectionIds.incrementAndGet());
  }
	
	private final class CompilerMessageInbound extends MessageInbound {

    private final int connectionId;
    private Compiler compiler;
  
    private CompilerMessageInbound(int id) {
      this.connectionId = id;
    }

		
		@Override
    protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
	    // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Binary message not supported.");
    }

		@Override
    protected void onTextMessage(CharBuffer arg0) throws IOException {
	    // TODO Auto-generated method stub
			System.out.println("message received: " + arg0.toString());
			
		  Message message = gson.fromJson(arg0.toString(), Message.class);

		  if (message.action != null && message.action.equals("compile")){
		  	compile("demo", message.content);
		  } else {
		  	System.err.println("unknown action: [" + message.action + "]");
		  	sendErrorMessage("unknown action: [" + message.action + "]");
		  }
//		  String query = arg0.toString();
//		  StringReader sr = new java.io.StringReader(query);
//		  Reader r = new BufferedReader(sr);
//		  TritonParser tritonParser;
//		  tritonParser = new TritonParser(r);
//		  ASTStart root;
//      try {
//	      root = tritonParser.Start();
//
//			  ResourceManager resourceManager = ResourceManager.getInstance();
//	
//			  LogicPlanVisitor logicPlanVisitor = new LogicPlanVisitor(resourceManager);
//	
//			  root.jjtAccept(logicPlanVisitor, resourceManager);
//			//System.out.println(resourceManager.getStreamByName("s1"));
//	
//			  sendInforMessage("Generating logic plan...");
//			  List<BaseLogicPlan> logicPlanList = logicPlanVisitor.getLogicPlanList();
//	
//			  sendInforMessage("Generating trident code...");
//			  String className = "Sample";
//			  CodeGenerator codeGen = new CodeGenerator(logicPlanList, className);
//	
//			  JavaProgram program = codeGen.generate();
//	
//			  sendInforMessage("Translating trident code into java code...");
//				String res = program.translate();
//	
//				sendInforMessage("Generating packge...");
//				sendInforMessage("Compile success!");
//				
//				Message message = new Message("server", "result", res);
//				sendMessage(gson.toJson(message));
//				
//      } catch (ParseException e) {
//	      // TODO Auto-generated catch block
//	      e.printStackTrace();
//      }
    }
		private void compile(final String scriptId, final String script) {
			
			// generate script file
			PrintWriter out;
      try {
	      out = new PrintWriter(TRITON_DIR + "/" + scriptId + ".tql");
				out.println(script);
				out.close();
      } catch (FileNotFoundException e1) {
	      // TODO Auto-generated catch block
	      e1.printStackTrace();
      }
			
			ProcessBuilder pb = new ProcessBuilder(
					"./compile.sh",
					scriptId + ".tql");

			System.out.println(script);

			pb.directory(new File(TRITON_DIR));

			pb.redirectErrorStream(true);

			try {
				final Process p;

				p = pb.start();
				String processId = "compile" + scriptId;
				new Thread(processId) {
					public void run() {
						InputStream stdout = p.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(
						    stdout));
						String line;
						try {
							while ((line = reader.readLine()) != null) {
								sendInfoMessage(line);
								if (line.startsWith("result: ")) {
									sendMessage("result", line.substring("result: ".length()));
								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							System.out.println("stream closed!");
						}
					}
				}.start();
				System.out.println("started");
				p.waitFor();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

    private void sendMessage(final String action, final String message) {
      try {
      	Message msgObject = new Message("server", action, message);
        CharBuffer buffer = CharBuffer.wrap(gson.toJson(msgObject));
        this.getWsOutbound().writeTextMessage(buffer);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    private void sendInfoMessage(String message) {
      sendMessage("info", message);
    }
    
    private void sendErrorMessage(String message) {
      sendMessage("error", message);
    }
    
    private void broadcast(String message) {

    }
	}
}
