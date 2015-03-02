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


	String accountID = "1234567890", message, receiver = "0702241845", testMsg = "Hey what is up", replaceMsg = "Shit man bacon is on sale";
	static Server server;
	Client client;
	static Thread thisThread;
	
	
	@BeforeClass
	public static void startServer()
	{
		server = new Server();
		thisThread = new Thread(server);
		thisThread.start();
	}
	
	@Before
	public void setUp()
	{
		
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
			assertEquals("<ErrorMsg> Reason </ErrorMsg>", client.addMessage(receiver, ""));				//Test adding empty message
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
			assertEquals("<ErrorMsg> Reason </ErrorMsg>", client.replaceMessage(ID, ""));				//Test replacing message with empty text
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
			assertEquals("<ErrorMsg> Reason </ErrorMsg>", client.deleteMessage(ID + 1));				//Test non existing message ID
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFetchMessage()
	{
		String fetchMsg = "<FetchedMessages> <Messages> <Sender \"1234567890\" /> <Content \"Hey what is up\" /> </Messages> </FetchedMessages>";
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
		
	@Test
	public void testServerFetch1()
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
	
	@Test
	public void testServerFetch2()
	{
			
		try 
		{
			client.connect(accountID);
			client.addMessage(receiver, testMsg);
			client.disconnect();
			client.connect("0702241845");
			String output = "<FetchedMessages> <Messages> <Sender \"1234567890\" /> <Content \"Hey what is up\" /> </Messages> </FetchedMessages>";
			assertEquals(output, client.fetchMessage());
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testServerFetch_complete1()
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
	
	@Test
	public void testServerFetch_complete2()
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
	
	
	
	@After
	public void tearDown() throws IOException
	{
		
		if(client != null)
		{
			assertEquals("Client disconnected", client.disconnect());
		}
		
		
		Kernel.server.clear();
		
	}
	
	@AfterClass
	public static void shutdownServer()
	{
		server.stop();
		assertTrue(server.isStopped());
	}
	
	public static String parse(String msg)
	{
		String idD = null;
		String pattern = "[^\\d]*";
		idD = msg.replaceAll(pattern, "");
		
		return idD;
	}		
}