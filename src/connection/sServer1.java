package connection;

public class sServer1 {
	public static void main(String[] args) {
		int ports = 7777;
		MyServer sserver = new MyServer(ports);
		sserver.run();
	}
	
}
