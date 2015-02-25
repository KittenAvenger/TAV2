import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class Connection implements Runnable
{

    Socket clientSocket = null;
    String sender, clientMsg, input, accountID;
    int result = 0;
    Parser parser= new Parser();    
    ConnectionHandler handler; 

    public Connection (Socket clientSocket) 
    {
        this.clientSocket = clientSocket;   
    }

    public synchronized void run() 
    {
    	outerloop:
    		
    	while (true)
    	{
    		
	        try 
	        {
		        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			 
				while ((input = in.readLine()) != null && !input.isEmpty())  
				{
					System.out.println("From client: " + input);	
					
					accountID = parser.parseRequest(input);					//Parse the account ID of the client, i.e. their telephone number
					
					
			        if (existsID(accountID) == true)
			        {
			        	out.println("Connection denied\n");
			     		break outerloop;		        			      
					}
			        
					else 
					{
						out.println("<Accepted connection from '" + accountID + "' +/>\n");
						addID(accountID);
						sender = accountID;
						
						while(true)
						{			        	
							while ((input = in.readLine()) != null && !input.isEmpty())  
							{
				 		        System.out.println("From client: " + input);
				 		        break;
				        	}
				        	 
				        	if(input.length() == 0)
				        	{
				        		 
				        	}
				        	 
				        	else if (input.equals("Disconnect"))
				        	{
				        		out.println("Client disconnected");
				        		removeID(accountID);
				        		break outerloop;
				        	}
				        	
				        	else
				        	{
				        		clientMsg = parser.parseAllMsg(input);
				     			handler = new ConnectionHandler(clientMsg, input, sender);
				     			out.println(handler.handleMessage());			     						        				        				     			      
				            } 
				        	 
				        }
					}
			    }    
	        }
	        
	        catch (IOException e) 
	        {
	        	e.printStackTrace();
	        }
    	}
    }
    
    
    private synchronized boolean existsID(String request)
    {
    	for(int i = 0; i < Server.ProcessIDList.size(); i++)
    	{	
    		if(Server.ProcessIDList.get(i).equals(request))
    		{
    			return true;
    		}	
    	}
    	
    	return false;        
    }
    
    
    
    private synchronized void addID (String ID)
    {
    	Server.ProcessIDList.add(ID);
    }
    
    private synchronized void removeID (String ID)
    {
    	for(int i = 0; i < Server.ProcessIDList.size(); i++)
    	{
    		if(Server.ProcessIDList.get(i).equals(ID))
    		{
    			Server.ProcessIDList.remove(i);
    		}	
    	}
    }
}
