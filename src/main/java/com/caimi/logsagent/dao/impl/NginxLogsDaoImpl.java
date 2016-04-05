package com.caimi.logsagent.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.log4j.Logger;

import com.caimi.logsagent.dao.NginxLogsDao;
import com.caimi.logsagent.entity.NginxLogsSrcEntity;
import com.caimi.logsagent.utils.DbConnManager;

public class NginxLogsDaoImpl implements NginxLogsDao {
	
	private final Logger log = Logger.getLogger(NginxLogsDaoImpl.class);
	
	@Override
	public int addNginxLogs(Connection conn,List<NginxLogsSrcEntity> entity) {
		//System.out.println("addNginxLogs size:"+entity.size());
		PreparedStatement ps = null;
		try{
			conn.setAutoCommit(false);
			String insertSql = "insert into nginx_logs_detail(log_time, "
					  + "merge_id,"
					  + "remote_addr,"
					  + "remote_user,"
					  + "status,"
					  + "request_time,"
					  + "request,"
					  + "http_referer,"
					  + "connection,"
					  + "connection_requests,"
					  + "upstream_status,"
					  + "request_length,"
					  + "bytes_sent,"
					  + "body_bytes_sent,"
					  + "http_user_agent,"
					  + "host) "
					  + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
			ps = conn.prepareStatement(insertSql);
			
			//批量插入
			int countInsertLogs = 0 ;
			for (NginxLogsSrcEntity tmpEntity : entity){
				countInsertLogs++;
				ps.setLong(1,tmpEntity.getLogTime());
				ps.setString(2,tmpEntity.getMergeId());
				ps.setString(3,tmpEntity.getRemoteAddr());
				ps.setString(4,tmpEntity.getRemoteUser());
				ps.setString(5,tmpEntity.getStatus());
				ps.setDouble(6, tmpEntity.getRequestTime());
				ps.setString(7, tmpEntity.getRequest());
				ps.setString(8, tmpEntity.getHttpReferer());
				ps.setString(9, tmpEntity.getConnection());
				ps.setString(10, tmpEntity.getConnectionRequests());
				ps.setString(11, tmpEntity.getUpstreamStatus());
				ps.setDouble(12, tmpEntity.getRequestLength());
				ps.setDouble(13, tmpEntity.getBytesSent());
				ps.setDouble(14, tmpEntity.getBodyBytesSent());
				ps.setString(15, tmpEntity.getHttpUserAgent());
				ps.setString(16, tmpEntity.getHost());
				ps.addBatch();
				if ( ++countInsertLogs % 1000 == 0){
					 ps.executeBatch();
					 conn.commit();
				}
			}
			ps.executeBatch();
			conn.commit();
		}catch(Exception e){
			try{
				conn.rollback();
			}catch(Exception es){
				es.printStackTrace();
				log.error(es.getMessage());
			}
			log.error(e.getMessage());
			e.printStackTrace();
		}finally{	
			DbConnManager.getInstance().closeStatement(ps);
			DbConnManager.getInstance().closeConnection(conn);
		}
		
		return 0;
	}

}



