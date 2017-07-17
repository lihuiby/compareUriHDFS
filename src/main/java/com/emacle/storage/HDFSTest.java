package com.emacle.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HDFSTest {
	private static final Logger logger = LoggerFactory.getLogger(HDFSTest.class);
	
	public static void main(String[] args) {
		String path = "/a/b/404Error.txt/";
		try {
			//downloadFile(path);
			
		} catch (Exception e) {
			logger.error("@@@@@@ error downloadFile path="+path,e);
			e.printStackTrace();
		}
	}
	
	/*public static void downloadFile(String path) throws Exception {
		try {
			logger.info("start downloadFile path="+path);
			//获得文件信息
			FsMeta fsMeta = DB.getFsMetaByPath(path);
			logger.info("<<<>>>fsMeta = "+fsMeta);
			
			//TODO 创建文件的目录
			
			//获得hdfs中的文件流
			HDFSStorage hdfs = new HDFSStorage();
			InputStream in = hdfs.download(fsMeta.getUri());
			
			//获得输出流
			File targetFile = new File(Constant.TARGET_ROOT_PATH+fsMeta.getParentPath(),fsMeta.getFileName());
			OutputStream out = new FileOutputStream(targetFile);

			//迁移文件
			IOUtils.copy(in, out, Constant.BUFFER_SIZE);
			
			logger.info("======== copy file success =======");
			
		} catch (Exception e) {
			logger.error("文件迁移失败 ",e);
			e.printStackTrace();
		}
	}*/
	
}
