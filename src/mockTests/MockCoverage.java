package mockTests;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.junit.Test;

import server.Connection;
import server.Server;

public class MockCoverage 
{
	

	//	Mock the socket and add an empty message
	
	@Test
	public void testMockAddMessageInvalidReceiver() throws IOException 
	{
		Socket socket = mock(Socket.class);
		Connection conn = new Connection(socket);
		String ID = "0702241845";
		String receiver = "07022418aa";
		String message = "";
		String example = "<Request connection  " + ID + " +/>";
		String example2 = "<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>";
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input).thenReturn(input2);	 
	    conn.run();
	    assertEquals(output.toString(), "<Accepted connection from '" + ID + "' +/>\r\n" + "<ErrorMsg> Reason </ErrorMsg>\r\n");
	    Server.getProcessIDList().clear();
	    
	}
	
	//	Mock the socket and add a message
	
	@Test
	public void testMockAddMessageInvalidSender() throws IOException 
	{		
		Socket socket = mock(Socket.class);
		Connection conn = new Connection(socket);
		String ID = "070224184";
		String receiver = "0702241845";
		String message = "Hey what is up";
		String example = "<Request connection  " + ID + " +/>";
		String example2 = "<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>";
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input).thenReturn(input2);	 
	    conn.run();
	    assertEquals(output.toString(), "<Accepted connection from '" + ID + "' +/>\r\n" + "<ErrorMsg> Reason </ErrorMsg>\r\n");
	    Server.getProcessIDList().clear();
	}
	
	@Test
	public void testMockClientDisconnect() throws IOException 
	{		
		Socket socket = mock(Socket.class);
		Connection conn = new Connection(socket);
		String ID = "0702241845";		
		String example = "<Request connection  " + ID + " +/>";
		String example2 = "Disconnect";
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	    
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input).thenReturn(input2);	 
	    
	    conn.run();
	    
	    assertEquals(output.toString(), "<Accepted connection from '" + ID + "' +/>\r\n" + "Client disconnected\r\n");
	    Server.getProcessIDList().clear();
	}
	
	////Sender sends message and try to delete message with correct message id.
	
	@Test
	public void testMockDeleteMessage() throws IOException 
	{
		Socket socket = mock(Socket.class);
		Connection conn = new Connection(socket);
		
		String ID = "0702241845";
		String receiver = "0702241845";
		String message = "Hey what is up";
		String example = "<Request connection  " + ID + " +/>";
		String example2 = "<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>";
		String example3 = "<DelMessage> <MsgId \"1\" /> </DelMessage>";
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ByteArrayOutputStream output2 = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	    ByteArrayInputStream input3 = new ByteArrayInputStream(example3.getBytes());
	    
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input).thenReturn(input2);
	    conn.run();
	   
	    when(socket.getOutputStream()).thenReturn(output2);
	    when(socket.getInputStream()).thenReturn(input3);
	    conn.handleRequest();
	    
	    assertEquals(output2.toString(),  "<Message deleted: \'1\' />\r\n");
	    Server.getProcessIDList().clear();
	}
	
	////Sender sends message and try to delete message with invalid message id.
	
	@Test
	public void testMockDeleteMessageInvalidMsgID() throws IOException 
	{
		Socket socket = mock(Socket.class);
		Connection conn = new Connection(socket);
		
		String ID = "0702241845";
		String receiver = "0702241845";
		String message = "Hey what is up";
		String example = "<Request connection  " + ID + " +/>";
		String example2 = "<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>";
		String example3 = "<DelMessage> <MsgId \"100\" /> </DelMessage>";
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ByteArrayOutputStream output2 = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	    ByteArrayInputStream input3 = new ByteArrayInputStream(example3.getBytes());
	    
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input).thenReturn(input2);
	    conn.run();
	   
	    when(socket.getOutputStream()).thenReturn(output2);
	    when(socket.getInputStream()).thenReturn(input3);
	    conn.handleRequest();
	    
	    assertEquals(output2.toString(),  "<ErrorMsg> Reason </ErrorMsg>\r\n");
	    Server.getProcessIDList().clear();
	}
	
	//Sender sends message and try to replace message with correct message id.
	
	@Test
	public void testMockReplaceMessage() throws IOException 
	{
		Socket socket = mock(Socket.class);
		Connection conn = new Connection(socket);
		
		String ID = "0702241845";
		String receiver = "0702241845";
		String message = "Hey what is up";
		String example = "<Request connection  " + ID + " +/>";
		String example2 = "<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>";
		String example3 = "<RplMessage> <MsgId \"2\" /> <Content \"Hello\" /> </RplMessage>";
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ByteArrayOutputStream output2 = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	    ByteArrayInputStream input3 = new ByteArrayInputStream(example3.getBytes());
	    
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input).thenReturn(input2);
	    conn.run();
	   
	    when(socket.getOutputStream()).thenReturn(output2);
	    when(socket.getInputStream()).thenReturn(input3);
	    conn.handleRequest();
	    
	    assertEquals(output2.toString(),  "<Message replaced: \'2\' />\r\n");
	    Server.getProcessIDList().clear();
	}
	
	//Sender sends a message. And he try to replace message with an invalid message id.
	
	@Test
	public void testMockReplaceMessageInvalidMsgID() throws IOException 
	{
		Socket socket = mock(Socket.class);
		Connection conn = new Connection(socket);
		
		String ID = "0702241845";
		String receiver = "0702241845";
		String message = "Hey what is up";
		String example = "<Request connection  " + ID + " +/>";
		String example2 = "<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>";
		String example3 = "<RplMessage> <MsgId \"100\" /> <Content \"Hello\" /> </RplMessage>";
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ByteArrayOutputStream output2 = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	    ByteArrayInputStream input3 = new ByteArrayInputStream(example3.getBytes());
	    
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input).thenReturn(input2);
	    conn.run();
	   
	    when(socket.getOutputStream()).thenReturn(output2);
	    when(socket.getInputStream()).thenReturn(input3);
	    conn.handleRequest();
	    
	    assertEquals(output2.toString(),  "<ErrorMsg> Reason </ErrorMsg>\r\n");
	    Server.getProcessIDList().clear();
	}
	
	//Client try to fetch messages, Given that he has some messages to fetch.
	
	@Test
	public void testMockFetchMessage() throws IOException 
	{
		Socket socket = mock(Socket.class);
		Connection conn1 = new Connection(socket);
		Connection conn2 = new Connection(socket);
		
		String ID = "0702241845";
		String receiver = "0702241850";
		String message = "Hey what is up";
		String example = "<Request connection  " + ID + " +/>";
		String example2 = "<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>";
		String example3 = "<Request connection  " + receiver+ " +/>";
		String example4 = "<FetchMessages/>";
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ByteArrayOutputStream output2 = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	    ByteArrayInputStream input3 = new ByteArrayInputStream(example3.getBytes());
	    ByteArrayInputStream input4 = new ByteArrayInputStream(example4.getBytes());
	    
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input).thenReturn(input2);
	    conn1.run();
	   
	    when(socket.getOutputStream()).thenReturn(output2);
	    when(socket.getInputStream()).thenReturn(input3).thenReturn(input4);
	    conn2.run();
	    String msg = " <Messages>\n <Sender \"0702241845\" />\n <Content \""+message+"\" />\n </Messages>";
	    assertEquals(output2.toString(),  "<Accepted connection from '" + receiver + "' +/>\r\n" + "<FetchedMessages>\n" + msg + "\n</FetchedMessages>\r\n");
	    Server.getProcessIDList().clear();
	}
	
	//Client try to fetch messages, Given that he have no messages to fetch.
	
	@Test
	public void testMockFetchNoMessages() throws IOException 
	{
		Socket socket = mock(Socket.class);
		Connection conn1 = new Connection(socket);
		Connection conn2 = new Connection(socket);
		
		String ID = "0702241845";
		String receiver = "0702241846";
		String message = "Hey what is up";
		String example = "<Request connection  " + ID + " +/>";
		String example2 = "<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>";
		String example3 = "<Request connection  0702241847 +/>";
		String example4 = "<FetchMessages/>";
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ByteArrayOutputStream output2 = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	    ByteArrayInputStream input3 = new ByteArrayInputStream(example3.getBytes());
	    ByteArrayInputStream input4 = new ByteArrayInputStream(example4.getBytes());
	    
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input).thenReturn(input2);
	    conn1.run();
	   
	    when(socket.getOutputStream()).thenReturn(output2);
	    when(socket.getInputStream()).thenReturn(input3).thenReturn(input4);
	    conn2.run();
	    
	    assertEquals(output2.toString(),  "<Accepted connection from '0702241847' +/>\r\n" + "<ErrorMsg> Message doesn't exist </ErrorMsg>\r\n");
	    Server.getProcessIDList().clear();
	}
	
	// Sender send message. Receiver fetch his messages and then perform fetchComplete.
	
	@Test
	public void testMockFetchComplete() throws IOException 
	{
		Socket socket = mock(Socket.class);
		Connection conn1 = new Connection(socket);
		Connection conn2 = new Connection(socket);
		
		String ID = "0702241845";
		String receiver = "0702241860";
		String message = "Hey what is up";
		String example = "<Request connection  " + ID + " +/>";
		String example2 = "<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>";
		String example3 = "<Request connection "+receiver+" +/>";
		String example4 = "<FetchMessages/>";
		String example5 = "<FetchComplete/>";

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ByteArrayOutputStream output2 = new ByteArrayOutputStream();
		ByteArrayOutputStream output3 = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	    ByteArrayInputStream input3 = new ByteArrayInputStream(example3.getBytes());
	    ByteArrayInputStream input4 = new ByteArrayInputStream(example4.getBytes());
	    ByteArrayInputStream input5 = new ByteArrayInputStream(example5.getBytes());
	    
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input).thenReturn(input2);
	    conn1.run();
	  
	    when(socket.getOutputStream()).thenReturn(output2);
	    when(socket.getInputStream()).thenReturn(input3).thenReturn(input4);
	    conn2.run();
	    
	    when(socket.getOutputStream()).thenReturn(output3);
	    when(socket.getInputStream()).thenReturn(input5);
	    conn2.handleRequest();
	    
	    assertEquals(output3.toString(), "<FetchedCompleteAck/>\r\n");
	    Server.getProcessIDList().clear();
	}
	
	//Sender sends a message. Receiver tries to perform fetchComplete without fetching messages.
	
	@Test
	public void testMockFetchCompleteFail() throws IOException 
	{
		Socket socket = mock(Socket.class);
		Connection conn1 = new Connection(socket);
		Connection conn2 = new Connection(socket);
		
		String ID = "0702241845";
		String receiver = "0702241860";
		String message = "Hey what is up";
		String example = "<Request connection  " + ID + " +/>";
		String example2 = "<AddMessage> <Receiver \"" + receiver + "\" /> <Content \"" + message + "\" /> </AddMessage>";
		String example3 = "<Request connection "+receiver+" +/>";
		String example4 = "<FetchComplete/>";
	
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ByteArrayOutputStream output2 = new ByteArrayOutputStream();
	    ByteArrayInputStream input = new ByteArrayInputStream(example.getBytes());
	    ByteArrayInputStream input2 = new ByteArrayInputStream(example2.getBytes());
	    ByteArrayInputStream input3 = new ByteArrayInputStream(example3.getBytes());
	    ByteArrayInputStream input4 = new ByteArrayInputStream(example4.getBytes());
	    
	    when(socket.getOutputStream()).thenReturn(output);
	    when(socket.getInputStream()).thenReturn(input).thenReturn(input2);
	    conn1.run();
	  
	    when(socket.getOutputStream()).thenReturn(output2);
	    when(socket.getInputStream()).thenReturn(input3).thenReturn(input4);
	    conn2.run();
	    
	    assertEquals(output2.toString(), "<Accepted connection from '0702241860' +/>\r\n" + "<ErrorMsg> No message to delete </ErrorMsg>\r\n");
	    Server.getProcessIDList().clear();
	}	
}
