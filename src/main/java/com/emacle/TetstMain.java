package com.emacle;

import com.emacle.constant.Constant;
import com.emacle.storage.HDFSFileHandler;

public class TetstMain {
	public static void main(String[] args) {
		//System.out.println(Constant.sql2);
		
		System.out.println(System.currentTimeMillis());
		
		Long a = 1000*24*3600*30L;
		System.out.println(a);
		System.out.println(System.currentTimeMillis() - a);
		
		
	}
}
