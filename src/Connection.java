import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable{

    Socket clientSocket = null;
    String str;
    String text = "<Accepted connection from  1234 +/>";

    public Connection(Socket clientSocket) {
        this.clientSocket = clientSocket;
        
    }

    public void run() {
        try {
        	
        	PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
		
		    
		
		 
		 while ((str = in.readLine()) != null && !str.isEmpty())  {
		        System.out.println("From client: " + str);	
		        
		       
		        //out.println(text + "\n");
		    	
		    	Parser parse= new Parser();
		    	String ID = parse.parseRequest(str);
		        if (existsID(ID)==false){
		        	out.println("Connection denied");
		        	out.flush();
		        	System.out.println("connection refused");
		        	
		        	
					
				}
				else if (existsID(ID)==true){
					out.println("<Accepted connection from '"+ID+"' +/>");
					//out.close();
			        //.close();
			        Server.ProcessIDList.add(ID);
			         System.out.println("Request processed");
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
}
