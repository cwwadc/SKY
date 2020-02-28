package com.sky.base.transport.ftp;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @ClassName: FtpManagerImpl
* @Description: ftp链接管理
* @author chenyt chenyt1@allinpay.com
* @date 2015-6-15 下午3:39:59
*
*/ 
public class FtpManagerImpl implements FtpManager{
	private static final Logger LOGGER = LoggerFactory.getLogger(FtpManagerImpl.class);
	private int counter = 0;
	private int maxconn;
	private String name;
	private String encoding;
	private FtpConfig ftpcfg;
	private  String linkType;
	private BlockingQueue<FtpInf> idleclients;

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getMaxconn() {
		return maxconn;
	}

	public void setMaxconn(int maxconn) {
		this.maxconn = maxconn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public FtpConfig getFtpcfg() {
		return ftpcfg;
	}

	public void setFtpcfg(FtpConfig ftpcfg) {
		this.ftpcfg = ftpcfg;
	}


	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public BlockingQueue<FtpInf> getIdleclients() {
		return idleclients;
	}

	public void setIdleclients(BlockingQueue<FtpInf> idleclients) {
		this.idleclients = idleclients;
	}

	public FtpManagerImpl(){}
	
	public FtpManagerImpl(int maxconn, String name, String encoding, FtpConfig ftpcfg,String linkType){
		this.name = name;
		this.maxconn = maxconn;
		this.encoding = encoding;
		this.ftpcfg = ftpcfg;
		this.linkType = linkType;
		this.idleclients = new ArrayBlockingQueue<FtpInf>(maxconn);
	}
	@Override
	public FtpInf getFtpclient(int type) {
		FtpInf ftpinf = null;
		if(idleclients.size() == 0){
			synchronized (FtpManagerImpl.class) {
				if(this.counter < this.maxconn)
					ftpinf = increaseClient(type);
			}
		}
		if(ftpinf == null){
			try {
				ftpinf = this.idleclients.take();
				ftpinf.setOpetype(type);
			} catch (Exception e) {
				LOGGER.error(this.name + "take ftpclient err", e);
			}
		}
		return ftpinf;
	}
	@Override
	public void releaseFtpclient(FtpInf ftpinf){
		if(ftpinf != null){
			try {
				ftpinf.closeConnect();
				idleclients.put(ftpinf);
			} catch (Exception e) {
				LOGGER.error(this.name + "release ftpclient err", e);
			}
		}
	}
	
	private FtpInf increaseClient(int type){
		counter++;
		FtpInf ftpinf = null;
		if(this.linkType.equals(FtpConstants.FTP)) {
			ftpinf = new FtpImpl(this.ftpcfg, type);
			if(this.encoding != null) {
				ftpinf.setEncoding(this.encoding);
			}
		}else if(this.linkType.equals(FtpConstants.SFTP)) {
			ftpinf = new SftpImpl(this.ftpcfg, type);
		}
		
		return ftpinf;
	}

}
