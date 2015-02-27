import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;




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
	
	//@Test
	public void testMultipleFetches() throws Exception
	{
		Client [] list = initClients(160);
		String ID, msg;
		
		for (int i = 0; i < list.length; i++)
		{
			assertEquals("<Accepted connection from '" + list[i].returnID()  + "' +/>", list[i].returnConn());
		}
		
//		Client [] newList = reconnectClients(list);
//		for (int i = 0; i < newList.length; i++)
//		{
//			assertNotEquals("<Accepted connection from '" + list[i].returnID()  + "' +/>", list[i].returnConn());
//		}
		
		Client [] addList = addMessageClients(list);
		for (int i = 0; i < addList.length; i++)
		{
			ID = parse(msg = list[i].returnMsgID());
			
			assertEquals("<Message added: '" + ID  + "' />", msg );
			//assertNotEquals("<Accepted connection from '" + list[i].returnID()  + "' +/>", list[i].returnConn());
		}
		
		server.stop();
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
		assertTrue(server.isStopped);
	}
	
	private String parse(String msg)
	{
		String idD = null;
		String pattern = "[^\\d]*";
		idD = msg.replaceAll(pattern, "");
		
		return idD;
	}
	
//	//@Test
//	public void testPhone()
//	{
//		boolean flag = false;
//		int number =Integer.parseInt(randomizePhoneNumber());
//		
//		if(number < 100000000 || number > 999999999)
//		{
//			flag = true;
//		}
//	
//		assertFalse(flag);
//	}
	
	private Client [] initClients(int clients) throws IOException
	{
		Client [] clientList = new Client [clients];
		for(int i = 0; i < clients; i++)
		{
			clientList[i] = new Client ();
			clientList[i].connect(randomizePhoneNumber());
		}
		
		return clientList;
	}

	private Client [] reconnectClients(Client[] list) throws IOException
	{
		
		for(int i = 0; i < list.length; i++)
		{
			
			list[i].connect(list[i].returnID());
		}
		
		return list;
	}
	
	private Client [] addMessageClients(Client[] list) throws Exception
	{
		
		for(int i = 0; i < list.length; i++)
		{
			String message = RandomStringGenerator.generateRandomString(1000, RandomStringGenerator.Mode.ALPHANUMERIC);
			
			if(i < list.length - 1)
			{
				list[i].addMessage(list[i + 1].returnID(), message );
			}
			
			else
			{
				list[i].addMessage(list[0].returnID(), message );
			}
			
		}
		
		return list;
	}
	
	private String randomizePhoneNumber()
	{
		Random rand = new Random();
		int max = 999999999; 
		int min = 100000000;
		int randomNum = rand.nextInt((max - min) + 1) + min;
		
		return "0" + Integer.toString(randomNum);
	}
}
