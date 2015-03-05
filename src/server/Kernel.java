package server;
import java.util.ArrayList;


public class Kernel implements WhatsUpGU {
	
	Message text = new Message ();
	public static ArrayList <Message> server = new ArrayList<Message>();
	static ArrayList <Integer> IDS = new ArrayList<Integer>();
	
	
	public Kernel (){
		
			
	}
	
	//	Returns a message object for testing purposes
	public Message returnMessage (){
		
		return text;
	}
	
	/* 	This function checks if the sender has a 10 digit phone number and 
	*	if the number is an integer. If everything is alright, then it checks if the message is empty
	*	when the message is not empty it generates an ID and adds a new Message to the 
	*	server ArrayList
	*	The method returns an ID
	*	if something goes wrong it returns -1
	*/ 
	
	public int add(String message, String sender, String recipient) {
		int ID;
		
		
		
		if(sender.length() != 10 || recipient.length() != 10)
		{
			return -1;
		}
		else if (!isInteger(sender)||!isInteger(recipient)){
			
			return -1;
		}
				
		
		if(IsNotEmpty(message))
		{
			
			ID = IDS.size() + 1;
			
			
			if(IDS.size() >= 10000)
			{
				return -1;
				
			}
			
			text = new Message (sender, message, ID, recipient, false);
			server.add(text);
			
			IDS.add(ID);
			return ID;
		}
		else
		{
			return -1;
		}
}
	/*
	 * this function checks if the ID received from the user is equal to the Message ID and if it is fetched
	 * if the conditions are true, then the function removes the message from the server ArrayList and 
	 * returns the ID, otherwise returns -1
	 * */
	public int delete(int ID) {
		
		for(int i=0; i<server.size(); i++) {
			if (server.get(i).getID() == ID && server.get(i).isFetched() == false) {
				
				//System.out.println(ID);
				
				server.remove(server.get(i));
				IDS.remove(i);
				return ID;
			} 
		}
		return -1;
		
	}

	/*	Takes a message ID and a message as arguments, if the message is not empty it tries to find the corresponding
	 * 	message based on the ID given. If message was found and successfully replaced it returns the message ID.
	 * 	Otherwise returns a -1.
	 */
	
	public int replace(int ID, String message) {
	if(IsNotEmpty(message)){
		for(int i = 0; i < server.size(); i++) {
			if(server.get(i).getID() == ID && server.get(i).isFetched() != true) {
				server.get(i).setMessage(message);
				return server.get(i).getID();
			}
			
		}
		return -1;
	}
	else{
		return -1;
	}
	}

	/*	Fetches all messages and takes the receiver as an argument. If the messages haven't been fetched previously and contains
	 * 	the receivers phone number it returns a string in xml format. For every message it increments the count variable.
	 * 	If the count variable is not equal to 0 it returns the entire message string, otherwise returns an error message.
	 */
	
	public String fetch(String receiver) {
		
		String message = "";
		int count = 0;
		
		for(int i=0; i<server.size(); i++) {
			if (server.get(i).getReceiver().equals(receiver) && server.get(i).isFetched() == false) {
				message = message +"\n <Messages>"+ 
						"\n <Sender \""+server.get(i).getSender()+"\" />"+
						"\n <Content \""+ server.get(i).getMessage()+"\" />"+
						"\n </Messages>";


				server.get(i).setFetched();
				count++;
			}
		}
		
		if(count!= 0) {
			//fetch_complete(receiver);
			return message;
		}
		else{
			return "Message doesn't exist";
		}	
	}
	
	/*	Takes the receiver as an argument. If the messages include the receiver's phone number and have been fetched they are deleted
	 * 	from the server. Every message increments the count variable by 1. If count doesn't equal 0 it returns a 1 for successsful
	 * 	fetch completion. Otherwise returns -1.
	 */
	
	public int fetch_complete(String receiver) {
		
		int count = 0;
		
		for(int i=0; i<server.size(); i++){
			if (server.get(i).getReceiver().equals(receiver) && server.get(i).isFetched() == true){
				server.remove(i);
				count++;
				IDS.remove(i);
			}
		}
		
		if(count!=0) {
			return 1;
		}
		else {
			return -1;
		} 
	}
		
	
	//	Checks if message is empty
		
	public boolean IsNotEmpty (String message) {
			
			if(message != null && !message.isEmpty()) {
				return true;
			}
			else{
				return false;
			}
		}
		
	//	Checks if value is an integer
		
	public static boolean isInteger(String s) {
		    try { 
		        Integer.parseInt(s); 
		    } catch(NumberFormatException e) { 
		        return false; 
		    }
		    // only got here if we didn't return false
		    return true;
		}
}
