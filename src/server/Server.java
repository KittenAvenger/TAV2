package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server implements Runnable 
{
	
	int port = 4444;
	ServerSocket serverSocket = null;
	boolean isStopped = false;
	public static ArrayList <String> ProcessIDList = new ArrayList<String>();
	Socket clientSocket = null;

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
}
