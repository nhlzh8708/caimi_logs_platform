package com.caimi.logsagent.entity;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

public class NginxLogsSrcEntity {
	private Long id;
	private Long logTime;
	private String mergeId;
	private String remoteAddr;
	private String remoteUser;
	private String status;
	private Double requestTime;
	private String request;
	private String httpReferer;
	private String connection;
	private String connectionRequests;
	private String upstreamStatus;
	private Double requestLength;
	private Double bytesSent;
	private Double bodyBytesSent;
	private String httpUserAgent;
	private String host;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLogTime() {
		return logTime;
	}
	public void setLogTime(Long logTime) {
		this.logTime = logTime;
	}
	public String getMergeId() {
		return mergeId;
	}
	public void setMergeId(String mergeId) {
		this.mergeId = mergeId;
	}
	public String getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	public String getRemoteUser() {
		return remoteUser;
	}
	public void setRemoteUser(String remoteUser) {
		this.remoteUser = remoteUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Double requestTime) {
		this.requestTime = requestTime;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getHttpReferer() {
		return httpReferer;
	}
	public void setHttpReferer(String httpReferer) {
		this.httpReferer = httpReferer;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
	public String getConnectionRequests() {
		return connectionRequests;
	}
	public void setConnectionRequests(String connectionRequests) {
		this.connectionRequests = connectionRequests;
	}
	public String getUpstreamStatus() {
		return upstreamStatus;
	}
	public void setUpstreamStatus(String upstreamStatus) {
		this.upstreamStatus = upstreamStatus;
	}
	public Double getRequestLength() {
		return requestLength;
	}
	public void setRequestLength(Double requestLength) {
		this.requestLength = requestLength;
	}
	public Double getBytesSent() {
		return bytesSent;
	}
	public void setBytesSent(Double bytesSent) {
		this.bytesSent = bytesSent;
	}
	public Double getBodyBytesSent() {
		return bodyBytesSent;
	}
	public void setBodyBytesSent(Double bodyBytesSent) {
		this.bodyBytesSent = bodyBytesSent;
	}
	public String getHttpUserAgent() {
		return httpUserAgent;
	}
	public void setHttpUserAgent(String httpUserAgent) {
		this.httpUserAgent = httpUserAgent;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	
}

