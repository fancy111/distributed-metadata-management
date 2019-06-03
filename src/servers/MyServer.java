package servers;

import java.util.Scanner;

import management.Management;

public class MyServer {
	private boolean isMaster;// if this server is the master server 
	private int slaveNum;//number of slave server
	private int slaveID;// if this is a slave server, Id of this slave server
	
	public boolean initializeMaster() {
		return false;
	}
	
	public boolean initializeSlave() {
		return false;
	}
	
	/**
	 * establish service for client
	 * @param m
	 */
	public void establishService(Management m) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String line;
		StringBuilder sb = new StringBuilder();
		while(true) {
			line = scanner.nextLine();
			String[] args = line.split(" ");
			m.runCommandLine(args, sb);
			System.out.println(sb);
//			line = "pwd";
//			args = line.split(" ");
//			m.runCommandLine(args, sb);
//			System.out.print(sb+"$");
		}
		
	}
	
	public boolean sendTo(int slaveId, String message, StringBuilder feedback) {
		return false;
	}
}
