package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {
	int port;
	ServerSocket serverSocket;
	Socket socket;
	String clientIP;
	BufferedReader br;
	BufferedWriter bw;
	
	public ServerConnection(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("start server ...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//server accept the connection requst from the client
	public void acceptConnectionRequest() {
		try {
			//listen and wait for the connect of client
			this.socket = serverSocket.accept();
			if(socket == null) {
				System.out.println("connect failed, please try again");
				return;
			}
			this.clientIP = this.socket.getInetAddress().getLocalHost().toString();
			System.out.println("client:"+ this.clientIP+" has connected to server");
			
			//initialize the input and output reader
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//server accept the request from client
	public String requestfromClient() {
		String request = null;
		try {
			//read the message sent from the client
			request = br.readLine();
//			System.out.println("Server: accept client request  " + request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return request;
	}
	
	//server reply message to the client
	public void replyToClient(String message) {
		try {
			bw.write(message);
			bw.write("\n");
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
