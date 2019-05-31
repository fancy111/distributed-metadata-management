package files;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

public class FileTree {
	TreeNode root;//root of the tree(namespace)
	TreeNode currentNode;//current workspace
	
	//constructor
	public FileTree() {
		this.root = new TreeNode(this);// create a root node
		currentNode = root;
	}
	
	/**
	 * move the currentNode to node according to dirs
	 * @param dirs: directory list
	 * @return return the destination of the current node, return null if move failed
	 */
	private TreeNode moveToDir(List<String> dirs, StringBuilder feedback) {
		if(dirs.size() == 0) {
			return this.currentNode;
		}
		TreeNode pNode = this.currentNode;//current node
		int i = 0;
		if("~".equals(dirs.get(0))) {
			pNode = this.root;
			i = 1;
		}
		for(; i < dirs.size(); i++) {
			if(pNode.isFile()) {//if pnode is a file, return false
				feedback.append("Not a directory");
				return null;
			}
			if((pNode = pNode.moveTo(dirs.get(i))) == null) {//if pnode do not have such child, return false
				feedback.append("No such file or directory");
				return null;
			}
		}
		return pNode;
	}
	
	/**
	 * transfer the path to directory, without the last filename
	 * @param paths string list of path names
	 * @return directory list
	 */
	private ArrayList<String> pathToDir(String[] paths) {
		ArrayList<String> dir = new ArrayList<>();
		for(int i = 0; i < paths.length; i++) {
			dir.add(paths[i]);
		}
		return dir;
	}
	
	/**
	 * delete all of the files under directory node
	 * @param node
	 * @return delete success or not
	 */
	private boolean deleteDir(TreeNode node, StringBuilder feedback) {
		if(node.isFile()) {
			return true;
		}
		boolean result = true;
		for(int i = 0; i < node.getChildren().size(); i++) {
			result = deleteDir(node.getChildren().get(i), feedback);
			node.deleteChild(i, feedback);
		}
		return result;
	}
	
	/**
	 * create a new file according to the path
	 * corresponding to command "touch file"
	 * @param path: path of the file, including its filename
	 * @param feedback: the feedback when creation of file failed
	 * @return creation is success or not
	 */
	public boolean createFile(String path, StringBuilder feedback) {
		feedback.delete(0, feedback.length());
		String[] paths = path.split("/");
		

		//if the directory does not exists, return false;
		ArrayList<String> dir = pathToDir(paths);
		dir.remove(dir.size()-1);
		TreeNode tmpNode = null;
		feedback.append("can not touch '" + path + "': ");
		if((tmpNode = moveToDir(dir, feedback)) == null) {	
			return false;
		}
		if(tmpNode.isFile()) {
			feedback.append("Not a directory");
			return false;
		}

		String filename = paths[paths.length - 1];
		//if this file/dir already exists, update the system time of the file
		if(tmpNode.containsChild(filename) >= 0) {
			//TO DO --------------update current time of the file/dir-------------------------
			return true;
		}
		
		//create a new file
		tmpNode.addChild(filename, true, feedback);
		
		return true;
	}
	
	/**
	 * create a directory
	 * corresponding to command "mkdir dir"
	 * @param path: name of the directory
	 * @param feedback: feedback if creation failed
	 * @return if success or not
	 */
	public boolean makeDirectory(String path, StringBuilder feedback) {
		feedback.delete(0, feedback.length());
		String[] paths = path.split("/");

		//if the directory does not exists, return false;
		ArrayList<String> dir = pathToDir(paths);
		dir.remove(dir.size() - 1);
		TreeNode tmpNode = null;
		feedback.append("can not create directory '" + path + "': ");
		if((tmpNode = moveToDir(dir, feedback)) == null) {
			return false;
		}
		
		if(tmpNode.isFile()) {
			feedback.append("Not a directory");
			return false;
		}

		String dirname = paths[paths.length - 1];
		//if this file/dir already exists, update the system time of the file
		if(tmpNode.containsChild(dirname) >= 0) {
			feedback.append("File exists");
			return false;
		}
		
		//create a new directory
		tmpNode.addChild(dirname, false, feedback);
		
		return true;
	}
	
