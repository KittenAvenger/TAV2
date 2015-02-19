
public class Parser {

	public String parseRequest (String message) {
		
		String delims = "[ ]+";
		String[] tokens = message.split(delims);
		//System.out.println(tokens[2]);
		return tokens[2];
		
	}
	
	public String parseAdd (String message){
		
		String pattern = "[\"]";
		String[] tokens = message.split(pattern);
		
//		for (int i = 0; i < tokens.length; i++)
//		    System.out.println(tokens[i]);
		
//		System.out.println(tokens[3]); // this is the text
//		System.out.println(tokens[1]); // this is the ID
		
		return tokens[3];
	}
	
	public String parseID(String message){
		
		String pattern = "[\"]";
		String[] tokens = message.split(pattern);
		
		return tokens[1];
	}
	
	// need to add if the message doesn't exist or was fetched + 
	//the msg was not deleted then return an ERROR msg
	public String parseDelete (String message){
		
		String pattern = "[\"]";
		String[] tokens = message.split(pattern);
		
//		System.out.println(tokens[1]); // this is the ID
		
		return tokens[1];
	}
	
	public String parseReplace (String message){
		
		String pattern = "[\"]";
		String[] tokens = message.split(pattern);
		
		return tokens[3];
	}
	
	public String parseFetchMessage (String message){
		
		String pattern = "[\"]";
		String[] tokens = message.split(pattern);
		
		return tokens[3];
	}
}
