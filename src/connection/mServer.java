package connection;

public class mServer {
	public static void main(String[] args) {
		MyServer mServer = new MyServer(8888, 3);
		mServer.run();
	}
}
