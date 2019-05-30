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

public class mClient {
	public static void main(String[] args) {
		try {
			//create client socket according to the server ip and port
			Socket socket = new Socket("127.0.0.1", 8888);
			
			//construct IO
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			//send a message to server
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
			bufferedWriter.write("Client fff: Request 1");
			bufferedWriter.flush();
			socket.shutdownOutput();
			
			//read the message send from server
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String serverMsg = null;
			while((serverMsg = bufferedReader.readLine()) != null) {
				System.out.println("Client: receive from server "+ serverMsg);
			}
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
