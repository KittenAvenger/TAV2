import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TestConnection {

	@Before
	public void setUp(){
		Server server = new Server();
		new Thread(server).start();
		
	}
	
	@Test
	public void testServerReply() {
		
		Client client = new Client();
		String message = null;
		
		try {
		message = client.connect();
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
		assertEquals("<Accepted connection from  1234 +/>", message );
	}

}
