package com.caimi.logsagent.dao;

import java.sql.Connection;
import java.util.List;

import com.caimi.logsagent.entity.NginxLogsSrcEntity;

public interface NginxLogsDao {
	
	
	public int addNginxLogs(Connection conn, List<NginxLogsSrcEntity> entity) throws Exception;
	
}
