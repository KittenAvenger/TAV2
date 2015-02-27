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
	
	
	
	//@Test
	public void serverTestAdd(){
		fail();
	
	}
	
	//@Test
	public void serverTestReplace(){
		
	}
	
	//@Test
	public void serverTestDelete(){
	
		
	}
	//@Test
	public void serverTestFetch(){
		
			
	}
	
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
	    
	  	assertEquals(client.returnRequest(), output.toString());
	   
	    verify(socket).getOutputStream();
	    verify(socket).getInputStream();
	}
}
