package files;

import utlis.Utils;

//lustre
public class Metadata {
	int ID;// id of the metadata, equal to file id and node id
	int parent;// id of the parent
	String name;// name of the file
	long size;// size of the file
	int type;// type of the file, 0 : directory, 1 : file
	String permissions;// e.g. rwxrwxr-x
	String lastAccessTime;// last access time of the file
	String lastModifyTime;// last modify time of the file
	String createTime;// the creation time of the file
	
	//constructor
	public Metadata(int Id, int parent, String name, long size, int type, String permissions,
			String lastAccesstime, String lastModifyTime, String createTime) {
		this.ID = Id;
		this.parent = parent;
		this.name = name;
		this.size = size;
		this.type = type;
		this.permissions = permissions;
		this.lastAccessTime = lastAccesstime;
		this.lastModifyTime = lastModifyTime;
		this.createTime = createTime;
	}
	
	public Metadata(int Id,int parent, String name, int type) {
		this.ID = Id;
		this.name = name;
		this.type = type;
		this.parent = parent;
		this.permissions = "-rw-rw-r--";
		this.size = 0;
		this.lastAccessTime = Utils.getCurrentTime();
		this.lastModifyTime = Utils.getCurrentTime();
		this.createTime = Utils.getCurrentTime();
	}
	
	public void updateName(String name) {
		this.name = name;
	}
	
	public void updateLastAccessTime() {
		this.lastAccessTime = Utils.getCurrentTime();
	}
	
	public void updateLastModifyTime() {
		this.lastModifyTime = Utils.getCurrentTime();
	}
	
	//change the metadata into string
	public String toString() {
		String filetype = "";
		if(type == 0) {
			filetype = "directory";
		}
		else {
			filetype = "file";
		}
		String result = "File:"+name+" @";
		result += "Size:" + size + " @";
		result += "Type:" + filetype + " @";
		result += "Access:" + permissions + " @";
		result += "LastAccessTime:" + lastAccessTime + " @";
		result += "LastModifyTime:" + lastModifyTime + " @";
		result += "CreateTime:" + createTime;
		return result;
	}
}
