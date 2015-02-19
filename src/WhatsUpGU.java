

public interface WhatsUpGU {
	
	/**

	  Description: Add a non-empty message from the sender to the server

	  Pre-condition: Non-empty string, valid sender and recipient number

	  Post-condition: Returns unique integer ID for successful addition of message, message is added to the server
	  
	  Test-cases: Non-valid numbers, empty messages, normal function, non-unique ID

	*/

	public int add (String message, String sender, String recipient);
	
	/**

	  Description: Delete a message before it is fetched by the recipient

	  Pre-condition: Message hasn't been fetched, existing message ID

	  Post-condition: ID of message is returned, message is deleted 
	  
	  Test-cases: Non-existing ID, message has been fetched, normal function

	*/
	
	public int delete ( int ID);
	
	/**

	  Description: Replace a message before it is fetched by the recipient

	  Pre-condition: Provide existing message ID, add non-empty string, message hasn't been fetched yet

	  Post-condition: Old message replaced by new one, ID of message is returned
	  
	  Test-cases: Non-existing ID, message has been fetched, empty string provided, normal function

	*/
	
	public int replace (int ID, String message);

	/**
	  Description: when a cell-phone gets connected to the Internet, 
	  				it contacts the server and fetches the messages sent to it since 
	  				its last time online.

	  Pre-condition: Internet is connected. Receiver provides his id (phone number).

	  Post-condition: All messages since last log in are returned as string.
	  
	  Test-cases: Receiver id matches with receiver id of message object, message is fetched
	*/
	
	public String fetch (String recipient);
	
	/**
	  Description: When the recipient has fetched all of his messages,
	   				the messages will be removed from the server; the sender will also be signalled to
	    			mark the fetched messages at its client user interface. Fetch complete will return 
	    			1 if it has been successful or -1 number indicating all messages are not deleted.

	  Pre-condition: Receiver provides his id (phone number), Messages are fetched.

	  Post-condition: Returns 1 if all messages for this receiver are deleted. 
	  				Returns -1 messages are not deleted. 
	  
	  Test-cases: Receiver id matches with receiver id of message object, message is fetched
	*/
	
	public int fetch_complete (String receiver);
}


