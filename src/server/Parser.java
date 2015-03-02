package server;

public class Parser 
{
	public String parseRequest(String message) 
	{
		String delims = "[ ]+";
		String[] tokens = message.split(delims);
		
		return tokens[2];
	}
	
	public String parseAdd(String message)
	{
		String pattern = "[\"]";
		String[] tokens = message.split(pattern);
		
		return tokens[3];
	}
	
	public String parseAddID (String message)
	{
		String pattern = "[\"]";
		String[] tokens = message.split(pattern);
		
		return tokens[1];
	}
	
	public String parseDelete(String message)
	{
		
		String pattern = "[\"]";
		String[] tokens = message.split(pattern);
	
		return tokens[1];
	}
	
	public String parseReplace(String message)
	{
		String pattern = "[\"]";
		String[] tokens = message.split(pattern);
		
		return tokens[3];
	}
	
	public String parseFetchMessage(String message)
	{
		String pattern = "[\"]";
		String[] tokens = message.split(pattern);
		
		return tokens[3];
	}
	
	public String parseAllMsg(String msg)
    {
    	String pattern = "[<>]";
   	 	String[] tokens = msg.split(pattern);
   	 	
   	 	return tokens[1];
    }
}
