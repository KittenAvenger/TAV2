package unitTests;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import server.Client;
import server.ConnectionHandler;
import server.Server;


public class TestPerformance 
{
	
	static Server server;
	static Thread thread;
	
	@BeforeClass
	public static void startServer()
	{
		server = new Server();
		thread = new Thread(server);
		thread.start();
	}

	@Test
	public void testMultipleFetches() throws Exception
		{
			Client [] list = initClients(160);
			String ID, msg;
			
			for (int i = 0; i < list.length; i++)
			{
				assertEquals("<Accepted connection from '" + list[i].returnID()  + "' +/>", list[i].returnConn());
			}
			
			
			
			Client [] addList = addMessageClients(list);
			for (int i = 0; i < addList.length; i++)
			{
				ID = TestConnection.parse(msg = list[i].returnMsgID());
				
				assertEquals("<Message added: '" + ID  + "' />", msg );				
			}
			
			Client [] newList = reconnectClients(list);
			for (int i = 0; i < newList.length; i++)
			{
				assertNotEquals("<Accepted connection from '" + list[i].returnID()  + "' +/>", list[i].returnConn());
			}
			
		}
		
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
	
	@AfterClass
	public static void stopServer()
	{
		server.stop();
		assertTrue(server.isStopped());
	}

	@Test
	public void testIncorrectClientRequest()
	{
		String receiver = "070224184";
		String message = null;
		String input = "<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>";
		ConnectionHandler handler = new ConnectionHandler("test", "test", "test");
		ConnectionHandler handler2 = new ConnectionHandler("AddMessage", input, "0702241845");
		assertEquals(handler.handleMessage(), "Message didn't match");
		assertEquals(handler2.handleMessage(), "<ErrorMsg> Reason </ErrorMsg>");
	}
}
