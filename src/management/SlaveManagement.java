package management;

import java.util.HashMap;
import java.util.Map;

import files.Metadata;
import servers.MyServer;

public class SlaveManagement extends Management{
	public MyServer mServer;
	private Map<Integer, Metadata> metadataMap;
	
	public SlaveManagement(MyServer mServer) {
		this.mServer = mServer;
		metadataMap = new HashMap<Integer, Metadata>();
	}
	
	//execute the command
	public void runCommandLine(String[] args, StringBuilder feedback) {
		feedback.delete(0, feedback.length());
		
		if(args.length == 0) {
			feedback.append("Error: the command should never be empty!");
			return;
		}
		
		String command = args[0];
		
		if("request".equals(command)) {
			this.request(args, feedback);
		}
		else if ("remove".equals(command)) {
			this.remove(args, feedback);
		}
		else if ("update1".equals(command)) {//update1 represents update the last access time
			this.updateLastAccessTime(args, feedback);
		}
		else if ("create".equals(command)) {
			this.create(args, feedback);
		}
		else {
			feedback.append("Error: Command is not support.");
		}
	}
	
	//request for metadata with fileid
	private void request(String[] args, StringBuilder feedback) {
		if(args.length != 2) {
			feedback.append("Error: request need 2 argument");
			return;
		}
		
		int fileID = Integer.parseInt(args[1]);
		Metadata metadata = metadataMap.get(fileID);
		if(metadata == null) {
			feedback.append("Error: request for fileID:" + fileID + " failed");
			return;
		}
		feedback.append(metadata.toString());
	}
	
	//remove the metadata according to the fileID
	private void remove(String[] args, StringBuilder feedback) {
		if(args.length != 2) {
			feedback.append("Error: remove need 2 argument");
			return;
		}
		
		int fileID = Integer.parseInt(args[1]);
		Metadata metadata = metadataMap.get(fileID);
		if(metadata == null) {
			feedback.append("Error: request for fileID:" + fileID + " failed");
			return;
		}
		metadataMap.remove(fileID);
		feedback.append("remove fileID:" + fileID + " success");
	}
	
	//update the last access time
	private void updateLastAccessTime(String[] args, StringBuilder feedback) {
		if(args.length != 3) {
			feedback.append("Error: updateLastAccessTime need 3 argument");
			return;
		}
		int Id = Integer.parseInt(args[1]);
		String lastAccessTime = args[2];
		Metadata metadata = metadataMap.get(Id);
		if(metadata == null) {
			feedback.append("Error: fileId "+ Id +"does not exist");
			return;
		}
		
		metadata.updateLastAccessTime(lastAccessTime);
		metadataMap.put(Id, metadata);
		feedback.append("update last access time success");
	}
	
	//create a new metadata
	private void create(String[] args, StringBuilder feedback) {
		if(args.length != 10) {
			feedback.append("Error: create need 10 argument");
			return;
		}
		int Id = Integer.parseInt(args[1]);
		int parent = Integer.parseInt(args[2]);
		String name = args[3];
		long size = Long.parseLong(args[4]);
		int type = Integer.parseInt(args[5]);
		String permissions = args[6];
		String lastAccesstime = args[7];
		String lastModifyTime = args[8];
		String createTime = args[9];
		Metadata metadata = new Metadata(Id, parent, name, size, type, permissions, lastAccesstime, lastModifyTime, createTime);
		if(metadataMap.get(Id) != null) {
			feedback.append("Error: file already exists");
			return;
		}
		metadataMap.put(Id, metadata);
		feedback.append("create fileId: "+ Id +"success");
	}
}
