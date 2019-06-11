package connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import management.Management;
import management.MasterManagement;
import management.SlaveManagement;

public class MyServer {
	private ServerConnection serverConnection;
	private String masterIP = "127.0.0.1";
	private int masterPort = 8888;
	private List<Integer> slavePorts;//only used for master server
	private List<ClientConnection> clientConnections; //only used for master server
	private int port; // the port number of the server
	private boolean isMaster;// if this server is the master server 
	private int slaveNum;//number of slave server
	private int slaveID;// if this is a slave server, Id of this slave server
	
	//constructor for master server
	public MyServer(int port, int slaveNum) {
		this.port = port; 
		this.isMaster = true;
		this.slaveNum = slaveNum;
		slavePorts = new ArrayList<Integer>();
		clientConnections = new ArrayList<ClientConnection>();
		initializeMaster();
	}
	
	//consructor for slave server
	public MyServer(int port) {
		this.port = port;
		this.isMaster = false;
		initializeSlave();
	}
	
	//initialize the master server
	public void initializeMaster() {
		serverConnection = new ServerConnection(this.port);
		for(int i = 0; i < slaveNum; i++) {//accept the connect of slaves to get their port
			serverConnection.acceptConnectionRequest();
			String line = serverConnection.requestfromClient();
			String[] args = line.split(" ");
			if(!args[0].equals("port")) {//the request is not valid
				System.out.println("initialize master: connect to slave "+i+" failed");
				serverConnection.replyToClient("fail");
				continue;
			}
			slavePorts.add(Integer.parseInt(args[1]));
			serverConnection.replyToClient("ok " + i);
		}
		
		for(int i = 0; i < this.slaveNum; i++) {
			ClientConnection clientConnection = new ClientConnection(masterIP, slavePorts.get(i));
			this.clientConnections.add(clientConnection);
		}
	}
	
	//initialize the slave server
	public void initializeSlave() {
		ClientConnection clientConnection = new ClientConnection(masterIP, masterPort);
		clientConnection.requestToServer("port " + port);
		String reply = clientConnection.replyFromServer();
		String[] args = reply.split(" ");
		if(!args[0].equals("ok")) {
			System.out.println("initialize slave: failed");
			return;
		}
		this.slaveID = Integer.parseInt(args[1]);
		this.serverConnection = new ServerConnection(this.port);
	}
	
	//start the service of this server
	public void run() {
		if(this.isMaster) {
			Management management = new MasterManagement(this);
			this.establishService(management);
		}
		else {
			Management management = new SlaveManagement(this);
			this.establishService(management);
		}
	}
	
	/**
	 * establish service for client
	 * @param m
	 */
	public void establishService(Management m) {
		this.serverConnection.acceptConnectionRequest();//ready to accept the client request
		StringBuilder sb = new StringBuilder();
		String line;
		while(true) {
			line = serverConnection.requestfromClient();
			System.out.println("client request: " + line);
			
			if("exit".equals(line)) {// if user want to exit
				break;
			}
			String[] args = line.split(" ");
			m.runCommandLine(args, sb);
			if(this.isMaster) {
				System.out.println("server reply:" + sb);
			}
			else {
				System.out.println(this.slaveID + "-th slave server reply:" + sb);
			}
			serverConnection.replyToClient(sb.toString());
		}
	}
	
	/**
	 * master server send message to the slave server
	 * @param slaveId: the id of the slave server
	 * @param message: message to be send
	 * @param feedback: feedback of the slave server
	 * @return
	 */
	public void sendTo(int slaveId, String message, StringBuilder feedback) {
		feedback.delete(0, feedback.length());
		
		ClientConnection clientConnection = this.clientConnections.get(slaveId);
		clientConnection.requestToServer(message);
		feedback.append(clientConnection.replyFromServer());
	}
	
	public int hashSlave(int fileID) {
		return fileID % this.slaveNum;
	}
}
