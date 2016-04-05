package com.caimi.logsagent;

import java.io.File;
import java.util.regex.Pattern;

import com.caimi.logsagent.service.NginxLogsHandleService;
import com.caimi.logsagent.service.impl.NginxLogsHandleServiceImpl;
import com.caimi.logsagent.utils.NginxPropManager;

public class StartLoadIn {

	public static void main(String[] args) {
		NginxLogsHandleService nginxLogsHandleService = new NginxLogsHandleServiceImpl();
		try{			
			String logs_load_in_path = NginxPropManager.getInstance().LOGS_IN_PATH;
			String logs_backup_path  = NginxPropManager.getInstance().BACKUP_PATH;
			String logs_match_name   = NginxPropManager.getInstance().LOGS_NAME_MATCH;
			
			while (true){
				File loadInPath = new File(logs_load_in_path);
				if (loadInPath.exists() && loadInPath.isDirectory()){
					String[] listDir = loadInPath.list();
					for(String tmpFileName:listDir){
						Pattern pattern = Pattern.compile(logs_match_name);
						if (pattern.matcher(tmpFileName).matches()){
							String logsFullPath = logs_load_in_path+"/"+tmpFileName;
							System.out.println("begin load in logs :"+logsFullPath);
							Long startTime = System.currentTimeMillis();
							nginxLogsHandleService.nginxLogsImoprt(logsFullPath);
							System.out.println("end load in logs ! use :" + String.valueOf((System.currentTimeMillis()-startTime)) + " SEC!");
							String backupFullPath =logs_backup_path+"/"+tmpFileName;
							File oldFileName = new File(logsFullPath);
							oldFileName.renameTo(new File(backupFullPath));
							System.out.println("file From ["+logsFullPath+"] move to ["+backupFullPath+"]");
						}else{
							System.out.println(tmpFileName+" not match");
						}
						System.out.println("End");
					}
				}
				System.out.println("sleep 30S");
				Thread.sleep(30000);
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