	/**
	 * list the files in the path
	 * corresponding to command "ls path"
	 * @param path: list path
	 * @param feedback: feedback if path is not found
	 * @return files under path, return null if failed
	 */
	public String listDirectory(String path, StringBuilder feedback) {
		feedback.delete(0, feedback.length());//delete the origin content of the feedback
		String result = "";
		TreeNode tmpNode = null;
		
		//user do not specify which path, default current node
		if(path==null || path.length() == 0) {
			tmpNode = this.currentNode;
		}
		else {//user specify the path and go to the path
			String[] paths = path.split("/");
			List<String> dirs = pathToDir(paths);
			//if the path does not exist
			feedback.append("can not access '" + path + "': ");
			if((tmpNode = moveToDir(dirs, feedback)) == null) {
				return null;
			}
		}
		
		if(tmpNode.isFile()) {// if the node is a file, list this file name
			result+=path;
		}
		else {//if the node is a directory, list all its children
			List<TreeNode> children = tmpNode.getChildren();
			for(int i = 0; i < children.size(); i++) {
				result+=(children.get(i).getName());
				result += "  ";
			}
		}
		
		return result;
	}
	
	/**
	 * remove the file or directory
	 * @param path: path of the file/dir
	 * @param recursive: recursive delete the directory
	 * @param feedback: feedback if failed
	 * @return true or false
	 */
	public boolean removeFile(String path, Boolean recursive, StringBuilder feedback) {
		feedback.delete(0, feedback.length());
		
		String[] paths = path.split("/");
		List<String> dirs = pathToDir(paths);
		
		TreeNode tmpNode = null;
		feedback.append("can not remove '" + path + "': ");
		// if the path is not valid, return false
		if((tmpNode = moveToDir(dirs, feedback)) == null) {
			return false;
		}
		// if the node is a directory and not recursive, can not delete
		if(!tmpNode.isFile() && !recursive) {
			feedback.append("Is a directory");
			return false;
		}
		
		//delete the file
		boolean result = false;
		if(!tmpNode.isFile()) {
			result = this.deleteDir(tmpNode, feedback);	
		}
		return tmpNode.getParent().deleteChild(tmpNode, feedback) && result;
	}
	
	/**
	 * print the status of the file/dir
	 * @param path
	 * @param feedback
	 * @return the basic information of the file/dir, null if find failed
	 */
	public String state(String path, StringBuilder feedback) {
		feedback.delete(0, feedback.length());
		
		String[] paths = path.split("/");
		List<String> dirs = this.pathToDir(paths);
		TreeNode tmpNode = null;
		feedback.append("can not stat '" + path + "': ");
		if((tmpNode = moveToDir(dirs, feedback)) == null) {// if path is not valid
			return null;
		}
		
		//get the metadata from the node
		String msg = tmpNode.getMetadata(feedback);
		
		return msg;
	}
	
	/**
	 * print current work path
	 * corresponding to command "pwd"
	 * @return
	 */
	public String printWorkDirectory() {
		String result = "";
		TreeNode node = this.currentNode;
		while(true) {
			result = node.getName() + result;
			node = node.getParent();
			if(node == null) {
				break;
			}
			result = "/" + result;
		}
		return result;
	}
	
	/**
	 * change work space, i.e. currentNode
	 * corresponding to cammand "cd path"
	 * @param path
	 * @param feedback
	 * @return true or false
	 */
	public boolean changeDirectory(String path, StringBuilder feedback) {
		feedback.delete(0, feedback.length());
		feedback.append("'" + path + "' : ");
		
		String[] paths = path.split("/");
		List<String> dirs = pathToDir(paths);
		TreeNode tmpNode = null;
		if((tmpNode = moveToDir(dirs, feedback)) == null) {
			return false;
		}
		if(tmpNode.isFile()) {//work space must be a directory
			feedback.append("Not a directory");
			return false;
		}
		
		this.currentNode = tmpNode;
		return true;
	}
}
