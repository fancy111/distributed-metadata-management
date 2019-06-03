package files;

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
	
	public void updateName(String name) {
		this.name = name;
	}
	
	public void updateLastAccessTime(String lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	
	public void updateLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
	//change the metadata into string
	public String toString() {
		String result = "File:'"+name+"'\n";
		result += "Size:" + size + "\n";
		result += "Access:" + permissions + "\n";
		result += "Last Access Time:" + lastAccessTime + "\n";
		result += "Last Modify Time:" + lastModifyTime + "\n";
		result += "create Time:" + createTime + "\n";
		return result;
	}
}
