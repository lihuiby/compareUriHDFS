package com.emacle.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emacle.constant.Constant;

public class HDFSStorage {

	private static final Logger log = LoggerFactory.getLogger(HDFSStorage.class);
	
	private static FileSystem fs;

	public HDFSStorage(){
		try {
			this.fs = FileSystem.get(new URI(Constant.HDFSPOINT), new Configuration());
			log.info("hdfsStorage init success");
		} catch (Exception e) {
			log.error("hdfsStorage init failed, hdfs="+Constant.HDFSPOINT, e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 文件是否存在
	 * @param uri
	 * @return
	 * @throws IOException
	 */
	public boolean isExist(String uri) throws IOException {
		String objectId = parseUri(uri).getObjectId();
		
		String buckName = parseUri(uri).getBucketname();
		boolean exist = fs.exists(new Path("/"+buckName + "/" + objectId));
		if(!exist) {
			log.info("objectId="+objectId + "|exist="+exist);
		}
		return exist;
	}
	
	/**
	 * 处理alimeta uri, 获得objectId
	 */
	private HDFSFileHandler parseUri(String uri) {
		HDFSFileHandler handler = new HDFSFileHandler();
		handler.parse(uri);
		return handler;
	}
	
	

}
