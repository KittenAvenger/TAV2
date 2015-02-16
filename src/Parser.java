
public class Parser {

	public String parseRequest (String message){
		
		String delims = "[ ]+";
		String[] tokens = message.split(delims);
		
		return tokens[3];
	}
	
	public String parseAdd (String message){
		
		String delims = "[ ]+";
		String[] tokens = message.split(delims);
		
		return tokens[5];
	}
}

