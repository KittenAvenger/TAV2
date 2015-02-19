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
		
		while(! isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            new Thread(
                new Connection(
                    clientSocket)
            ).start();
        }
        System.out.println("Server Stopped.") ;
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
