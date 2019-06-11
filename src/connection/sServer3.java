package connection;

public class sServer3 {
	public static void main(String[] args) {
		int ports = 7779;
		MyServer sserver = new MyServer(ports);
		sserver.run();
	}
	
}
