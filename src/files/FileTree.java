package files;

import java.util.List;

public class FileTree {
	private class treeNode{
		int nodeID;
		boolean isFile; //true if the node is a file, false if the node is a directory
		String name; //name of file or directory
		treeNode parent; //parent of this node
		List<treeNode> children; //children of current node
		public treeNode() {
			// TODO Auto-generated constructor stub
		}
	}
	
	treeNode root;
	treeNode currentNode;
}
