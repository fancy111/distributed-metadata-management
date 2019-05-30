package files;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;

public class TreeNode {
	private FileTree fTree;
	static int nodeID;//every node have an unique ID, equal to file ID
	private boolean isFile; //true if the node is a file, false if the node is a directory
	private String name; //name of file or directory
	private TreeNode parent; //parent of this node
	private List<TreeNode> children; //children of current node
	
	//-----------constructors------------------------
	//1. root construct
	public TreeNode(FileTree fTree) {
		this.fTree = fTree;
		nodeID = 0;
		this.isFile = false;//root must be a directory
		this.parent = null; //root has no parent
		this.name = "~";
		this.children = new ArrayList<TreeNode>();
	}
	//2. other node construct
	public TreeNode(boolean isFile, String name, TreeNode parent) {
		nodeID++;
		this.isFile = isFile;
		this.name = name;
		this.parent = parent;
		if(isFile) {//file do not have child
			children = null;
		}
		else {
			children = new ArrayList<TreeNode>();
		}
		
		//!!!TO DO----------------- system call to slave servers to update the metadata-------------
	}
	
	/**
	 * @return this node is a file or directory
	 */
	public boolean isFile() {
		return this.isFile;
	}
	
	/**
	 * if this node have a child named "name"
	 * @param name: the name of the child
	 * @return index of the child, -1 if do not contain
	 */
	public int containsChild(String name) {
		for(int i = 0; i < this.children.size(); i++) {
			if(name.equals(this.children.get(i).name)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * move the node to destination
	 * @param name: name of the path
	 * @param dst: one child of the node
	 * @return if move success, return the new move node. return null if move failed
	 */
	public TreeNode moveTo(String name) {
		//"." means stay in current directory
		if (".".equals(name)) {
			return this;
		}
		//".." means move to current node's parent
		if("..".equals(name)) {
			if(this == this.fTree.root) {//root do not have parent
				return null;
			}
			return this.parent;
		}
		// move to current node's children according to name
		int i  = this.containsChild(name);
		if(i >= 0) {
			return this.children.get(i);
		}
		return null;
	}
	
	/**
	 * add a child to this node
	 * @param name: name of the new node
	 * @param isFile: new node is a file or directory
	 * @param feedback: feedback of the failure
	 * @return add operation success or not
	 */
	public boolean addChild(String name, boolean isFile, StringBuilder feedback) {
		if(this.isFile()) {//file should not have children
			feedback.delete(0, feedback.length());
			feedback.append("error: " + this.name + "should never be a file");
			return false;
		}
		TreeNode node = new TreeNode(isFile, name, this);
		this.children.add(node);
		return true;
	}
	
	public void deleteChild() {
		
	}
}
