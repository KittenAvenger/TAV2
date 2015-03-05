package unitTests;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.Client;
import server.Kernel;
import server.Server;




public class TestConnection {

	//	Fields for testing purposes

	String accountID = "1234567890", message, receiver = "0702241845", testMsg = "Hey what is up", replaceMsg = "Shit man bacon is on sale";
	static Server server;
	Client client;
	static Thread thisThread;
	
	
	//	Start a server before any tests are run
	
	@BeforeClass
	public static void startServer()
	{
		server = new Server();
		thisThread = new Thread(server);
		thisThread.start();
	}
	
	//	Start a new client before every test
	
	@Before
	public void setUp()
	{
		
		client = new Client();
	}
	
	//	Test that a single client is able to connect to the server
	
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
	
	//	Test that the same client connects with same accountID after already being connectec once
	
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
	
	//	Test connecting and adding a simple message
	
	@Test
	public void testServerAddMessage() throws InterruptedException 
	{

		String msg, ID;

		try 
		{
			client.connect(accountID);
			ID = parse(msg = client.addMessage(receiver, testMsg));
			
			assertEquals("<Message added: '" + ID  + "' />", msg );
			assertEquals("<ErrorMsg> Reason </ErrorMsg>", client.addMessage(receiver, ""));				//Test adding empty message
		} 
		
		catch ( IOException e) 
		{
		    e.printStackTrace();
		}
		
	}
	
	//	Test replacing a message the client just added
	
	@Test
	public void testServerReplaceMessage() throws InterruptedException 
	{
		String ID;
		
		try 
		{
			client.connect(accountID);
			ID = parse(client.addMessage(receiver, testMsg));
			assertEquals("<Message replaced: '" + ID +"' />", client.replaceMessage(ID, replaceMsg));
			assertEquals("<ErrorMsg> Reason </ErrorMsg>", client.replaceMessage(ID, ""));				//Test replacing message with empty text
		} 
		
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
	}
	
	//	Test deleting a message the client just added
	
	@Test
	public void testDeleteMessage()
	{
		String ID;
				
		try 
		{
			client.connect(accountID);
			ID = parse(client.addMessage(receiver, testMsg));
			assertEquals("<Message deleted: '" + ID + "' />", client.deleteMessage(ID));
			assertEquals("<ErrorMsg> Reason </ErrorMsg>", client.deleteMessage(ID + 1));				//Test non existing message ID
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//	Test fetching a message the client just send to himself
	
	@Test
	public void testFetchMessage()
	{
		String fetchMsg = "<FetchedMessages> <Messages> <Sender \"1234567890\" /> <Content \"Hey what is up\" /> </Messages></FetchedMessages>";
		String error = "<ErrorMsg> Message doesn't exist </ErrorMsg>";
		
		try 
		{
			client.connect(accountID);
			client.addMessage(accountID, testMsg);
			
			assertEquals(fetchMsg, client.fetchMessage());
			assertEquals("<FetchedCompleteAck/>", client.fetch_complete_Message());
			assertEquals("<ErrorMsg> No message to delete </ErrorMsg>", client.fetch_complete_Message());
			assertEquals(error, client.fetchMessage());
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//	Test fetching messages when the message list for the client is empty
		
	@Test
	public void testServerFetchNoMessages()
	{
			
		try 
		{
			client.connect(accountID);
			client.addMessage(receiver, testMsg);
			
			assertEquals("<ErrorMsg> " + "Message doesn't exist" + " </ErrorMsg>", client.fetchMessage());
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//	Test adding a message, then another client connects and fetches this message
	
	@Test
	public void testServerFetch2()
	{
			
		try 
		{
			client.connect(accountID);
			client.addMessage(receiver, testMsg);
			client.disconnect();
			client.connect("0702241845");
			String output = "<FetchedMessages> <Messages> <Sender \"1234567890\" /> <Content \"Hey what is up\" /> </Messages></FetchedMessages>";			
			assertEquals(output, client.fetchMessage());
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//	Try to fetch complete after the messages have been deleted
	
	@Test
	public void testServerFetchCompleteNoMessages()
	{
			
		try 
		{
			client.connect(accountID);		
			assertEquals("<ErrorMsg> No message to delete </ErrorMsg>", client.fetch_complete_Message());
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//	Try to fetch complete existing messages
	
	@Test
	public void testServerFetchComplete()
	{
			
		try 
		{
			client.connect(accountID);
			client.addMessage(receiver, testMsg);
			client.disconnect();
			client.connect("0702241845");
			client.fetchMessage();
			assertEquals("<FetchedCompleteAck/>", client.fetch_complete_Message());
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	//	Disconnects the client after every test and clears the message database
	
	@After
	public synchronized void tearDown() throws IOException
	{
		
		assertEquals("Client disconnected", client.disconnect());
		Kernel.server.clear();		
	}
	
	//	Stops the server and asserts that it is shut down
	
	@AfterClass
	public static void shutdownServer()
	{
		server.stop();
		assertTrue(server.isStopped());
	}
	
	//	Parse message ID from the addMessage function
	
	public static String parse(String msg)
	{
		String idD = null;
		String pattern = "[^\\d]*";
		idD = msg.replaceAll(pattern, "");
		
		return idD;
	}		
}
