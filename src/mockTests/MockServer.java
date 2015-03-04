package mockTests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import server.Client;
import server.Connection;
import server.Kernel;
import server.Server;


public class MockServer {	
	String receiver = "0700348273";
	String message = "hey whats up";
	
	@Test
	public void testFailFetch()
	{
		Kernel kernel = Mockito.spy(new Kernel());
		//when(kernel.fetch("0702241845")).thenReturn("Message doesn't exist");
		assertEquals(kernel.fetch_complete("0702241845"), -1);
		//kernel.fetch("0702241845");	
		verify(kernel).fetch_complete("0702241845");		
	}
	
	@Test
	public void testMockSocketClientConnect() throws IOException  
	{

		String ID = "0702241845";
		String example = "<Accepted connection from '0702241845' +/>";
		String example2 = "<Request connection  " + ID + " +/>";
	    final Socket socket = mock(Socket.class);
	    Socket socket2= mock(Socket.class);
	    
	    Connection conn = new Connection(socket2);
	    
	    Client client = new Client(){
            @Override
            protected Socket createSocket(String address, int port) {
                return socket;
            }
        };
        
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayOutputStream output2 = new ByteArrayOutputStream();
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input);
	    
	    client.connect("0702241845");
	    
	  	assertEquals(client.returnRequest(), output.toString());
	   
	  	when(socket2.getOutputStream()).thenReturn(output2);
	  	when(socket2.getInputStream()).thenReturn(input2);
	  	
	  	conn.run();
	  	assertEquals(output2.toString(), example + "\r\n");
	  	
