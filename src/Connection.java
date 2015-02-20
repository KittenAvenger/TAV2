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

    public Connection (Socket clientSocket) {
        this.clientSocket = clientSocket;
        
    }

    public void run() {
        try {
        	
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
		 
		 while ((input = in.readLine()) != null && !input.isEmpty())  {
		        System.out.println("From client: " + input);	
		        
		        
		    	
		    	Parser parse= new Parser();
		    	String ID = parse.parseRequest(input);
		        if (existsID(ID)==false){
		        	out.println("Connection denied");
		        	out.flush();
		        	
		        

				}
				else if (existsID(ID)==true){
					out.println("<Accepted connection from '"+ID+"' +/>");
					
					sender = ID;
					addID(ID);
			        
			        
			         
			         while(true){			        	
			        	 
			        	 while ((input = in.readLine()) != null && !input.isEmpty())  {
			 		        System.out.println("From client: " + input);
			 		        break;
			        	 }
			        	 
			        	 if(input.length() == 0){
			        		 
			        	 }
			        	 else{
			        	test = parse(input);
		        	 

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
			            			Server.ProcessIDList.remove(result-1);
			            			break;
			            		}
			            		
			            		
			            		
			            		
			            	
					case "DelMessage":
			         
			            		String msgID = "";
			            		msgID = parser.parseAddID(input);
			            		
			            		this.result = karn.delete(Integer.parseInt(msgID));
			  
			            		if(result == -1){
			            			out.println("<ErrorMsg> Reason </ErrorMsg>");
			            			break;
			            		}
			            		else{
			            			out.println("<Message deleted: '" + result + "' />");
			            			Server.ProcessIDList.remove(result-1);
			            			break;
			            		}
//			            		
//			            	case "<RplMessage>":
//			            		
//			            		break;

			            	case "FetchMessages":
			            		String output = "";
			            		output = karn.fetch(ID);
			            		if(output.equals("Message doesn't exist")){
			            			out.println("<ErrorMsg> + output + </ErrorMsg>");
			            		}
			            		else{
			            			out.println("<FetchedMessages>\n"+ output + "\n<FetchedMessages>");
			            		}
			            		break;
			            		
			            	case "FetchComplete":
			            		if(karn.fetch_complete(ID)==1){
			            			out.println("<FetchedCompleteAck/>");
			            		}
			            		else{
			            			out.println("<ErrorMsg> No message to delete </ErrorMsg>");
			            		}
			            		break;
			            		
			      
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
    
    
    private synchronized boolean existsID(String request){
	
		
	for(int i = 0; i < Server.ProcessIDList.size(); i++){
		
		System.out.println("Size of list is: " + Server.ProcessIDList.get(i));
	
		if(Server.ProcessIDList.get(i).equals(request)){
			return false;
		}	
	}
	return true;        
    }
    
    private synchronized String parse(String msg ){
    	String pattern = "[<>]";
   	 String[] tokens = input.split(pattern);
	 return tokens[1];
    }
    
    private synchronized void addID (String ID){
    	Server.ProcessIDList.add(ID);
    }
    
    
}
