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
		out.println(text + "\n");
		    
		
		 Parser parse= new Parser();
		 while ((str = in.readLine()) != null && !str.isEmpty())  {
		        System.out.println("From client: " + str);	
		        
		       
				
		    	String ID = parse.parseRequest(str);
		        if (existsID(ID)==true){
					
					out.write("<Accepted connection from '"+ID+"'");
					out.close();
			            	in.close();
			            	System.out.println("Request processed");
				}
				else{
					System.out.println("Connection denied");
				}
		    }   
		
		

           
        } 
        catch (IOException e) {
            //e.printStackTrace();
        }
    }
    
    
    public boolean existsID(String request){
	Parser parse= new Parser();
    	String ID = parse.parseRequest(request);
		
	for(int i = 0; i < Server.ProcessIDList.size(); i++){
		if(Server.ProcessIDList.get(i).equals(ID)){
			return false;
		}
			
	}
	return true;        
    }
}
