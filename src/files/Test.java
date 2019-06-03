package files;
import java.io.File;
import java.util.Scanner;

import files.FileTree;
import management.MasterManagement;
import servers.MyServer;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyServer mServer = new MyServer();
		
		mServer.establishService(new MasterManagement(mServer));
		
	}

}
