package com.sky.base.transport.ftp;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.lang.string.StringUtils;
/**
 * 
 * @author dengny
 * @version 1.0.0
 * @Title FtpImpl.java
 * @Description ftp实现接入接口
 * @date 2019-03-08
 */
public class FtpImpl implements FtpInf {
	private static final Logger LOGGER = LoggerFactory.getLogger(FtpImpl.class);
	// 详细配置参数
	private FtpConfig ftpParam;
	// 操作类型 1-下载 2-上传
	private int opetype;
	private FTPClient ftpclient;
	private boolean autoColseConnection = true;
	private String encoding;

	public FtpImpl(FtpConfig ftpcfg, int opetype) {
		this.ftpParam = ftpcfg;
		this.opetype = opetype;
	}

	public FtpConfig getFtpParam() {
		return ftpParam;
	}

	public void setFtpParam(FtpConfig ftpParam) {
		this.ftpParam = ftpParam;
	}

	public int getOpetype() {
		return opetype;
	}

	public void setOpetype(int opetype) {
		this.opetype = opetype;
	}

	public FTPClient getFtpclient() {
		return ftpclient;
	}

	public void setFtpclient(FTPClient ftpclient) {
		this.ftpclient = ftpclient;
	}

	public boolean isAutoColseConnection() {
		return autoColseConnection;
	}

	public void setAutoColseConnection(boolean autoColseConnection) {
		this.autoColseConnection = autoColseConnection;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean connectHost() {
		boolean result = false;
		try {

			TrustManager[] trustManager = new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };
			SSLContext sslContext = null;
			try {
				sslContext = SSLContext.getInstance("SSL");
				sslContext.init(null, trustManager, new SecureRandom());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			}
			ftpclient = new FTPClient();
			LOGGER.info("连接ftp服务器:[{}:{}], 登录用户:[{}], 模式:[{}]", ftpParam.getHost(), ftpParam.getPort(),
			        ftpParam.getUser(), ftpParam.getFtpmode());
			ftpclient.connect(ftpParam.getHost(), ftpParam.getPort());
			if (!ftpclient.isConnected()) {
				LOGGER.error("连接FTP服务器失败--->" + ftpParam.getHost() + ":" + ftpParam.getPort());
				return result;
			}
			ftpclient.login(ftpParam.getUser(), ftpParam.getPassword());

			if (ftpParam.getFtpmode().toLowerCase().equals("pasv"))
				ftpclient.setPassive(true);
			else
				ftpclient.setPassive(false);

			LOGGER.info("FTP设置工作目录:" + ftpParam.getRemotepath());
			if (!StringUtils.isEmpty(ftpParam.getRemotepath())) {
				ftpclient.changeDirectory(ftpParam.getRemotepath());
			}
			ftpclient.setType(FTPClient.TYPE_BINARY);
			if (!StringUtils.isEmpty(encoding)) { // add by mofu 加入自定义编码
				ftpclient.setCharset(encoding);
				LOGGER.info("已改变字符编码为:" + this.encoding);
			}
			result = true;
		} catch (Exception ex) {
			LOGGER.error("连接FTP服务器错误,请检查配置参数和网络", ex);
			return false;
		}
		LOGGER.info((new StringBuilder("Ftp服务器连接成功:")).append(ftpParam.getHost()).append(":").append(ftpParam.getPort())
				.toString());
		return true;
	}

	/**
	 * 关闭连接
	 */
	public void closeConnect() {
		try {
			ftpclient.disconnect(true);
			ftpclient = null;
			LOGGER.info("Ftp Disconnect Success");
		} catch (Exception ex) {
			LOGGER.error("关闭FTP连接错误", ex);
		}
	}

