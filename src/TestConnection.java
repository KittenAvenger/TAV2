import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;


public class TestConnection {


	@Test
	public void testServerReply() {
		Server server = new Server();
		new Thread(server).start();
		
		Client client = new Client();
		String message = null;
		
		try {
		message = client.connect();
		    Thread.sleep(1000);
		} catch (InterruptedException | IOException e) {
		    e.printStackTrace();
		}
		server.stop();
		assertEquals("<Accepted connection from '1234567890' +/>", message );
	}
	
	@Test
	public void testServerStopped(){
		Server server = new Server();
		new Thread(server).start();
		
		Client client = new Client();
		
		try {
			client.connect();
		    Thread.sleep(1000);
		} catch (InterruptedException | IOException e) {
		    e.printStackTrace();
		}
		server.stop();
		
		assertTrue(server.isStopped());
	}
	
	@Test
	public void testMultipleConnection(){
		Server server = new Server();
		new Thread(server).start();
		String message = null;
		
		Client client = new Client();
		Client client2 = new Client();
		Client client3 = new Client();
		
		try {
			client.connect();
			message = client2.connect();
			message = client3.connect();
		    Thread.sleep(700);
		} catch (InterruptedException | IOException e) {
		    e.printStackTrace();
		}
		server.stop();
		assertEquals("Connection denied", message);
	}
	
	@Test
	public void testServerAddMessage() {
		Server server = new Server();
		new Thread(server).start();
		
		Client client = new Client();
		String message = null;
		String add = null;
		
		try {
		message = client.connect();
		add = client.addMessage();
		
		    Thread.sleep(1000);
		} catch (InterruptedException | IOException e) {
		    e.printStackTrace();
		}
		server.stop();
		String pattern = "[^\\d]*";
		String idD = add.replaceAll(pattern, "");
		//System.out.println(idD);
		
//		String[] tokens = add.split(pattern);
//		
//		for (int i = 0; i < tokens.length; i++)
//	    System.out.println(tokens[i] + "ffffff");
		
		assertEquals("<Message added: '" +idD +"' />", add );
	}

}
