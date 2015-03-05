package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*	Client class used to request connections and communicate with the server
 * 	All return messages were used in initial mocking tests to assert outputs.
 * 
 */

public class Client 
{
	
	int port = 4444;
	String str, ID, msgID, request, address = "localhost", add, delete;		
	Socket conn = null;
	
	
	//	Sends a request to server to connect

	public String connect(String ID) throws IOException
	{
		
		conn = createSocket(address, port);
		
		this.ID = ID;
		
		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
	    BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));	 
	    request = "<Request connection  " + ID + " +/>";
	    out.println(request);
	   
		
	    while ((str = in.readLine()) != null && !str.isEmpty()) 
	    {
	    	System.out.println("From server: " + str);
		    break; 
	    }    

	    return str;   
	}	
	
	//	Creates a new socket with the specified inet address and port
	
	protected Socket createSocket(String address, int port) throws UnknownHostException, IOException 
	{
	    return new Socket(address, port);
	}
	
	public String returnRequest()
	{
		return request + "\r\n";
	}
	
	//	Sends a request to add a message
	
	public String addMessage(String receiver, String message) throws IOException
	{		
		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
	    BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	    add ="<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>";
	    out.println(add);
	    
	    while ((msgID = in.readLine()) != null && !msgID.isEmpty()) 
	    {
	    	System.out.println("From server: " + msgID);
		    break;
		}
	    
	    return msgID;
	}
	
	public String returnAddMessage(){
		return add;
	}
	
	//	Sends a request to replace an existing message
	
	public String replaceMessage(String msgID, String message) throws IOException
	{
		
		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
	    BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	    
	    out.println("<RplMessage> <MsgID \"" + msgID + "\" /> <Content \"" + message + "\" /> </RplMessage>");
	    
	    while ((str = in.readLine()) != null && !str.isEmpty()) 
	    {
	    	System.out.println("From server: " + str);
		    break;
		}
		
	    return str;
	}


	//	Sends a request to delete a message
	
	public String deleteMessage(String ID) throws IOException
	{
	
		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
	    BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	    
	    delete ="<DelMessage> <MsgId \"" + ID + "\" /> </DelMessage>";
	    out.println(delete);
	    
	    while ((str = in.readLine()) != null && !str.isEmpty()) 
	    {
	    	System.out.println("From server: " + str);
		    break;
		}
	
    	return str;
	}
	
	public String retrunDeleteMessage(){
		return delete;
	}

	//	Sends a request to fetch a message
	
	public synchronized String fetchMessage() throws IOException
	{
		String msg = "";
		
		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	    
	    out.println("<FetchMessages/>");
	    
	    while ((str = in.readLine()) != null) 
	    {
	    	System.out.println("From server: " + str);
	    	msg += str;
	    	
	    	if(str.equals("</FetchedMessages>") || str.equals("<ErrorMsg> Message doesn't exist </ErrorMsg>"))
	    	{
	    		break;
	    	}    
		}
	
    	return msg;
	}

	//	Sends a request to perform a fetch complete
	
	public String fetch_complete_Message() throws IOException
	{

		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));

		out.println("<FetchComplete/>");

		while ((str = in.readLine()) != null && !str.isEmpty()) 
	    {
	    	System.out.println("From server: " + str);
		    break;
		}

		return str;
	}

	//	Sends a request to disconnect from the server
	
	public String disconnect() throws IOException
	{
	
		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
	    BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	    
	    out.println("Disconnect");
	    
	    while ((str = in.readLine()) != null && !str.isEmpty()) 
	    {
	    	System.out.println("From server: " + str);
		    break;
		}
		
	    return str;
	}
	
	public String returnID()
	{
		return ID;
	}
	
	public String returnConn()
	{
		return str;
	}
	
	public String returnMsgID()
	{
		return msgID;
	}
}
