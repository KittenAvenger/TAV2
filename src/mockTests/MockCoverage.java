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

	@Test
	public void testMockInvalidReceiver() throws IOException 
	{
		Socket socket = mock(Socket.class);
		Connection conn = new Connection(socket);
		String ID = "0702241845";
		String receiver = "070224184";
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
	    Server.ProcessIDList.clear();
	}
	
	@Test
	public void testMockInvalidSender() throws IOException 
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
	    Server.ProcessIDList.clear();
	}
	
	@Test
	public void testMockInvalidSenderAndReceiver() throws IOException 
	{
		Socket socket = mock(Socket.class);
		Connection conn = new Connection(socket);
		String ID = "070224184";
		String receiver = "070224184";
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
	    Server.ProcessIDList.clear();
	}
	
	@Test
	public void testMockAddMessage() throws IOException 
	{
		Socket socket = mock(Socket.class);
		Connection conn = new Connection(socket);
		String ID = "0702241845";
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
	    assertEquals(output.toString(), "<Accepted connection from '" + ID + "' +/>\r\n" + "<Message added: '1' />\r\n");
	    Server.ProcessIDList.clear();
	}

}
