package com.sky.service.api.struct.domain.common;

public class IBMMQBinding {
	private String username;
	private String password;
	private String host;
	private Integer port;
	private String qmgr;
	private Integer ccsid;
	private String channel;
	private String queue;
	
	public IBMMQBinding() {
		super();
	}
	public IBMMQBinding(String username, String password, String host, 
			Integer port, String qmgr, Integer ccsid,
			String channel, String queue) {
		super();
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
		this.qmgr = qmgr;
		this.ccsid = ccsid;
		this.channel = channel;
		this.queue = queue;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getQmgr() {
		return qmgr;
	}
	public void setQmgr(String qmgr) {
		this.qmgr = qmgr;
	}
	public Integer getCcsid() {
		return ccsid;
	}
	public void setCcsid(Integer ccsid) {
		this.ccsid = ccsid;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	
	
}
