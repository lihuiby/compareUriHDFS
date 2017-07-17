package com.emacle.storage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HDFSFileHandler {

	private String bucketname;
	private String objectid;
	private String storeUploadId;

	private static final String patternStr = "hdfs://([^\\/]*):(.*):(.*)";
	private static Pattern pattern = Pattern.compile(patternStr);

	public HDFSFileHandler() {
	}

	public boolean parse(String uri) {
		Matcher matcher = pattern.matcher(uri);
		if (matcher.matches()) {
			bucketname = matcher.group(1);
			objectid = matcher.group(2);
			storeUploadId = matcher.group(3);
			return true;
		} else {
			return false;
		}
	}

	public String getBucketname() {
		return bucketname;
	}

	public String getObjectId() {
		return objectid;
	}

	public String getStoreUploadId() {
		return storeUploadId;
	}

//	public StorerType getStoreType() {
//		return StorerType.HDFSSTORER;
//	}

}
