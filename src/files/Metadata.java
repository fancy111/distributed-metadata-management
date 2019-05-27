package files;

import java.util.Date;
import java.util.List;

public class Metadata {
	int nodeID;
	String filename;
	String owner;
	String permissions;
	Date lastAccessTime;
	Date createTime;
	List<Block> blocks;//location of the file blocks
	
}
