package server;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/*	Main class for handling client requests and messages to and from the server
 * 
 */

public class Connection implements Runnable
{

    Socket clientSocket = null;
    String sender, clientMsg, input, accountID;
    int result = 0;
    Parser parser= new Parser();    
    ConnectionHandler handler; 
    
    //	Sets the socket

    public Connection (Socket clientSocket) 
    {
        this.clientSocket = clientSocket;   
    }
    
    /*	Reads the input stream from the client, if it equals disconnect, the client is disconnected from the server,
     * 	else parses the input message from the client and passes it to the ConnectionHandler object.
     * 	The ConnectionHandler returns a string that is sent to the client.
     */
    
    public synchronized void handleRequest()
    {   	
		try 
		{
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			while(true)
			{			        	
				while ((input = in.readLine()) != null && !input.isEmpty())  
				{
	 		        System.out.println("From client: " + input);
	 		        break;
	        	}
	        	
				if(input != null)
				{
					if (input.equals("Disconnect"))
		        	{
		        		out.println("Client disconnected");
		        		removeID(accountID);		        		
		        		break;
		        	}
		        	
		        	else
		        	{		        		
		        		clientMsg = parser.parseAllMsg(input);
		     			handler = new ConnectionHandler(clientMsg, input, sender);
		     			out.println(handler.handleMessage());			     						        				        				     			      
		            } 
				}
				
				else break;
	    	 }
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}		  	
    }
    
    /*	Reads the first incoming request from the client which is for requesting a connection to the server,
     * 	parses the accountID from the request string and checks if the client is already connected with his current accountID
     * 	if he is, the connection is denied and the method terminates, otherwise adds his accountID to the ProcessIDList and goes
     * 	to the method handleRequest
     */

    public synchronized void run() 
    {    		
	        try 
	        {
		        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			 
				while ((input = in.readLine()) != null && !input.isEmpty())  
				{
					System.out.println("From client: " + input);	
					
					if(input != null)
					{
						accountID = parser.parseRequest(input);					//Parse the account ID of the client, i.e. their telephone number
					}					
										
			        if (existsID(accountID) == true)
			        {
			        	out.println("Connection denied\n");	
//			        	clientSocket.close();
					}
			        
					else 
					{
						out.println("<Accepted connection from '" + accountID + "' +/>");
						addID(accountID);
						sender = accountID;
						handleRequest();						
					}
			    }    
	        }
	        
	        catch (IOException e) 
	        {
	        	e.printStackTrace();
	        }
    	
    }
    
    //	Checks if current client is already connected or not
    
    private synchronized boolean existsID(String request)
    {
    	for(int i = 0; i < Server.getProcessIDList().size(); i++)
    	{	
    		if(Server.getProcessIDList().get(i).equals(request))
    		{
    			return true;
    		}	
    	}
    	
    	return false;        
    }
    
    //	Adds newly connected client's accountID to the ProcessIDList
    
    private synchronized void addID (String ID)
    {
    	Server.getProcessIDList().add(ID);
    }
    
    //	When client disconnects, removes his accountID from the ProcessIDList
    
    private synchronized void removeID (String ID)
    {
    	for(int i = 0; i < Server.getProcessIDList().size(); i++)
    	{
    		if(Server.getProcessIDList().get(i).equals(ID))
    		{
    			Server.getProcessIDList().remove(i);
    		}	
    	}
    }
}
