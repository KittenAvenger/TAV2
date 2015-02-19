import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class Connection implements Runnable{

    Socket clientSocket = null;
    String input;
    String text = "<Accepted connection from  1234 +/>";
    String sender, test;
    int result = 0;

    public Connection(Socket clientSocket) {
        this.clientSocket = clientSocket;
        
    }

    public void run() {
        try {
        	
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
		 
		 while ((input = in.readLine()) != null && !input.isEmpty())  {
		        System.out.println("From client: " + input);	
		        
		        //out.println(text + "\n");
		    	
		    	Parser parse= new Parser();
		    	String ID = parse.parseRequest(input);
		        if (existsID(ID)==false){
		        	out.println("Connection denied");
		        	out.flush();
		        	
		        //System.out.println("connection refused");

				}
				else if (existsID(ID)==true){
					out.println("<Accepted connection from '"+ID+"' +/>");
					//out.close();
			        //.close();
					sender = ID;
			        Server.ProcessIDList.add(ID);
			        //System.out.println("Request processed");
			         
			         while(true){			        	
			        	 //System.out.println(input + " jdjdjdjd");
			        	 while ((input = in.readLine()) != null && !input.isEmpty())  {
			 		        System.out.println("From client: " + input);
			 		        break;
			        	 }
			        	 
			        	 if(input.length() == 0){
			        		 
			        	 }
			        	 else{
			        	 test = parse(input);
		        	 
//			        	 for (int i = 0; i < tokens.length; i++){
//			        		 //System.out.println(tokens[i] + " kaki");
//			        	 }
//			        	System.out.println(operation + " kdjfkajdkf");
			        	Parser parser= new Parser();
			        	Kernel karn = new Kernel();
			        	
			        	//System.out.println(test);
			        	switch(test){
			            	case "AddMessage":
			            		
			            		
			            		String message ="";
			            		String sender ="1";
			            		String recipent ="";
			            		recipent = parser.parseAddID(input);
			            		message = parser.parseAdd(input);
			            		sender = ID;
			            		//System.out.println(message);
			            		this.result = karn.add(message, sender, recipent);
			            		if(result==-1){
			            			//System.out.println("<ErrorMsg> Reason </ErrorMsg>");
			            			out.println("<ErrorMsg> Reason </ErrorMsg>");
			            			break;
			            		}
			            		else{
			            			//System.out.println("<Message added: '"+result+"' />");
			            			out.println("<Message added: '" + result + "' />");
			            			break;
			            		}
			            		
			            		
			            		
			            		
			            	
//			            	case "<DelMessage>":
//			            		
//			            		break;
//			            		
//			            	case "<RplMessage>":
//			            		
//			            		break;
//			            	case "<FetchMessages/>":
//			            		
//			            		break;
//			            	case "<FetchComplete/>":
//			            		
//			            		break;
			            		
			       
			            	
			            }
			        	
			        	 } 
			        	 
			         }
				}
		    }    
        } 
        catch (IOException e) {
            //e.printStackTrace();
        }
    }
    
    
    public boolean existsID(String request){
	
		
	for(int i = 0; i < Server.ProcessIDList.size(); i++){
		
		System.out.println("Size of list is: " + Server.ProcessIDList.get(i));
	
		if(Server.ProcessIDList.get(i).equals(request)){
			return false;
		}	
	}
	return true;        
    }
    
    public synchronized String parse(String msg ){
    	String pattern = "[<>]";
   	 String[] tokens = input.split(pattern);
	 return tokens[1];
    }
    
    
}
