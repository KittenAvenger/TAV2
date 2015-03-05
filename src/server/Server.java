package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server implements Runnable 
{
	
	/*	Fields for initializing the server, ProcessIDList saves the
	 * 	accountID for every client to keep track of who is already connected
	 */
	
	private int port = 4444;
	private ServerSocket serverSocket = null;
	private boolean isStopped = false;
	private static ArrayList <String> ProcessIDList = new ArrayList<String>();
	Socket clientSocket = null;
	
	//	Creates a server socket to listen on port 4444

	private synchronized void start ()
	{
		System.out.println("Server started");
		
		try 
		{
			serverSocket = new ServerSocket(port);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/*	Initiates the server socket and goes into a loop to listen to incoming client requests. 
	 * 	Creates a new thread for every client and passes the clients socket into the Connection object.
	 */
	
	public synchronized void run() 
	{
		start();
		
		while(!isStopped())
		{
            Socket clientSocket = null;
            
            try 
            {
                clientSocket = this.serverSocket.accept();
            } 
            
            catch (IOException e) 
            {
                if(isStopped()) 
                {
                    return;
                }
                
                throw new RuntimeException("Error accepting client connection", e);
            }
            
            new Thread(new Connection(clientSocket)).start();
        }
        
    }
		
	//	Stops server and sets flag to true
	
	public void stop()
	{
        this.isStopped = true;
        
        try 
        {
            this.serverSocket.close();
            System.out.println("Server has been stopped");
        } 
        
        catch (IOException e) 
        {
        	e.printStackTrace();
            throw new RuntimeException("Error closing server", e);
        }
    }
	
	public synchronized  boolean isStopped()
	{
		return isStopped;
	}

	//	Getter for ProcessIDList
	
	public static ArrayList <String> getProcessIDList() {
		return ProcessIDList;
	}
	
}
