package com.caimi.logsagent.utils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Properties;

import com.caimi.logsagent.exceptions.DbConnManagerException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DbConnManager {
	
	private static DbConnManager singleton = null;
	private static ComboPooledDataSource  cpDs = null ;
	//private LinkedList<Connection> connList ;
	private Properties jdbcConf = null;
	
	//读取配置文件
	private void getJdcbConf(){
		try{
			jdbcConf = new Properties(); 
//			String jdbcPath = this.getClass().getClassLoader().getResource("").getPath()+"jdbc.properties";
//			System.out.println("jdbc.prpoerties:"+jdbcPath);
//			jdbcConf.load(new FileInputStream(jdbcPath));		
			jdbcConf.load(this.getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
			System.out.println("read properties!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private DbConnManager(){
		getJdcbConf();
		initcpDs();
	}
	
	//初始化cpds
	private void initcpDs(){
		cpDs = new ComboPooledDataSource(true);
		try{
			String driverClass = StringUtils.isNullOrEmpty(jdbcConf.getProperty("driverClassName"));
			String jdbcUrl     = StringUtils.isNullOrEmpty(jdbcConf.getProperty("url"));
			String user = StringUtils.isNullOrEmpty(jdbcConf.getProperty("username"));
			String pwd  = StringUtils.isNullOrEmpty(jdbcConf.getProperty("password"));
			//System.out.println(driverClass+","+jdbcUrl+","+user+","+pwd);
			if ( null == driverClass || null ==  jdbcUrl || null == user || null == pwd){
				throw new  DbConnManagerException("DB config error !");
			}
			
			String maxPoolSize = StringUtils.isNullOrEmpty(jdbcConf.getProperty("C3P0.maxPoolSize").trim());
			String minPoolSize = StringUtils.isNullOrEmpty(jdbcConf.getProperty("C3P0.minPoolSize").trim());
			String acquireIncrement = StringUtils.isNullOrEmpty(jdbcConf.getProperty("C3P0.acquireIncrement").trim());
			String initialPoolSize = StringUtils.isNullOrEmpty(jdbcConf.getProperty("C3P0.initialPoolSize").trim());
			String maxIdleTime = StringUtils.isNullOrEmpty(jdbcConf.getProperty("C3P0.maxIdleTime").trim());
			
			
			cpDs.setDriverClass(driverClass);
			cpDs.setJdbcUrl(jdbcUrl);
			cpDs.setUser(user);
			cpDs.setPassword(pwd);
			
			if (null != maxPoolSize){
				cpDs.setMaxPoolSize(Integer.valueOf(maxPoolSize));
			}
			
			if (null != minPoolSize){
				cpDs.setMinPoolSize(Integer.valueOf(minPoolSize));
			}
			
			if (null != acquireIncrement){
				cpDs.setAcquireIncrement(Integer.valueOf(acquireIncrement));
			}
			
			if (null != initialPoolSize){
				cpDs.setInitialPoolSize(Integer.valueOf(initialPoolSize));
			}
			
			if (null != maxIdleTime){
				cpDs.setMaxIdleTime(Integer.valueOf(maxIdleTime));
			}

		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	//获取单例
	public static  DbConnManager getInstance(){
		if ( null == singleton){
			synchronized (DbConnManager.class) {
				if (null == singleton){
					singleton = new DbConnManager();
				}
			}
		}
		return singleton;
	}
	
	//打开链接
	public  synchronized Connection getConnnection(){
		Connection conn = null ;
		try{
			conn =  cpDs.getConnection();
		}catch (Exception e){
			e.printStackTrace();
		}
		return conn;
	}
	
	//关闭链接
	public  synchronized void closeConnection(Connection conn){
		
		try{
			if ( null != conn){
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public  synchronized void closeStatement(Statement st){
		try{
			if ( null != st){
				st.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
