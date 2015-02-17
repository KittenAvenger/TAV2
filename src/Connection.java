import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**

 */
public class Connection implements Runnable{

    Socket clientSocket = null;
    String str;

    public Connection(Socket clientSocket) {
        this.clientSocket = clientSocket;
        
    }

    public void run() {
        try {
        	
        	PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
		    
			str = in.readLine();
				
			if (existsID(str)==true){
				Parser parse= new Parser();
		    	String ID = parse.parseRequest(str);
				out.write("<Accepted connection from '"+ID+"'");
				out.close();
	            in.close();
	            System.out.println("Request processed");
			}
			else{
				System.out.println("Connection denied");
			}

           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public boolean existsID(String request){
		Parser parse= new Parser();
    	String ID = parse.parseRequest(request);
		
	    for(int i = 0; i < Server.ProcessIDList.size(); i++){
			if(Server.ProcessIDList.get(i).equals(ID)){
				return true;
			}
			
		}
	   return false;        
	}
}
