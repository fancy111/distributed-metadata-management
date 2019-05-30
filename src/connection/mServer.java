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
			
			//listen and wait for the connect of client
			while(true) {
				Socket socket = serverSocket.accept();
				//create a new thread to handle the client request
				ServerThread serverThread = new ServerThread(socket);
				serverThread.start();
				System.out.println("Server: Clinet IP " + socket.getInetAddress().getLocalHost() + " connect successfully to server." );
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
