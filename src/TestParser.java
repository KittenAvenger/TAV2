import static org.junit.Assert.*;

import org.junit.Test;


public class TestParser {

	@Test
	public void testParseConnection() {
		
		String test = "<Request connection  1234 +/>";
		Parser parse = new Parser();
		String ID = parse.parseRequest(test);
		
		assertEquals("1234", ID);
	}

	@Test
	public void testParseAdd() {
		
		String test = "<AddMessage> <Receiver \"ID\" /> <Content \"This is my message's.\" /> </AddMessage>";
		Parser parse = new Parser();
		String msg = parse.parseAdd(test);
		
		assertEquals("This is my message's.", msg);
	}
	
	@Test
	public void testParseDelete() {
		
		// need to add if the message doesn't exist or was fetched + 
		//the msg was not deleted then return an ERROR msg
		
		String test = "<DelMessage> <MsgId \"ID\" /> </DelMessage>";
		Parser parse = new Parser();
		String msg = parse.parseDelete(test);
		
		assertEquals("ID", msg);
	}
	
	@Test
	public void testParseReplace() {
		
		String test = "<RplMessage> <MsgId \"ID\" /> <Content \"TEXT\" /> </RplMessage>";
		Parser parse = new Parser();
		String msg = parse.parseReplace(test);
		
		assertEquals("TEXT", msg);
	}
	
	@Test
	public void testParseIDAdd() {
		
		String test = "<AddMessage> <Receiver \"ID\" /> <Content \"This is my message's.\" /> </AddMessage>";
		Parser parse = new Parser();
		String msg = parse.parseAddID(test);
		
		assertEquals("ID", msg);
	}
	
	@Test
	public void testParseIDReplace() {
		
		String test = "<RplMessage> <MsgId \"ID\" /> <Content \"TEXT\" /> </RplMessage>";
		Parser parse = new Parser();
		String msg = parse.parseAddID(test);
		
		assertEquals("ID", msg);
	}
	
	@Test
	public void testParseFetchID() {
		
		String test = "<Messages> <Sender \"ID\" /> <Content \"TEXT\" /> </Message>";
		Parser parse = new Parser();
		String msg = parse.parseAddID(test);
		
		assertEquals("ID", msg);
	}
	
	@Test
	public void testParseFetchMessage() {
		
		String test = "<Messages> <Sender \"ID\" /> <Content \"TEXT\" /> </Message>";
		Parser parse = new Parser();
		String msg = parse.parseFetchMessage(test);
		
		assertEquals("TEXT", msg);
	}
	
	
}
