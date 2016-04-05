package com.caimi.logsagent.utils;

import java.io.InputStream;
import java.util.Properties;

import javax.print.DocFlavor.STRING;

public class NginxPropManager {
	
	private static NginxPropManager npManager;
	public static final String LOGS_TYPE;
	public static final String LOGS_NAME_MATCH;
	public static final String LOGS_IN_PATH;
	public static final String BACKUP_PATH;
	public static final String HOSTNAME;
	public static final Integer READLINE_ONCE;
	public static final String ERRLOGS_PATH;
	public static final String ERRLOGS_HEAD;
		
	
	static{
		InputStream is = NginxPropManager.class.getClassLoader().getResourceAsStream("nginx_logs.properties");
		Properties prop = null;
		try{
			prop = new Properties();
			prop.load(is);
		}catch(Exception e){
			e.printStackTrace();
		}
		LOGS_TYPE       = prop.getProperty("logs.type");
		LOGS_NAME_MATCH = prop.getProperty("logs.name_match");
		LOGS_IN_PATH    = prop.getProperty("logs.in_path");
		BACKUP_PATH     = prop.getProperty("logs.backup_path");
		HOSTNAME        = prop.getProperty("logs.hostname");
		READLINE_ONCE   = Integer.valueOf(prop.getProperty("logs.readline_once"));
		ERRLOGS_PATH    = prop.getProperty("logs.errlogs_path");
		ERRLOGS_HEAD    = prop.getProperty("logs.errlogs_head");
	}
	

	
	private NginxPropManager(){

	}
	
	public static NginxPropManager getInstance(){
		if (null != npManager){
			synchronized (NginxPropManager.class) {
				if (null != npManager){
					npManager = new NginxPropManager();
				}
			}
		}	
		return npManager;
	}
	
}