	    verify(socket).getOutputStream();
	    verify(socket).getInputStream();
	    verifyNoMoreInteractions(socket);
	}
	
	@Test
	public void testMockSocketClientAddMessage() throws IOException  
	{

		String example = "<Accepted connection  0702241845 +/>/";
	    final Socket socket = mock(Socket.class);
	    
	    Client client = new Client(){
            @Override
            protected Socket createSocket(String address, int port) {
                return socket;
            }
        };
        
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input);
	    
	    client.connect("0702241845");
	    client.addMessage(receiver, message);
	  	assertEquals(client.returnAddMessage(), "<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>");
	   
	    verify(socket, atLeast(2)).getOutputStream();
	    verify(socket, atLeast(2)).getInputStream();
	}
	
	@Test
	public void testMockSocketClientDeleteMessage() throws IOException  
	{
		String ID = "";
		String result = "1";
		String example = "<Accepted connection  0702241845 +/>/";
		String example2 = "<Message added: '" + result + "' />";
		
	    final Socket socket = mock(Socket.class);
	    
	    Client client = new Client(){
            @Override
            protected Socket createSocket(String address, int port) {
                return socket;
            }
        };
        
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    ByteArrayOutputStream output2 = new ByteArrayOutputStream();
	    ByteArrayOutputStream output3 = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	   
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input);
	   
	    
	    client.connect("0702241845");
	    //client.addMessage(receiver, message);+String ID)
	    when(socket.getOutputStream()).thenReturn(output2);
	    when(socket.getInputStream()).thenReturn(input2);
	    
	    ID = parse(client.addMessage(receiver, message));
	    String example3 = "<Message deleted: '" + ID + "' />";
	    ByteArrayInputStream input3 = new ByteArrayInputStream(example3.getBytes());
	    when(socket.getOutputStream()).thenReturn(output3);
	    when(socket.getInputStream()).thenReturn(input3);
	    
	    
	  	assertEquals( client.deleteMessage(ID), "<Message deleted: '" + ID + "' />");
	  	 
	    verify(socket, atLeast(3)).getOutputStream();
	    verify(socket, atLeast(3)).getInputStream();
	}
		
	@Test
	public void testMockSocketClientReplaceMessage() throws IOException  
	{
		String ID = "";
		String result = "1";
		String example = "<Accepted connection  0702241845 +/>/";
		String example2 = "<Message added: '" + result + "' />";
		
	    final Socket socket = mock(Socket.class);
	    
	    Client client = new Client(){
            @Override
            protected Socket createSocket(String address, int port) {
                return socket;
            }
        };
        
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    ByteArrayOutputStream output2 = new ByteArrayOutputStream();
	    ByteArrayOutputStream output3 = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	   
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input);
	   
	    
	    client.connect("0702241845");
	    //client.addMessage(receiver, message);+String ID)
	    when(socket.getOutputStream()).thenReturn(output2);
	    when(socket.getInputStream()).thenReturn(input2);
	    
	    ID = parse(client.addMessage(receiver, message));
	    String example3 = "<Message replaced: '" + ID + "' />";
	    ByteArrayInputStream input3 = new ByteArrayInputStream(example3.getBytes());
	    when(socket.getOutputStream()).thenReturn(output3);
	    when(socket.getInputStream()).thenReturn(input3);
	    
	    
	  	assertEquals( client.replaceMessage(ID,"changed message"), "<Message replaced: '" + ID + "' />");
	  	 
	    verify(socket, atLeast(3)).getOutputStream();
	    verify(socket, atLeast(3)).getInputStream();
	}
		
	@Test
	public void testMockSocketClientFetchMessage() throws IOException  
	{
		
		String result = "1";
		String example = "<Accepted connection  0702241845 +/>/";
		String example2 = "<Message added: '" + result + "' />";
		
	    final Socket socket = mock(Socket.class);
	    
	    Client client = new Client(){
            @Override
            protected Socket createSocket(String address, int port) {
                return socket;
            }
        };
        
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    ByteArrayOutputStream output2 = new ByteArrayOutputStream();
	    ByteArrayOutputStream output3 = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	   
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input);
	   
	    
	    client.connect("0702241845");
	   
	    when(socket.getOutputStream()).thenReturn(output2);
	    when(socket.getInputStream()).thenReturn(input2);
	    
	    
	    client.connect("0700348273");
	    String mss= "<Messages> <Sender \"0702241845\" /> <Content \"hey whats up\" /> </Messages>";
	    String example3 = "<FetchedMessages> " + mss + " </FetchedMessages>";
	    ByteArrayInputStream input3 = new ByteArrayInputStream(example3.getBytes());
	    when(socket.getOutputStream()).thenReturn(output3);
	    when(socket.getInputStream()).thenReturn(input3);
	    
	    
	  	assertEquals(client.fetchMessage(), example3);
	  	 
	    verify(socket, atLeast(3)).getOutputStream();
	    verify(socket, atLeast(3)).getInputStream();
	}
		
	private String parse(String msg)
		{
			String idD = null;
			String pattern = "[^\\d]*";
			idD = msg.replaceAll(pattern, "");
			
			return idD;
		}
	
	@Test
	public void testConnectionException() throws IOException
	{
		Client client = mock(Client.class);
		doThrow(new IOException()).when(client).disconnect();
		doThrow(new IOException()).when(client).connect("test");
		doThrow(new IOException()).when(client).addMessage("test", "test");
		doThrow(new IOException()).when(client).replaceMessage("test", "test");
		
		Exception caught = null;
		try
		{
			client.connect("test");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			caught = e;
		}
		
		assertNotNull(caught);
	}

	@Test
	public void testServerException()
	{
		Server server = spy(new Server());
		doThrow(new RuntimeException()).when(server).stop();
		
		
		Exception caught = null;
		try
		{
			server.stop();
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
			caught = e;
		}
		
		assertNotNull(caught);
	}
	
	@After
	public synchronized void tearDown() throws IOException
	{
		Server.ProcessIDList.clear();
	}
	
//	@Test
//	public void testMockServer() throws IOException
//	{
//		final ServerSocket serverSocketmock = mock(ServerSocket.class);
//		Socket socket = mock(Socket.class);
//		ByteArrayOutputStream output = new ByteArrayOutputStream();
//		
//		Server server = new Server(){
//			public synchronized void start()
//			{
//				System.out.println("Server started");
//				
//				serverSocket = serverSocketmock;
//			}
//		};
//		
//		server.run();
//	}
}

