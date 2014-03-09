package compiler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import com.google.gson.Gson;


public class CompilerWebSocketServlet extends WebSocketServlet {
	
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
		//	String query = request.getParameter("query");
		//StringReader sr = new java.io.StringReader(query);
		//Reader r = new BufferedReader(sr);
		//TritonParser tritonParser;
		//tritonParser = new TritonParser(r);
		//ASTStart root = tritonParser.Start();

		//ResourceManager resourceManager = ResourceManager.getInstance();

		//LogicPlanVisitor logicPlanVisitor = new LogicPlanVisitor(resourceManager);

		//root.jjtAccept(logicPlanVisitor, resourceManager);
		//System.out.println(resourceManager.getStreamByName("s1"));

			sendMessage("Generating logic plan...");
		//List<BaseLogicPlan> logicPlanList = logicPlanVisitor.getLogicPlanList();

			sendMessage("Generating trident code...");
		//String className = "Sample";
		//CodeGenerator codeGen = new CodeGenerator(logicPlanList, className);

		//JavaProgram program = codeGen.generate();

			sendMessage("Translating trident code into java code...");
		//String res = program.translate();

			sendMessage("Generating packge...");
			sendMessage("success!!!!");
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

    private void sendErrorMessage(String message) {
      Message errorMsg = new Message("server", "error", message);
      sendMessage(gson.toJson(errorMsg));
    }
    
    private void broadcast(String message) {

    }
	}
}
