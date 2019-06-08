package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientConnection {
	String serverIp;
	int serverPort;
	Socket socket;
	BufferedReader br;
	BufferedWriter bw;
	
	public ClientConnection(String serverIp, int serverPort) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		try {
			this.socket = new Socket(serverIp, serverPort);
			if(socket == null) {
				System.out.println("client connect failed, please try again");
				return;
			}
			//initialize the socket reader and writer
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//client send request to server
	public void requestToServer(String request) {
		try {
			//send a message to server
			bw.write(request);
			bw.write("\n");
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//client receive reply from server
	public String replyFromServer() {
		String serverMsg = null;
		try {
			//read the message send from server
			serverMsg = br.readLine();
//			System.out.println("Client: reply from server "+ serverMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serverMsg;
	}
	
	
}