	public boolean uploadFile(String localfile, String remotefile) {
		boolean result = false;
		try {
			if (localfile == null || localfile.trim().length() == 0) {
				LOGGER.error("本地文件参数错误,无法上传");
				return result;
			}
			if (remotefile == null || remotefile.trim().length() == 0) {
				LOGGER.error("远程文件参数错误,无法上传");
				return result;
			}
			if (ftpclient == null) {
				LOGGER.error("ftp服务器尚未连接,无法上传");
				return result;
			}

			if (ftpParam.getLocalpath() != null && ftpParam.getLocalpath().trim().length() > 0) {
				if (!ftpParam.getLocalpath().endsWith(File.separator))
					localfile = ftpParam.getLocalpath() + File.separator + localfile;
				else
					localfile = ftpParam.getLocalpath() + localfile;
			}

			File file = new File(localfile);
			if (!file.exists()) {
				LOGGER.error("本地文件不存在,无法上传");
				return result;
			}
			try {
				ftpclient.upload(file);
				String[] temp = localfile.replaceAll("\\\\", "/").split("/");
				if (!temp[temp.length - 1].equals(remotefile))
					ftpclient.rename(temp[temp.length - 1], remotefile);
				LOGGER.info((new StringBuilder("上传文件:")).append(localfile).append(" 至ftp服务器:").append(remotefile)
						.append(" 成功").toString());
				result = true;
			} catch (Exception ex) {
				LOGGER.error((new StringBuilder("上传文件:")).append(localfile).append(" 至ftp服务器:").append(remotefile)
						.append(" 失败").toString(), ex);
				result = false;
			}

		} catch (Exception ex) {
			LOGGER.error("上传异常", ex);
			result = false;
		} finally {
			if (autoColseConnection) {
				this.closeConnect();
			}
		}
		return result;
	}

	/**
	 * 上传队列文件
	 * 
	 * @param localfile  本地文件队列,设置好localpath后仅设置文件名
	 * @param remotefile 远程文件队列,设置好remotepath后仅设置文件名
	 * @return
	 */
	public boolean uploadFile(List<String> localfile, List<String> remotefile) {
		boolean result = false;
		try {
			if (localfile == null || localfile.size() == 0) {
				LOGGER.error("本地文件队列参数错误,无法上传");
				return result;
			}
			if (remotefile == null || remotefile.size() == 0) {
				LOGGER.error("远程文件队列参数错误,无法上传");
				return result;
			}
			if (localfile.size() != remotefile.size()) {
				LOGGER.error("文件队列中本地和远程的文件个数不符");
				return result;
			}
			if (ftpclient == null) {
				LOGGER.error("ftp服务器尚未连接,无法上传");
				return result;
			}
			try {

				for (int i = 0; i < localfile.size(); i++) {
					uploadFile(localfile.get(i), remotefile.get(i));
				}
			} catch (Exception ex) {
				LOGGER.error("上传文件至ftp服务器部分或全部失败", ex);
				result = false;
			}
		} catch (Exception ex) {
			LOGGER.error("上传异常", ex);
			result = false;
		} finally {
			if (autoColseConnection) {
				this.closeConnect();
			}
		}
		return result;
	}

	/**
	 * 下载单文件
	 * 
	 * @param localfile  本地文件名,设置localpath后此处仅设置文件名
	 * @param remotefile 远程文件路径,设置remotepath后此处仅设置文件名
	 * @return
	 */
	public boolean downloadFile(String localfile, String remotefile) {
		boolean result = false;
		try {
			if (localfile == null || localfile.trim().length() == 0) {
				LOGGER.error("本地文件参数错误,无法下载");
				return result;
			}
			if (remotefile == null || remotefile.trim().length() == 0) {
				LOGGER.error("远程文件参数错误,无法下载");
				return result;
			}
			if (ftpclient == null) {
				LOGGER.error("ftp服务器尚未连接,无法下载");
				return result;
			}
			try {

				if (ftpParam.getLocalpath() != null && ftpParam.getLocalpath().trim().length() > 0) {
					if (!ftpParam.getLocalpath().endsWith(File.separator))
						localfile = ftpParam.getLocalpath() + File.separator + localfile;
					else
						localfile = ftpParam.getLocalpath() + localfile;

				}
				ftpclient.download(remotefile, new File(localfile));
				LOGGER.info((new StringBuilder("下载文件:")).append(localfile).append(" 从ftp服务器:").append(remotefile)
						.append(" 成功").toString());
				result = true;
			} catch (Exception ex) {
				LOGGER.error((new StringBuilder("下载文件:")).append(localfile).append(" 从ftp服务器:").append(remotefile)
						.append(" 失败").toString(), ex);
				result = false;
			}

		} catch (Exception ex) {
			LOGGER.error("下载异常", ex);
			result = false;
		} finally {
			if (autoColseConnection) {
				this.closeConnect();
			}
		}
		return result;
	}

