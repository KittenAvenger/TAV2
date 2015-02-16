import static org.junit.Assert.*;

import org.junit.Test;


public class TestParser {

	@Test
	public void testParseConnection() {
		
		String test = "<Accepted connection from  1234 +/>";
		Parser parse = new Parser();
		String ID = parse.parseRequest(test);
		
		assertEquals("1234", ID);
	}

	@Test
	public void testParseAdd() {
		
		String test = "<AddMessage> <Receiver ID /> <Content TEXT /> </AddMessage>";
		Parser parse = new Parser();
		String msg = parse.parseAdd(test);
		
		assertEquals("TEXT", msg);
	}
	
}
