import java.io.IOException;


public class Threads {
	    private Thread thread = null;
	    private Server runnableServer = null;
	    private Client runnableClient = null;

	    
	    public void startServer() {
	        runnableServer = new Server();
	        thread = new Thread(runnableServer);
	        System.out.println("Starting Server thread: " + thread);
	        thread.start();
	    }
	    
	    public void stopServer() throws InterruptedException {
	        if (thread != null) {
	            runnableServer.terminate();
	            thread.join();
	            System.out.println("stopped server thread " + thread);
	            try {
					runnableServer.serverSocket.close();
					System.out.println("server socket closed");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	    
	    /*public void startClient() {
	        runnableClient = new Client();
	        thread = new Thread(runnableClient);
	        System.out.println("Starting Client thread: " + thread);
	        thread.start();
	    }
	    
	    public void stopClient() throws InterruptedException {
	        if (thread != null) {
	            runnableClient.terminate();
	            thread.join();
	            System.out.println("stopped client thread " + thread );
	            try {
					runnableClient.clientSocket.close();
					System.out.println("client socket closed");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }*/
}
