package server;

public class Message {
	
	String message, receiver, sender;
	int ID;
	boolean fetched;
	
	public Message(){
		
	}
	
	

	public Message (String sender, String message, int ID, String receiver, boolean fetched) {
		
		this.message = message;
		this.ID = ID;
		this.receiver = receiver;
		this.sender = sender;
		fetched = false;
	}
	
	public String getMessage (){
		
		return message;
	}
	
	public void setMessage (String message){
		
		this.message = message;
	}
	
	public int getID (){
		
		return ID;
	}
	
	
	public String getReceiver() {
		
		return receiver;
	}
	
	public String getSender(){
		
		return sender;
	}
	
	public boolean isFetched(){
		
		return fetched;
	}
	
	public void setFetched(){
		
		fetched = true;
	}

}
