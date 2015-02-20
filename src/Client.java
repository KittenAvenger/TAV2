import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	
	int port = 4444;
	String str, test;
	int i = 0;
	Scanner scan = new Scanner(System.in);
	Socket conn = null;
	

	public String connect() throws IOException{
		
		try {
			conn = new Socket (InetAddress.getLocalHost(), 4444);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
			 
			 
		    PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
		    BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));

		    
		    out.println("<Request connection  1234567890 +/>" + "\n");
		

		    while ((str = in.readLine()) != null && !str.isEmpty()) {
		    	 
			      System.out.println("From server: " + str);
			      break;
			    }
		    
		    
			
		return str;
		
		}	
		
	public String addMessage() throws IOException{
		
		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
	    BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	    
	    out.println("<AddMessage> <Receiver \"1234567890\" /> <Content \"This is my message's.\" /> </AddMessage>");
	    
	    while ((str = in.readLine()) != null && !str.isEmpty()) {
	    	 
		      System.out.println("From server: " + str);
		      break;
		    }
		
	    return str;
	}
	
	public String DeleteMessage(String ID) throws IOException{
		
		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
	    BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	    
	    out.println("<DelMessage> <MsgId \"" + ID + "\" /> </DelMessage>");
	    
	    while ((str = in.readLine()) != null && !str.isEmpty()) {
	    	 
		      System.out.println("From server: " + str);
		      break;
		    }
		
	    return str;
	}
	
	public String fetchMessage(String ID) throws IOException{
		
		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	    
	    	out.println("<FetchMessages/>");
	    
	    	while ((str = in.readLine()) != null && !str.isEmpty()) {
	    	 
		      System.out.println("From server: " + str);
		      break;
		    }
		
	    	return str;
	}

	public String fetch_complete_Message(String ID) throws IOException{
	
		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
    		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
    
    		out.println("<FetchComplete/>");
    
    		while ((str = in.readLine()) != null && !str.isEmpty()) {
    	 
	      		System.out.println("From server: " + str);
	      		break;
		 }
	
    		return str;
	}
}
