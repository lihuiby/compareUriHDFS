package com.emacle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emacle.constant.Constant;
import com.emacle.service.AlimetaService;


public class CompareMain {
	private static Logger log = LoggerFactory.getLogger(CompareMain.class);

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			log.info("start......" + args);
			AlimetaService alimetaService = new AlimetaService(Constant.START_NUM);
			if(Constant.ali.equals("ali")) {
				alimetaService.countNum();
			}else {
				alimetaService.init();
			}
			log.info("end compareUri");
		} catch (Exception e) {
			log.error("error",e);
		}finally {
			
		}
	}
}
