package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	Socket socket = null;
	public ServerThread(Socket s) {
		this.socket = s;
	}
	
	@Override
	public void run() {
		
		String message;
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//read the message sent from the client
			while((message = bufferedReader.readLine()) != null) {
				System.out.println("Server: Client send msg is :" + message);
				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				bufferedWriter.write("server reply to " + message + "\n");
				bufferedWriter.flush();
			}
			
			socket.shutdownInput();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(socket != null) {
					socket.close();
				}
			}
			 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
