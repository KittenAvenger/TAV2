import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server implements Runnable 
{
	
	int port = 4444;
	String str, ID;
	String text = "<Accepted connection from  1234 +/>";
	ServerSocket serverSocket = null;
	boolean isStopped = false, idExists = false;
	static ArrayList <String> ProcessIDList = new ArrayList<String>();
	Parser parse = new Parser();
	Socket clientSocket = null;

	public void start (){
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() 
	{
		start();
		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(true){	
		try 
		{    
			
			    
//			    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//			    BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
//			    
//			    out.println(text + "\n");
			    new Thread(
			            new Connection(
			            clientSocket)
			            ).start();
//			    while ((str = in.readLine()) != null && !str.isEmpty())  {
//			        System.out.println("From client: " + str);		
//			        checkID(str);			     
//			    }   
		}
		
		catch (Exception e){
			e.printStackTrace();
		}
	}
	}
	
	public void checkID(String request){
		ID = parse.parseRequest(request);
		ProcessIDList.add("test");
	    
	    for(int i = 0; i < ProcessIDList.size(); i++)
	    {
			if(ProcessIDList.get(i).equals(ID)){
				System.out.println("it works");
				//out.println("Request denied");
				idExists = true;
				break;
			}
			
		}
	   
	    if(!idExists){
	    	ProcessIDList.add(ID);
	    }
	    else if(idExists == true){
	    	idExists = false;
	    }
	}
	
	public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
//            System.out.println("Server has been stopped");
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }
	
	public synchronized boolean isStopped(){
		
		return isStopped;
	}
}