	/**
	 * 下载队列文件
	 * 
	 * @param localfile  本地文件队列,设置localpath后此处仅设置文件名
	 * @param remotefile 远程文件队列,设置remotepath后此处仅设置文件名
	 * @return
	 */
	public boolean downloadFile(List<String> localfile, List<String> remotefile) {
		boolean result = false;
		try {
			if (localfile == null || localfile.size() == 0) {
				LOGGER.error("本地文件参数错误,无法下载");
				return result;
			}
			if (remotefile == null || remotefile.size() == 0) {
				LOGGER.error("远程文件参数错误,无法下载");
				return result;
			}
			if (localfile.size() != remotefile.size()) {
				LOGGER.error("文件队列中本地和远程的文件个数不符");
				return result;
			}
			if (ftpclient == null) {
				LOGGER.error("ftp服务器尚未连接,无法下载");
				return result;
			}
			try {
				for (int i = 0; i < remotefile.size(); i++) {
					downloadFile(localfile.get(i), remotefile.get(i));
				}
				result = true;
			} catch (Exception ex) {
				LOGGER.error("下载文件从ftp服务器部分或全部失败", ex);
				result = false;
			}

		} catch (Exception ex) {
			LOGGER.error("下载异常", ex);
			result = false;
		} finally {
			if (autoColseConnection) {
				this.closeConnect();
			}
		}
		return result;
	}

	/**
	 * 列出目录下文件列表[仅列出列表]
	 * 
	 * @param remotepath 设置ftp参数的remotepath后此处可不设置
	 * @return List<String>
	 */
	public List<String> listFilesOnly() {
		try {
			List<String> ls = null;
			FTPFile[] ffarrs = ftpclient.list();
			if (ffarrs == null || ffarrs.length == 0) {
				LOGGER.debug("FTP当前目录下没有文件");
				return null;
			}
			for (int i = 0; i < ffarrs.length; i++) {
				FTPFile ff = ffarrs[i];
				if (ff.getType() != FTPFile.TYPE_FILE) {
					LOGGER.debug("文件:" + ff.getName() + " 为目录或连接");
					continue;
				}
				if (ls == null)
					ls = new ArrayList<String>();
				ls.add(ff.getName());
			}
			return ls;
		} catch (Exception ex) {
			LOGGER.error("列出ftp下当前文件失败", ex);
			return null;
		}
	}

	/**
	 * 列出目录下文件列表[仅列出列表]
	 * 
	 * @param remotepath 设置ftp参数的remotepath后此处可不设置
	 * @return List<String>
	 */
	public FTPFile[] listFiles() {
		try {
			FTPFile[] ffarrs = ftpclient.list();
			if (ffarrs == null || ffarrs.length == 0) {
				LOGGER.debug("FTP当前目录下没有文件");
				return null;
			}
			return ffarrs;
		} catch (Exception ex) {
			LOGGER.error("列出ftp下当前文件失败", ex);
			return null;
		}
	}

	// 获取文件夹大小
	@Override
	public Long getDirsSize() throws Exception {
		Long result = 0L;
		FTPFile[] ffarrs = ftpclient.list();
		for (FTPFile file : ffarrs) {
			result += file.getSize();
		}
		return result;
	}

