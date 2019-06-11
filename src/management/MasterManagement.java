package management;

import connection.MyServer;
import files.FileTree;

public class MasterManagement extends Management {
	public MyServer mServer;//belong to master server
	private FileTree fileTree;//file tree to handle the namespace
	
	//constructor
	public MasterManagement(MyServer mServer) {
		this.mServer = mServer;
		fileTree = new FileTree(this);
	}
	
	//execute the command
	public void runCommandLine(String[] args, StringBuilder feedback) {
		feedback.delete(0, feedback.length());
		
		if(args.length == 0) {
			feedback.append("Error: the command should never be empty!");
			return;
		}
		
		String command = args[0];
		if("touch".equals(command)) {
			this.touch(args, feedback);
		}
		else if ("mkdir".equals(command)) {
			this.mkdir(args, feedback);
		}
		else if ("ls".equals(command)) {
			this.ls(args, feedback);
		}
		else if ("rm".equals(command)) {
			this.rm(args, feedback);
		}
		else if ("stat".equals(command)) {
			this.stat(args, feedback);
		}
		else if ("pwd".equals(command)) {
			this.pwd(args, feedback);
		}
		else if ("cd".equals(command)) {
			this.cd(args, feedback);
		}
		else {
			feedback.append("Error: Command is not support.");
		}
	}
	
	//create a new file, have at least one argument
	private void touch(String[] args, StringBuilder feedback) {
		feedback.append("touch:");
		if(args.length == 1) {
			feedback.append("missing file operand");
			return;
		}
		//if create file success, feedback have nothing
		if(fileTree.createFile(args[1], feedback)) {
			feedback.delete(0, feedback.length());
		}
	}
	
	//create a new directory, have at least one argument
	private void mkdir(String[] args, StringBuilder feedback) {
		feedback.append("mkdir:");
		if(args.length == 1) {
			feedback.append("missing operand");
			return;
		}
		//if create directory success, feedback have nothing
		if(fileTree.makeDirectory(args[1], feedback)) {
			feedback.delete(0, feedback.length());
		}
	}
	
	//list the directories
	private void ls(String[] args, StringBuilder feedback) {
		//user does not specify which path, default current workspace
		if(args.length == 1) {
			String path = ".";
			String result = fileTree.listDirectory(path, feedback);
			feedback.delete(0, feedback.length());
			feedback.append(result);
			return;
		}
		
		//user specify paths
		feedback.append("ls: ");
		String result;
		//if find the path is valid, add the result
		if((result = fileTree.listDirectory(args[1], feedback)) != null) {
			feedback.delete(0, feedback.length());
			feedback.append(result);
		}
	}
	
	//remove the file
	private void rm(String[] args, StringBuilder feedback) {
		feedback.append("rm:");
		if(args.length == 1) {
			feedback.append("missing operand");
			return;
		}
		
		boolean recursive = false;
		int index = 1;
		if(args.length > 2 && "-r".equals(args[1])) {
			recursive = true;
			index = 2;
		}
		//if remove file successfully, feedback have nothing
		if(fileTree.removeFile(args[index], recursive, feedback)) {
			feedback.delete(0, feedback.length());
		}
	}
	
	//get the state of the file
	private void stat(String[] args, StringBuilder feedback) {
		feedback.append("stat: ");
		if(args.length == 1) {
			feedback.append("missing operand");
			return;
		}
		//if find the file successfully, put the result into feedback
		String result;
		if((result = fileTree.state(args[1], feedback)) != null) {
			feedback.delete(0, feedback.length());//remove the stat: 
			feedback.append(result);
		}
	}
	
	//print the current work space
	private void pwd(String[] args, StringBuilder feedback) {
		feedback.append(fileTree.printWorkDirectory());
	}
	
	//change the work space
	private void cd(String[] args, StringBuilder feedback) {
		feedback.append("cd:");
		String path;
		if(args.length == 1) {
			path = "~";
		}
		else {
			path = args[1];
		}
		//if change directory successfully, feedback have nothing
		if(fileTree.changeDirectory(path, feedback)) {
			feedback.delete(0, feedback.length());
		}
	}
}
