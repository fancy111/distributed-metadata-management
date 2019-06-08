package connection;

public class mServer {
	public static void main(String[] args) {
		ServerConnection serverConnection = new ServerConnection(8888);
		serverConnection.acceptConnectionRequest();
		
		while(true) {
			String req = serverConnection.requestfromClient();
			System.out.println("client request: " + req);
			if("exit".equals(req)) {
				break;
			}
			String reply = "server reply to msg :" + req;
			serverConnection.replyToClient(reply);
		}
	}
}
