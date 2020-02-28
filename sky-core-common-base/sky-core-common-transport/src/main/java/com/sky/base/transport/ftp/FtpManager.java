package com.sky.base.transport.ftp;


/**
* @ClassName: FtpManager
* @Description: ftp链接管理接口
* @author chenyt chenyt1@allinpay.com
* @date 2015-6-30 下午3:40:57
*
*/ 
public interface FtpManager {
	/**
	 * @param type 1:下载  2：上传
	 * @return
	 */
	public FtpInf getFtpclient(int type);
	
	public void releaseFtpclient(FtpInf ftpinf);
	
}
