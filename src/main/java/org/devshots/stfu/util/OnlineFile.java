package org.devshots.stfu.util;

import java.util.Date;

public class OnlineFile {
	private String id;
	private Date created;
	private String filename;
	private String originalFileName;
	private String contentType;
	private String remoteAddr;
	private String hash;
	private long size;
	
	public long getTimeOnlineMillis(){
		Date now = new Date();
		return now.getTime() - created.getTime();
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		appendo(sb, this.id);
		appendo(sb, created.toString());
		appendo(sb, remoteAddr);
		appendo(sb, filename);
		appendo(sb, originalFileName);
		appendo(sb, contentType);
		appendo(sb, String.valueOf(size));
		appendo(sb, hash);
		
		return sb.toString();		
	}
	private void appendo(StringBuilder sb, String s){
		sb.append(s);
		sb.append(";");
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
}
