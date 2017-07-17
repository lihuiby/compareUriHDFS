package com.emacle.pojo;

public class FsMeta {

	private int inode;
	private String username;
	private String path;
	private String md5;
	private String uri;
	private String bucket;
	private int reference;
	private String tstorer;
	public int getInode() {
		return inode;
	}
	public void setInode(int inode) {
		this.inode = inode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public int getReference() {
		return reference;
	}
	public void setReference(int reference) {
		this.reference = reference;
	}
	public String getTstorer() {
		return tstorer;
	}
	public void setTstorer(String tstorer) {
		this.tstorer = tstorer;
	}
	public FsMeta(int inode, String username, String path, String md5, String uri, String bucket, int reference,
			String tstorer) {
		super();
		this.inode = inode;
		this.username = username;
		this.path = path;
		this.md5 = md5;
		this.uri = uri.replaceFirst("oss:", "hdfs:");
		this.bucket = bucket;
		this.reference = reference;
		this.tstorer = tstorer;
	}
	public FsMeta() {
		super();
	}
	@Override
	public String toString() {
		return "FsMeta [inode=" + inode + ", username=" + username + ", path=" + path + ", md5=" + md5 + ", uri=" + uri
				+ ", bucket=" + bucket + ", reference=" + reference + ", tstorer=" + tstorer + "]";
	}
	public FsMeta(String md5, String uri, String bucket, int reference, String tstorer) {
		super();
		this.md5 = md5;
		this.uri = uri.replaceFirst("oss:", "hdfs:");
		this.bucket = bucket;
		this.reference = reference;
		this.tstorer = tstorer;
	}
	public FsMeta(int inode, String username, String path, String md5) {
		super();
		this.inode = inode;
		this.username = username;
		this.path = path;
		this.md5 = md5;
	}


}
