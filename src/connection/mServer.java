package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.sun.xml.internal.ws.wsdl.writer.document.Port;

import jdk.internal.util.xml.impl.Input;

public class mServer {
	static int port;
	boolean isMaster;// is a master or slave server
	
	public mServer() {
		// TODO Auto-generated constructor stub
	}
	
	public void startServer() {
		
	}
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(8888);
			System.out.println("Start the server...");
			Socket socket = serverSocket.accept();
			System.out.println("Client:" + socket.getInetAddress().getLocalHost() + " connect successfully to server" );
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//read the message send from the client
			String message = bufferedReader.readLine();
			System.out.println("Client message:" + message);
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bufferedWriter.write(message + "\n");
			bufferedWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
