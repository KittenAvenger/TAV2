import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;


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
	    
	  	assertEquals(client.returnRequest(), output.toString());
	   
	    verify(socket).getOutputStream();
	    verify(socket).getInputStream();
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
	   
	    when(socket.getOutputStream()).thenReturn(output2);
	    when(socket.getInputStream()).thenReturn(input2);
	    
	    ID = parse(client.addMessage(receiver, message));
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
	
}
