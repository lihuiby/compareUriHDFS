package com.emacle.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

public class Constant {
	
	private static final ResourceBundle bundle  = PropertyResourceBundle.getBundle("cf");

	public static final String COMPANYID = bundle.getString("COMPANYID");
	
	public static final String sql = bundle.getString("sql");
	public static final String sql2 = bundle.getString("sql2");
	public static final String getFsMetaByMd5 = bundle.getString("getFsMetaByMd5");
	
	public static final String ali = bundle.getString("ali");
	
	public static final String COMPANY_PATH = bundle.getString("COMPANY_PATH");
	
	public static final String HDFSPOINT = bundle.getString("HDFSPOINT");
	
	public static final int START_NUM = Integer.parseInt(bundle.getString("START_NUM"));
	
	public static final String MYSQLDRIVER =  bundle.getString("MYSQLDRIVER");
	public static final String MYSQL_USERNAME =  bundle.getString("MYSQL_USERNAME");
	public static final String MYSQL_PASS =  bundle.getString("MYSQL_PASS");
	public static final String MYSQL_URL =  bundle.getString("MYSQL_URL");

	public static void main(String[] args) {
		System.out.println(MYSQL_URL);
	}

}
