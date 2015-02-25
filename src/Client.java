import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;



public class Client 
{
	
	int port = 4444;
	String str;		
	Socket conn = null;
	

	public String connect(String ID) throws IOException
	{
		try 
		{
			conn = new Socket (InetAddress.getLocalHost(), 4444);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		
		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
	    BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));	 
			 
	    out.println("<Request connection  " + ID + " +/>" + "\n");
		
	    while ((str = in.readLine()) != null && !str.isEmpty()) 
	    {
	    	System.out.println("From server: " + str);
		    break; 
	    }    

	    return str;   
	}	
		
	public String addMessage(String receiver, String message) throws IOException
	{		
		PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
	    BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	    
	    out.println("<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>");
	    
	    while ((str = in.readLine()) != null && !str.isEmpty()) 
	    {
	    	System.out.println("From server: " + str);
		    break;
		}
	    
	    return str;
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
	    	
	    	if(str.equals("</FetchedMessages>") || str.equals("<ErrorMsg> all messages fetched </ErrorMsg>"))
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
}
