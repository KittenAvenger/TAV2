import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



public class Client 
{
	
	int port = 4444;
	String str, ID, msgID, request, address = "localhost";		
	Socket conn = null;
	
	
	

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
	
	protected Socket createSocket(String address, int port) throws UnknownHostException, IOException 
	{
	    return new Socket(address, port);
	}
	public String returnRequest()
	{
		return request + "\r\n";
	}
	
	public String addMessage(String receiver, String message) throws IOException
	{		
		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
	    BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	    
	    out.println("<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>");
	    
	    while ((msgID = in.readLine()) != null && !msgID.isEmpty()) 
	    {
	    	System.out.println("From server: " + msgID);
		    break;
		}
	    
	    return msgID;
	}
	
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


	public String deleteMessage(String ID) throws IOException
	{
	
		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
	    BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	    
	    out.println("<DelMessage> <MsgId \"" + ID + "\" /> </DelMessage>");
	    
	    while ((str = in.readLine()) != null && !str.isEmpty()) 
	    {
	    	System.out.println("From server: " + str);
		    break;
		}
	
    	return str;
	}

	public String fetchMessage() throws IOException
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

	public String fetch_complete_Message() throws IOException
	{

		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));

		out.println("<FetchComplete>");

		while ((str = in.readLine()) != null && !str.isEmpty()) 
	    {
	    	System.out.println("From server: " + str);
		    break;
		}

		return str;
	}

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
