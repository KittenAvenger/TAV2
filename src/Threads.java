import java.io.IOException;


public class Threads {
	    private Thread thread = null;
	    private Server runnable = null;

	    
	    public void startServer() {
	        runnable = new Server();
	        thread = new Thread(runnable);
	        System.out.println("Starting thread: " + thread);
	        thread.start();
	    }
	    
	    public void stopServer() throws InterruptedException {
	        if (thread != null) {
	            runnable.terminate();
	            thread.join();
	            System.out.println("stopped");
	            try {
					runnable.serverSocket.close();
					System.out.println("server socket closed");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
}
