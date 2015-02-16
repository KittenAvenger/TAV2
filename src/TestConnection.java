import static org.junit.Assert.*;

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
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		server.stop();
		assertEquals("<Accepted connection from  1234 +/>", message );
	}
	
	@Test
	public void testServerStopped(){
		Server server = new Server();
		new Thread(server).start();
		
		Client client = new Client();
		
		try {
			client.connect();
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		server.stop();
		
		assertTrue(server.isStopped());
	}
	
	@Test
	public void testMultipleConnection(){
		Server server = new Server();
		new Thread(server).start();
		
		Client client = new Client();
		
		
		try {
			client.connect();
			client.connect();
		    Thread.sleep(700);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
		assertTrue(server.idExists);
	}

}
