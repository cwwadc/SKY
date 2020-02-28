package com.sky.base.transport.ftp;
/***
 * 
 * @author dengny
 * @version 1.0.0
 * @Title FtpConfig.java
 * @Description ftp/sftp 连接参数
 * @date 2019-03-08
 */
public class FtpConfig {
	/**
	 * 主机ip
	 */
	private String host; 
	/**
	 * 端口号
	 */
	private int port;
	/**
	 * ftp模式
	 */
	private String ftpmode;
	/***
	 * 用户名
	 */
	private String user;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 远程地址
	 */
	private String remotepath;
	/***
	 * 本地地址
	 */
	private String localpath;
	/** 
	 * 密钥文件路径 
	 */
	private String keyFile;
	/** 
	 * 密钥口令 
	 */
	private String passphrase;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getFtpmode() {
		return ftpmode;
	}
	public void setFtpmode(String ftpmode) {
		this.ftpmode = ftpmode;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemotepath() {
		return remotepath;
	}
	public void setRemotepath(String remotepath) {
		this.remotepath = remotepath;
	}
	public String getLocalpath() {
		return localpath;
	}
	public void setLocalpath(String localpath) {
		this.localpath = localpath;
	}
	public String getKeyFile() {
		return keyFile;
	}
	public void setKeyFile(String keyFile) {
		this.keyFile = keyFile;
	}
	public String getPassphrase() {
		return passphrase;
	}
	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
	@Override
	public String toString() {
		return "FtpConfig [host=" + host + ", port=" + port + ", ftpmode=" 
				+ ftpmode + ", user=" + user + ", password=" + password 
				+ ", remotepath=" + remotepath + ", localpath=" + localpath 
				+ ", keyFile=" + keyFile + ", passphrase=" + passphrase + "]";
	}

	
	
}
