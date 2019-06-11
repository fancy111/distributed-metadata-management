package connection;

public class sServer2 {
	public static void main(String[] args) {
		int ports = 7778;
		MyServer sserver = new MyServer(ports);
		sserver.run();
	}
	
}
