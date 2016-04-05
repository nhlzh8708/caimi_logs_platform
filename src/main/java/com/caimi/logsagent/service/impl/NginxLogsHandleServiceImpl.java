package com.caimi.logsagent.service.impl;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.caimi.logsagent.dao.NginxLogsDao;
import com.caimi.logsagent.dao.impl.NginxLogsDaoImpl;
import com.caimi.logsagent.entity.NginxLogsSrcEntity;
import com.caimi.logsagent.service.NginxLogsHandleService;
import com.caimi.logsagent.utils.DbConnManager;
import com.caimi.logsagent.utils.NginxPropManager;
import com.caimi.logsagent.utils.StringUtils;

public class NginxLogsHandleServiceImpl implements NginxLogsHandleService {

	private NginxLogsDao dao;
	private final Logger Log = Logger.getLogger(NginxLogsHandleServiceImpl.class);
	private NginxLogsDao nginxLogsDao = new NginxLogsDaoImpl();

	@Override
	public void nginxLogsImoprt(String file) throws Exception {
		
		File logFile = new File(file);
		BufferedReader br = null;
		//文件不存在，抛出异常
		if ( null == logFile || !logFile.exists()){
			throw new IOException();
		}
		
		try{			
			Map<String, Integer> countInfo4Map = new HashMap<String,Integer>();
			countInfo4Map.put("total", 0);
			countInfo4Map.put("filter_line", 0);
			countInfo4Map.put("commitIdx", 0);
			
			
			br = new BufferedReader(new FileReader(logFile));
			String readLine = null;
			String hostname = NginxPropManager.getInstance().HOSTNAME;
			
			List<NginxLogsSrcEntity> ngxLogsList = new ArrayList<NginxLogsSrcEntity>();
			
			while ((readLine = br.readLine()) != null){
				countInfo4Map.put("total", countInfo4Map.get("total")+1);
				
				//过滤keepalive信息
				Pattern pattern = Pattern.compile("KeepAliveClientaccess");
				Matcher matcher = pattern.matcher(readLine);
				if (matcher.find()){
					countInfo4Map.put("filter_line", countInfo4Map.get("filter_line")+1);
					continue;
				}
				
				countInfo4Map.put("commitIdx", countInfo4Map.get("commitIdx")+1);

				String[] contentArray = readLine.split("\\|");
				NginxLogsSrcEntity entity = new NginxLogsSrcEntity();
				entity.setLogTime(StringUtils.str2Long(contentArray[0]));
				entity.setRemoteAddr(StringUtils.isNullOrEmpty(contentArray[1]));
				entity.setRemoteUser(StringUtils.isNullOrEmpty(contentArray[2]));
				entity.setStatus(StringUtils.isNullOrEmpty(contentArray[3]));
				entity.setRequestTime(StringUtils.str2Doulbe(contentArray[4]));
				
				String requestStr = StringUtils.isNullOrEmpty(contentArray[5]);
				if ( null!=requestStr && requestStr.length() > 512){
					requestStr = requestStr.substring(0, 511);
				}
				entity.setRequest(StringUtils.isNullOrEmpty(requestStr));
				
				String httpRefererStr = StringUtils.isNullOrEmpty(contentArray[6]);
				if ( null !=httpRefererStr && httpRefererStr.length() > 512){
					httpRefererStr = httpRefererStr.substring(0,511);
				}
				
				entity.setHttpReferer(StringUtils.isNullOrEmpty(httpRefererStr));
				
				entity.setConnection(StringUtils.isNullOrEmpty(contentArray[7]));
				entity.setConnectionRequests(StringUtils.isNullOrEmpty(contentArray[8]));
				entity.setUpstreamStatus(StringUtils.isNullOrEmpty(contentArray[10]));
				entity.setRequestLength(StringUtils.str2Doulbe(contentArray[11]));
				entity.setBytesSent(StringUtils.str2Doulbe(contentArray[12]));
				entity.setBodyBytesSent(StringUtils.str2Doulbe(contentArray[13]));
				entity.setHttpUserAgent(StringUtils.isNullOrEmpty(contentArray[14]));
				entity.setHost(hostname);
				String merge_id = entity.getRemoteAddr()+"_"+hostname+"_"+entity.getConnection();
				entity.setMergeId(StringUtils.isNullOrEmpty(merge_id));
				
				ngxLogsList.add(entity);
				
				//如果大于设定值，提交一次
				if ( countInfo4Map.get("commitIdx") > NginxPropManager.getInstance().READLINE_ONCE){
					countInfo4Map.put("commitIdx", 0);
					Connection conn = DbConnManager.getInstance().getConnnection();
					nginxLogsDao.addNginxLogs(conn, ngxLogsList);
					ngxLogsList = new ArrayList<NginxLogsSrcEntity>();
				}
			}
			
//			System.out.println("total:"+countInfo4Map.get("total")
//								+"filter_line:"+countInfo4Map.get("filter_line")
//								+"commitIdx:"+countInfo4Map.get("commitIdx"));
			Connection conn = DbConnManager.getInstance().getConnnection();
			nginxLogsDao.addNginxLogs(conn, ngxLogsList);
			ngxLogsList = new ArrayList<NginxLogsSrcEntity>();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if ( br !=null){
				br.close();
			}
		}
		

	}
	

//	0.004
//	304
//	805
//	253
//	0
//	Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; 2014011 Build/HM2014011) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.4 TBS/025489 Mobile Safari/533.1 MicroMessenger/6.3.15.49_r8aff805.760 NetType/WIFI Language/zh_CNaccess_log/logs/nginx_logs/access.logmain
	public NginxLogsDao getDao() {
		return dao;
	}
	public void setDao(NginxLogsDao dao) {
		this.dao = dao;
	}


}
