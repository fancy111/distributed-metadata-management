package connection;

import java.util.Scanner;

public class mClient {
	public static void main(String[] args) {
		ClientConnection clientConnection = new ClientConnection("127.0.0.1", 8888);
		
		Scanner scanner = new Scanner(System.in);
		String request;
		while(true) {
			System.out.print("$ ");
			//send request to server
			request = scanner.nextLine();
			
			String[] commands = request.split(" ");
			if(commands.length == 0) {
				continue;
			}
			//user want to exit
			if("exit".equals(commands[0]) || "quit".equals(commands[0])) {
				clientConnection.requestToServer("exit");
				break;
			}
			
			clientConnection.requestToServer(request);
			String reply = clientConnection.replyFromServer();
			System.out.println(reply);
		}
	}
}
