package files;

import java.util.ArrayList;
import java.util.List;

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
	private TreeNode moveToDir(List<String> dirs) {
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
			if(pNode.isFile() || (pNode = pNode.moveTo(dirs.get(i))) == null) {//if pnode is a file or pnode dont have such child, return false
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
		for(int i = 0; i < paths.length - 1; i++) {
			dir.add(paths[i]);
		}
		return dir;
	}
	
	/**
	 * create a new file according to the path
	 * corresponding to command "touch"
	 * @param path: path of the file, including its filename
	 * @param feedback: the feedback when creation of file failed
	 * @return creation is success or not
	 */
	public boolean createFile(String path, StringBuilder feedback) {
		String[] paths = path.split("/");
		

		//if the directory does not exists, return false;
		ArrayList<String> dir = pathToDir(paths);
		TreeNode tmpNode = null;
		if((tmpNode = moveToDir(dir)) == null || tmpNode.isFile()) {
			feedback.delete(0, feedback.length());
			feedback.append("can not touch '" + path + "': No such file or directory");
			return false;
		}

		String filename = paths[paths.length - 1];
		//if this file/dir already exists, update the system time of the file
		if(tmpNode.containsChild(filename) >= 0) {
			//feedback = "can not create file '" + path + "': File exists";
			//TO DO --------------update current time of the file/dir-------------------------
			return true;
		}
		
		//create a new file
		tmpNode.addChild(filename, true, feedback);
		
		return true;
	}
	
	public void makeDirectory() {
		
	}
	
	/**
	 * corresponding to command "ls"
	 */
	public void listDirctory() {
		
	}
	
	public void removeFile() {
		
	}
	
	public void state() {
		
	}
	
	public void printWorkDirectory() {
		
	}
	
	public void changeDirectory() {
		
	}
}
