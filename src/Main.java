


public class Main {

	public static void main(String[] args) {
		
		Server server = new Server();
		new Thread(server).start();
		Client client = new Client();
		
		try {
			client.connect();
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		server.stop();
		System.out.println("Stopping Server");

	}

}
