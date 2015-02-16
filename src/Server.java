import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server implements Runnable 
{
	
	int port = 4444;
	String str;
	String text = "<Accepted connection from  1234 +/>";
	ServerSocket serverSocket = null;

	public void run() 
	{
		while(true)
		{
			
		try 
		{
			    serverSocket = new ServerSocket(port);
			    Socket clientSocket = serverSocket.accept();
			    
			    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			    BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
			    
			    out.println(text);
			    
			    while ((str = in.readLine()) != null) {
			        System.out.println("From client: " + str);
			    }
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		}
		
	}
	
	public synchronized void stop(){
        
        try {
            this.serverSocket.close();
//            System.out.println("Server has been stopped");
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }
	

}
