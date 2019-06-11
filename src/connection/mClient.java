package connection;

import java.util.Scanner;

public class mClient {
	public static void main(String[] args) {
		ClientConnection clientConnection = new ClientConnection("127.0.0.1", 8888);
		
		Scanner scanner = new Scanner(System.in);
		String request;
		String reply;
		while(true) {
			request = "pwd";
			clientConnection.requestToServer(request);
			reply = clientConnection.replyFromServer();
			System.out.print(reply + "$ ");
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
			reply = clientConnection.replyFromServer();
			if(reply == null || reply.equals("")) {
				continue;
			}
			if("stat".equals(commands[0])) {
				String[] tmp = reply.split("@");
				reply = "";
				for(int i = 0; i < tmp.length - 1; i++) {
					reply += tmp[i];
					reply += "\n";
				}
				reply += tmp[tmp.length - 1];
			}
			System.out.println(reply);
		}
	}
}
