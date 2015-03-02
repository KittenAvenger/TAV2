package server;

public class ConnectionHandler 
{
	String clientMsg, input, accountID;
	Parser parser = new Parser();
	Kernel kernel = new Kernel();
	int result = 0;
	
	public ConnectionHandler(String clientMsg, String input, String accountID)
	{
		this.clientMsg = clientMsg;
		this.input = input;
		this.accountID = accountID;
	}
	
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
	    			return "<ErrorMsg> " + output + " </ErrorMsg>\n";
	    			
	    		}
	    		
	    		else
	    		{
	    			return "<FetchedMessages>\n" + output + "\n</FetchedMessages>\n";
	    			
	    		}
	    		
    		
    		
    		case "FetchComplete":
    			
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
