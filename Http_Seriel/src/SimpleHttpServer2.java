import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleHttpServer2 {

//  public static void main(String[] args) throws Exception {
//    HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
//    server.createContext("/info", new InfoHandler());
//    server.createContext("/get", new GetHandler());
//    server.setExecutor(null); // creates a default executor
//    server.start();
//    System.out.println("The server is running");
//  }
//  
  public SimpleHttpServer2(){
	  
	   try{ 
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
	    server.createContext("/info", new InfoHandler());
	    server.createContext("/get", new GetHandler());
	    server.setExecutor(null); // creates a default executor
	    server.start();
	    System.out.println("The server is running");
	    SerialConnection connection = new SerialConnection(null, null, null, null);
	   }
	   catch(Exception e)
	   {
		   		   
	   }
  }

  // http://localhost:8000/info
  static class InfoHandler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
      String response = "Use /get?hello=word&foo=bar to see how to handle url parameters";
      SimpleHttpServer2.writeResponse(httpExchange, response.toString());
    }
  }

  static class GetHandler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
      StringBuilder response = new StringBuilder();
      Map <String,String>parms = SimpleHttpServer2.queryToMap(httpExchange.getRequestURI().getQuery());
      //response.append("<html><body>");
      //response.append("PW : " + parms.get("PW") + "<br/>");
      //response.append("foo : " + parms.get("foo") + "<br/>");
      //response.append("</body></html>");
	if(parms.get("PW").equals("1*"))
	response.append("Account Correct!");
	else
	response.append("Account Wrong!!");
	
	if(parms.get("PW").equals("s")){
	response.append("start");
	}
    SimpleHttpServer2.writeResponse(httpExchange, response.toString());
    }
  }

  public static void writeResponse(HttpExchange httpExchange, String response) throws IOException {
    httpExchange.sendResponseHeaders(200, response.length());
    OutputStream os = httpExchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }


  /**
   * returns the url parameters in a map
   * @param query
   * @return map
   */
  public static Map<String, String> queryToMap(String query){
    Map<String, String> result = new HashMap<String, String>();
    for (String param : query.split("&")) {
        String pair[] = param.split("=");
        if (pair.length>1) {
            result.put(pair[0], pair[1]);
        }else{
            result.put(pair[0], "");
        }
    }
    return result;
  }

}