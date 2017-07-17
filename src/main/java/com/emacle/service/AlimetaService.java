package com.emacle.service;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emacle.constant.Constant;
import com.emacle.db.DB;
import com.emacle.pojo.FsMeta;
import com.emacle.storage.HDFSStorage;

public class AlimetaService extends Thread {
	private static Logger log = LoggerFactory.getLogger(AlimetaService.class);
	
	private int start = -1;
	
	private HDFSStorage hdfs;
	private FileWriter fw;
	private Integer total_num = 0;
	private Integer total_error_num = 0;
	private Integer local_num = 0;
	
	public AlimetaService(int start) {
		this.start = start;
		this.hdfs = new HDFSStorage();
	}

	public void init() {
		log.info("service running  , fisrt start=" + start);
		if(start < 0) {
			start = 0;
		}
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			List<FsMeta> metas = null;
			try {
				metas = DB.getFsMetaByStart(start);
			} catch (Exception e1) {
				log.error("getFsMeta error " + start, e1);
			}
			
			if (null == metas) {
				try {
					log.error("metas is null");
				} catch (Exception e) {
					log.error("metas is null",e);
				}
				return;
			}
			if (metas.size() == 0) {
				log.info("metas.size() == 0 all data done success !!!");
				return;
			}
			log.info("service running  , process to n=" + start);
			start += 100;
			for (FsMeta fsMeta : metas) {
				doWork(fsMeta);
			}
			log.info("<<<>>> total_num="+total_num
					+" | total_error_num="+total_error_num 
					+" | local_num="+local_num);
		}
	}
	
	private void doWork(FsMeta fsMeta) {
		// 指验证 oss 的数据, bucket=tongbupan_store, tstorer=null
		try {
			// 判断数据库中是否存在 MD5 + gfs 的记录
			int count = DB.getAliMetaByMd5(fsMeta.getMd5());
			if (count > 0) {
				total_num ++;
				//判断fsmeta表中是否有数据
				int countLocal = DB.getAliLocalByMd5(fsMeta.getMd5());
				if(countLocal>0) {
					local_num ++;
					return;
				}
				//验证存储中是否存在
				boolean exist = hdfs.isExist(fsMeta.getUri());
				if(!exist) {
					if(Constant.COMPANYID.equals(fsMeta.getUsername())) {
						fsMeta.setPath(Constant.COMPANY_PATH + fsMeta.getPath());
					}
					pathInFile(fsMeta.getUsername(), fsMeta.getPath());
					log.info("!!!!! hdfs not exist. md5="+fsMeta.getMd5()+"|path="+fsMeta.getPath());
					total_error_num ++;		
				}
				return;
			}
			log.info("db not exist md5");
		} catch (Exception e) {
			log.error("get file error:",e);
		}
	}
	
	private void doWorkAli(FsMeta fsMeta) {
		// 指验证 oss 的数据, bucket=tongbupan_store, tstorer=null
		try {
			// 判断fsmeta表中是否有数据
			int count = DB.getFsByMd5(fsMeta.getMd5());
			if (count > 0) {
				total_num ++;
				//判断是否有local存储
				int countLocal = DB.getAliLocalByMd5(fsMeta.getMd5());
				if(countLocal>0) {
					log.info("AAA in localfs md5="+fsMeta.getMd5());
					local_num ++;
					return;
				}
				//验证存储中是否存在
				boolean exist = hdfs.isExist(fsMeta.getUri());
				if(!exist) {
					log.info("!!!!! hdfs not exist. md5="+fsMeta.getMd5());
					getFileAndUser(fsMeta.getMd5());
					total_error_num ++;
				}
				return;
			}
			log.info("db not exist md5");
		} catch (Exception e) {
			log.error("get file error:",e);
		}
	}

	/**
	 * 获得fsmeta表中的用户名, 和path, 写入文件
	 * @param md5
	 */
	private void getFileAndUser(String md5) {
		List<FsMeta> fmList = DB.getFsMetaByMd5(md5);
		for(FsMeta fm : fmList) {
			log.info("!!! hdfs not exist");
			pathInFile(fm.getUsername(), fm.getPath());
		}
	}

	/**
	 * 将username 和 path写入文件中
	 * @param path
	 */
	private void pathInFile(String useranme, String path) {
		try {
			File file = new File("./noFile.log");
			if(!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file, true);
			fw.write(useranme+"   |   "+path  + "\r\n");
			fw.close();
		} catch (Exception e) {
			log.error("path in file error.", e);
		}
	}

	public void countNum() {
		log.info("service running  , fisrt start=" + start);
		if(start < 0) {
			start = 0;
		}
		while (true) {
			List<FsMeta> metas = null;
			try {
				metas = DB.getAlimeta(start);
			} catch (Exception e1) {
				log.error("getFsMeta error " + start, e1);
			}
			
			if (null == metas) {
				try {
					log.error("metas is null");
				} catch (Exception e) {
					log.error("metas is null",e);
				}
				return;
			}
			if (metas.size() == 0) {
				log.info("metas.size() == 0 all data done success !!!");
				return;
			}
			log.info("service running  , process to n=" + start);
			start += 100;
			for (FsMeta fsMeta : metas) {
				doWorkAli(fsMeta);
			}
			log.info("total_num="+total_num
					+" | total_error_num="+total_error_num 
					+" | local_num="+local_num);
		}
	}
	
}
