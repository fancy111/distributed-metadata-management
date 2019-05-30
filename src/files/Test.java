package files;
import files.FileTree;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileTree fileTree = new FileTree();
		fileTree.root.addChild("path1", false, new StringBuilder());
		StringBuilder feedback = new StringBuilder();
		String path = "file1";
		if(!fileTree.createFile(path, feedback)) {
			System.out.println(feedback);
		}
		else {
			System.out.println("create " + path + " success!");
		}
		
		path = "path1/file1";
		if(!fileTree.createFile(path, feedback)) {
			System.out.println(feedback);
		}
		else {
			System.out.println("create " + path + " success!");
		}
		
		path = "path2/file2";
		if(!fileTree.createFile(path, feedback)) {
			System.out.println(feedback);
		}
		else {
			System.out.println("create " + path + " success!");
		}
		
		path = "file1/file2";
		if(!fileTree.createFile(path, feedback)) {
			System.out.println(feedback);
		}
		else {
			System.out.println("create " + path + " success!");
		}		
		
		
	}

}
