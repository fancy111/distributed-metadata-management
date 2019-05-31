package files;
import files.FileTree;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileTree fileTree = new FileTree();
		StringBuilder feedback = new StringBuilder();
		
		String path = "file1";
		if(!fileTree.createFile(path, feedback)) {
			System.out.println(feedback);
		}
		else {
			System.out.println("create " + path + " success!");
		}
		
		path = "path1";
		if(!fileTree.makeDirectory(path, feedback)) {
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
		
		path = "path1/path2";
		if(!fileTree.makeDirectory(path, feedback)) {
			System.out.println(feedback);
		}
		else {
			System.out.println("create " + path + " success!");
		}
		
		path = "path1/path2/file3";
		if(!fileTree.createFile(path, feedback)) {
			System.out.println(feedback);
		}
		else {
			System.out.println("create " + path + " success!");
		}		
		
		path = "path1/fuke";
		String result;
		if((result = fileTree.listDirectory(path, feedback)) == null) {
			System.out.println(feedback);
		}
		else {
			System.out.println(result);
		}	
		
		path = "path1/";
		if(!fileTree.changeDirectory("path1/", feedback)) {
			System.out.println(feedback);
		}
		System.out.println(fileTree.printWorkDirectory());
		
		if(!fileTree.changeDirectory("..", feedback)) {
			System.out.println(feedback);
		}
		System.out.println(fileTree.printWorkDirectory());
	}

}
