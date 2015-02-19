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
	

	public String connect(){
		try {
			 conn = new Socket (InetAddress.getLocalHost(), 4444);
			 
		    PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
		    BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
//		    while(i < 10){
//		    text = scan.next();	
//		    out.println(text);
//		    i++;
//		    }
		    
		    out.println("<Request connection  1234 +/>" + "\n");
		
//		    if(in.ready()){
//		    	 
//		    }
		   
		    while ((str = in.readLine()) != null && !str.isEmpty()) {
		    	 
			      System.out.println("From server: " + str);
			      break;
			    }
		    //System.out.println("Shit didn't work");
		    
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return str;
		
	}
}
