import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestConnection {


	String accountID = "1234567890", message, receiver = "0702241845", testMsg = "Hey what is up", replaceMsg = "Shit man bacon is on sale";
	Server server;
	Client client;
	Thread thisThread;
	
	
	@Before
	public void setUp()
	{
		server = new Server();
		thisThread = new Thread(server);
		thisThread.start();
		client = new Client();
	}
	
	
	@Test
	public void testSingleConnection() throws InterruptedException 
	{
		
		try 
		{
			assertEquals("<Accepted connection from '1234567890' +/>", client.connect(accountID));  
		} 
		
		catch ( IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void testMultipleConnections() throws InterruptedException
	{
			Client client2 = new Client();
			Client client3 = new Client();
		
		try 
		{
			client.connect(accountID);
			assertEquals("Connection denied", client2.connect(accountID));
			assertEquals("Connection denied", client3.connect(accountID));
		} 
		
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
	}
	
	@Test
	public void testServerAddMessage() throws InterruptedException 
	{

		String msg, ID;

		try 
		{
			client.connect(accountID);
			ID = parse(msg = client.addMessage(receiver, testMsg));
			
			assertEquals("<Message added: '" + ID  + "' />", msg );
		} 
		
		catch ( IOException e) 
		{
		    e.printStackTrace();
		}
		
	}
	
	@Test
	public void testServerReplaceMessage() throws InterruptedException 
	{
		String ID;
		
		try 
		{
			client.connect(accountID);
			ID = parse(client.addMessage(receiver, testMsg));
			assertEquals("<Message replaced: '" + ID +"' />", client.replaceMessage(ID, replaceMsg));
		} 
		
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteMessage()
	{
		String ID;
				
		try 
		{
			client.connect(accountID);
			ID = parse(client.addMessage(receiver, testMsg));
			assertEquals("<Message deleted: '" + ID + "' />", client.deleteMessage(ID));
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() throws IOException
	{
		
		if(client != null)
		{
			assertEquals("Client disconnected", client.disconnect());
		}
		
		server.stop();
		assertTrue(server.isStopped);
		
	}
	
	private String parse(String msg)
	{
		String idD = null;
		String pattern = "[^\\d]*";
		idD = msg.replaceAll(pattern, "");
		
		return idD;
	}

}