	/**
	 * 列出目录下文件列表[包括目录]
	 * 
	 * @param remotepath 设置ftp参数的remotepath后此处可不设置
	 * @return List<String>
	 */
	public List<String> listFilesAndDirs() {
		try {
			List<String> ls = null;
			String[] ffarrs = ftpclient.listNames();
			for (int i = 0; i < ffarrs.length; i++) {
				if (ls == null)
					ls = new ArrayList<String>();
				ls.add(ffarrs[i]);
			}
			return ls;
		} catch (Exception ex) {
			LOGGER.error("列出ftp下当前文件失败", ex);
			return null;
		}
	}

	/**
	 * 当前路径下重命名 oldname和newname均相对于当前路径
	 * 
	 * @param oldname
	 * @param newname
	 * @return
	 */
	public boolean rename(String oldname, String newname) {
		try {
			this.ftpclient.rename(oldname, newname);
			return true;
		} catch (Exception ex) {
			LOGGER.error("重命名文件失败", ex);
			return false;
		}
	}

	/**
	 * 当前路径下删除 filename相对于当前路径
	 * 
	 * @param filename
	 * @return
	 */
	public boolean remove(String filename) {
		try {
			this.ftpclient.deleteFile(filename);
			return true;
		} catch (Exception ex) {
			LOGGER.error("删除文件失败", ex);
			return false;
		}
	}

	/**
	 * 当前路径下删除 文件夹
	 * 
	 * @param filename
	 * @return
	 */
	public boolean removedir(String dirname) {
		try {
			this.ftpclient.deleteDirectory(dirname);
			return true;
		} catch (Exception ex) {
			LOGGER.error("删除文件夹失败", ex);
			return false;
		}
	}

	/**
	 * 当前路径下新建目录 dirname相对于当前路径
	 * 
	 * @param dirname
	 * @return
	 */
	public boolean mkdir(String dirname) {
		try {
			this.ftpclient.createDirectory(dirname);
			return true;
		} catch (Exception ex) {
			LOGGER.error("删除文件失败", ex);
			return false;
		}
	}

	/**
	 * 当前路径下新建目录(支持多级目录)并变换工作路径至新建目录下 dirname相对于当前路径
	 * 
	 * @param dirname
	 * @return
	 */
	public boolean mkdirandcd2newdir(String dirname) {
		try {
			String[] dirs = dirname.split("/");
			for (String dir : dirs) {
				if (StringUtils.isEmpty(dir)) {
					continue;
				}
				if (!this.fileExistCheck(dir)) {
					this.ftpclient.createDirectory(dir);
				}
				this.cd(dir);
			}
			return true;
		} catch (Exception ex) {
			LOGGER.error("删除文件失败", ex);
			return false;
		}
	}

	public boolean dirsExistCheck(String dir) {
		try {
			String[] dirs = dir.split("/");

			for (String str : dirs) {
				if (str == null) {
					continue;
				}
				if (!this.fileExistCheck(dir)) {
					return false;
				}
				this.cd(str);
			}
			return true;
		} catch (Exception ex) {
			LOGGER.error("检测目录失败:" + ex.getMessage(), ex);
			return false;
		}
	}

	/**
	 * 变换路径 dirname相对于FTP根目录的绝对路径
	 * 
	 * @param dirname
	 * @return
	 */
	public boolean cd(String dirname) {
		try {
			this.ftpclient.changeDirectory(dirname);
			return true;
		} catch (Exception ex) {
			LOGGER.error("变更目录失败" + dirname, ex);
			return false;
		}
	}

	/**
	 * 文件存在性检查
	 * 
	 * @param filename
	 * @return boolean
	 * @author l_ghui
	 * @date 2009-10-12
	 */
	public boolean fileExistCheck(String filename) {
		boolean result = false;
		try {
			FTPFile[] fileList = ftpclient.list();
			if (fileList != null) {
				for (int i = 0; i < fileList.length; i++) {
					if (fileList[i].getName().equals(filename)) {
						return true;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
