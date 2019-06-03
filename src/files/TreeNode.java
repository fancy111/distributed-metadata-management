package files;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	private FileTree fTree;
	static int nodeCount;// global variable, as a counter
	private int nodeID;//every node have an unique ID, equal to file ID
	private boolean isFile; //true if the node is a file, false if the node is a directory
	private String name; //name of file or directory
	private TreeNode parent; //parent of this node
	private List<TreeNode> children; //children of current node
	
	//-----------constructors------------------------
	//1. root construct
	public TreeNode(FileTree fTree) {
		this.fTree = fTree;
		nodeCount = 0;
		nodeID = nodeCount;
		this.isFile = false;//root must be a directory
		this.parent = null; //root has no parent
		this.name = "~";
		this.children = new ArrayList<TreeNode>();
	}
	//2. other node construct
	public TreeNode(FileTree fTree, boolean isFile, String name, TreeNode parent) {
		nodeCount ++;
		this.nodeID = nodeCount;
		this.fTree = fTree;
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
	 * @return return children of this node
	 */
	public List<TreeNode> getChildren() {
		return this.children;
	}
	/**
	 * @return return parent of this node
	 */
	public TreeNode getParent() {
		return this.parent;
	}
	
	/**
	 * @return return name of this node
	 */
	public String getName() {
		return this.name;
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
				return this;
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
		TreeNode node = new TreeNode(this.fTree, isFile, name, this);
		this.children.add(node);
		return true;
	}
	
	/**
	 * delete the child of this node
	 * @param i: i-th child of the node
	 * @param feedback
	 */
	public boolean deleteChild(int i, StringBuilder feedback) {
		if(this.isFile) {
			feedback.append(this.name + "should never be a file");
			return false;
		}
		//TODO------system call to slaves to delete the metadata-------------
		
		this.children.remove(i);
		return true;
	}
	
	/**
	 * delete the child according to TreeNode
	 * @param child
	 * @param feedback
	 * @return
	 */
	public boolean deleteChild(TreeNode child, StringBuilder feedback) {
		if(this.isFile) {
			feedback.append(this.name + "should never be a file");
			return false;
		}
		for(int i = 0; i < this.children.size(); i++) {
			if(child.nodeID == this.children.get(i).nodeID) {// find the child according to its nodeID
				deleteChild(i, feedback);
				return true;
			}
		}
		feedback.append(this.name + " has no such child");
		return false;
	}
	
	/**
	 * get the metadata from the slaves
	 * @param feedback
	 * @return basic information about the metadata, null if not found
	 */
	public String getMetadata(StringBuilder feedback) {
		String metadataMsg = null;
		
		//TODO------system call to slaves to fetch the metadata of this node-------------
		
		return metadataMsg;
	}
}
