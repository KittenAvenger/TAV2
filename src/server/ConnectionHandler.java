package server;

public class ConnectionHandler 
{
	String clientMsg, input, accountID;
	Parser parser = new Parser();
	Kernel kernel = new Kernel();
	int result = 0;
	
	/*	The clientMsg string has been previously parsed to a format that matches one of the case statements,
	 * 	the input is the unparsed message from a client, normally containing sender, receiver and message data
	 * 	the accountID string is for the addMessage function.
	 * 
	 */
	
	public ConnectionHandler(String clientMsg, String input, String accountID)
	{
		this.clientMsg = clientMsg;
		this.input = input;
		this.accountID = accountID;
	}
	
	/*	The cases can either be adding a message, replacing it, deleting it, fetching it and to do a fetch complete.
	 * 	The default case is if the xml format doesn't match any of the above and returns a string "Message didn't match"
	 * 	Every case except the default one either returns a successful result or an error.
	 */
	
	public String handleMessage()
	{
		switch(clientMsg)
		{
    		case "AddMessage":
    		
	    		String message  = "";
	    		String sender   = "";
	    		String recipent = "";
	    		recipent = parser.parseAddID(input);
	    		message = parser.parseAdd(input);
	    		sender = accountID;
	    		
	    		this.result = kernel.add(message, sender, recipent);
	    		
	    		if(result == -1)
	    		{
	    			return "<ErrorMsg> Reason </ErrorMsg>";
	    		   	
	    		}
	    		
	    		else
	    		{
	    			return "<Message added: '" + result + "' />";
	    			
	    		}
    		
    		
    		case "RplMessage":
    		
	    		String rplMsg = "";
	    		String msgID  = "";
	    		rplMsg = parser.parseReplace(input);
	    		msgID = parser.parseAddID(input);
	    		
	    		this.result =kernel.replace(Integer.parseInt(msgID), rplMsg);
	    		
	    		if(result == -1)
	    		{
	    			return "<ErrorMsg> Reason </ErrorMsg>";
	    			
	    		}
	    		
	    		else
	    		{
	    			return "<Message replaced: '" + result + "' />";
	    			
	    		}
    		
    		case "DelMessage":
	         
    			msgID = parser.parseAddID(input);
	    		
	    		this.result = kernel.delete(Integer.parseInt(msgID));
	
	    		if(result == -1)
	    		{
	    			return "<ErrorMsg> Reason </ErrorMsg>";
	    			
	    		}
	    		
	    		else
	    		{
	    			return "<Message deleted: '" + result + "' />";
	    			
	    		}

    		case "FetchMessages/":
    			
	    		String output = "";
	    		output = kernel.fetch(accountID);
	    			    		
	    		if(output.equals("Message doesn't exist"))
	    		{
	    			return "<ErrorMsg> " + output + " </ErrorMsg>";
	    			
	    		}
	    		
	    		else
	    		{
	    			return "<FetchedMessages>" + output + "\n</FetchedMessages>";
	    			
	    		}
	    		
    		
    		
    		case "FetchComplete/":
    			
	    		if(kernel.fetch_complete(accountID) == 1)
	    		{
	    			return "<FetchedCompleteAck/>";
	    			
	    		}
	    		
	    		else
	    		{
	    			return "<ErrorMsg> No message to delete </ErrorMsg>";
	    		}    		   	
	    }
			return "Message didn't match";
	}
}
