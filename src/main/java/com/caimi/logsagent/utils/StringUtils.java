package com.caimi.logsagent.utils;

import com.caimi.logsagent.exceptions.DataTransferException;

public class StringUtils {
	
	//检查字符攒是否空
	public static String isNullOrEmpty(String str){
		if ( null == str || "".equals(str)){
			return null;
		}
		return str;
	}
	
	
	public static Long str2Long(String str) throws Exception{
		Long ret = null;
		if ( null == str || "".equals(str)){
			ret =  null;
		}	
		return Long.parseLong(str);		
	}
	
	public static Double str2Doulbe(String str) throws Exception{
		Long ret = null;
		if ( null == str || "".equals(str)){
			ret =  null;
		}	
		return Double.parseDouble(str);
	}
		
	
}