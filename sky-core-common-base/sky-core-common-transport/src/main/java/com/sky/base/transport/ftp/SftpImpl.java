package com.sky.base.transport.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.sky.base.lang.string.StringUtils;
/**
 * 
 * @author dengny
 * @version 1.0.0
 * @Title SftpImpl.java
 * @Description sftp实现接口
 * @date 2019-03-08
 */
public class SftpImpl implements FtpInf {
	private static final Logger LOGGER = LoggerFactory.getLogger(FtpImpl.class);
	// 详细配置参数
	private FtpConfig ftpParam;
	// 操作类型 1-下载 2-上传
	private int opetype;
	private Session sshSession;
	private ChannelSftp sftpclient;
	private boolean autoColseConnection = true;
	private String encoding;

	public SftpImpl(FtpConfig ftpcfg, int opetype) {
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

	public Session getSshSession() {
		return sshSession;
	}

	public void setSshSession(Session sshSession) {
		this.sshSession = sshSession;
	}

	public ChannelSftp getSftpclient() {
		return sftpclient;
	}

	public void setSftpclient(ChannelSftp sftpclient) {
		this.sftpclient = sftpclient;
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

	@Override
	public boolean connectHost() {
		try {
			JSch jsch = new JSch();

			if (ftpParam.getKeyFile() != null && !"".equals(ftpParam.getKeyFile())) {
				// 使用密钥验证方式，密钥可以使有口令的密钥，也可以是没有口令的密钥
				if (ftpParam.getPassphrase() != null && !"".equals(ftpParam.getPassphrase())) {
					jsch.addIdentity(ftpParam.getKeyFile(), ftpParam.getPassphrase());
				} else {
					jsch.addIdentity(ftpParam.getKeyFile());
				}
			}
			LOGGER.info("连接ftp服务器:[{}:{}], 登录用户:[{}]", ftpParam.getHost(), ftpParam.getPort(), ftpParam.getUser());
			sshSession = jsch.getSession(ftpParam.getUser(), ftpParam.getHost(), ftpParam.getPort());
			LOGGER.info("SFTP会话开始......");
			if (ftpParam.getPassword() != null && !"".equals(ftpParam.getPassword())) {
				sshSession.setPassword(ftpParam.getPassword());
			}
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");// do not verify host key
			sshSession.setConfig(sshConfig);
			// session.setTimeout(timeout);
			sshSession.setServerAliveInterval(92000);
			sshSession.connect();
			// 参数sftp指明要打开的连接是sftp连接
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftpclient = (ChannelSftp) channel;

			LOGGER.info("服务器当前路径:" + sftpclient.getHome());
			LOGGER.info("SFTP设置工作目录:" + ftpParam.getRemotepath());
			if (!StringUtils.isEmpty(ftpParam.getRemotepath())) {
				sftpclient.cd(ftpParam.getRemotepath());
			}
		} catch (Exception ex) {
			LOGGER.error("连接SFTP服务器错误,请检查配置参数和网络", ex);
			return false;
		}
		LOGGER.info((new StringBuilder("SFTP服务器连接成功:")).append(ftpParam.getHost()).append(":").append(ftpParam.getPort())
				.toString());
		return true;
	}

	@Override
	public void closeConnect() {
		try {
			sshSession.disconnect();
			sftpclient.disconnect();
			sftpclient = null;
			LOGGER.info("SFTP Disconnect Success");
		} catch (Exception ex) {
			LOGGER.error("关闭FTP连接错误", ex);
		}
	}

	@Override
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
			if (sftpclient == null) {
				LOGGER.error("sftp服务器尚未连接,无法上传");
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
				InputStream is = new FileInputStream(file);
				sftpclient.put(is, remotefile);
				LOGGER.info((new StringBuilder("上传文件:")).append(localfile).append(" 至sftp服务器:").append(remotefile)
						.append(" 成功").toString());
				result = true;
				is.close();
			} catch (Exception ex) {
				LOGGER.error((new StringBuilder("上传文件:")).append(localfile).append(" 至sftp服务器:").append(remotefile)
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

	@Override
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
			if (sftpclient == null) {
				LOGGER.error("sftp服务器尚未连接,无法上传");
				return result;
			}
			try {
				for (int i = 0; i < localfile.size(); i++) {
					uploadFile(localfile.get(i), remotefile.get(i));
				}
			} catch (Exception ex) {
				LOGGER.error("上传文件至sftp服务器部分或全部失败", ex);
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

	@Override
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
			if (sftpclient == null) {
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
				File file = new File(localfile);
				sftpclient.get(remotefile, new FileOutputStream(file));
				LOGGER.info((new StringBuilder("下载文件:")).append(localfile).append(" 从sftp服务器:").append(remotefile)
						.append(" 成功").toString());
				result = true;
			} catch (Exception ex) {
				LOGGER.error((new StringBuilder("下载文件:")).append(localfile).append(" 从sftp服务器:").append(remotefile)
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

	@Override
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
			if (sftpclient == null) {
				LOGGER.error("ftp服务器尚未连接,无法下载");
				return result;
			}
			try {
				for (int i = 0; i < remotefile.size(); i++) {
					downloadFile(localfile.get(i), remotefile.get(i));
				}
				result = true;
			} catch (Exception ex) {
				LOGGER.error("下载文件从sftp服务器部分或全部失败", ex);
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

	@Override
	public List<String> listFilesOnly() {
		try {
			List<String> ls = null;
			ls = new ArrayList<String>();
			Vector<LsEntry> vector = sftpclient.ls("./");
			if (vector == null)
				vector = new Vector<LsEntry>();
			for (LsEntry lsEntry : vector) {
				SftpATTRS attr = lsEntry.getAttrs();
				if (!attr.isDir()) {
					ls.add(lsEntry.getFilename());
				}
			}
			return ls;
		} catch (Exception ex) {
			LOGGER.error("列出sftp下当前文件失败", ex);
			return null;
		}
	}

	@Override
	public boolean fileExistCheck(String filename) {
		boolean result = false;
		try {
			Vector<LsEntry> vector = sftpclient.ls("./");
			if (vector == null)
				vector = new Vector<LsEntry>();
			for (LsEntry lsEntry : vector) {
				if (filename.equals(lsEntry.getFilename()))
					return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean dirsExistCheck(String dir) {
		boolean isDirExistFlag = false;
	    try {
	        SftpATTRS sftpATTRS = sftpclient.lstat(dir);
	        isDirExistFlag = true;
	        return sftpATTRS.isDir();
	    } catch (Exception e) {
	        if (e.getMessage().toLowerCase().equals("no such file")) {
	            isDirExistFlag = false;
	        }
	    }
	    return isDirExistFlag;
	}

	@Override
	public boolean rename(String oldname, String newname) {
		try {
			sftpclient.rename(oldname, newname);
			return true;
		} catch (Exception ex) {
			LOGGER.error("重命名文件失败", ex);
			return false;
		}
	}

	@Override
	public boolean remove(String filename) {
		try {
			sftpclient.rm(filename);
			return true;
		} catch (Exception ex) {
			LOGGER.error("删除文件失败", ex);
			return false;
		}
	}

	@Override
	public boolean removedir(String dirname) {
		try {
			this.sftpclient.rmdir(dirname);
			return true;
		} catch (Exception ex) {
			LOGGER.error("删除文件夹失败", ex);
			return false;
		}
	}

	@Override
	public boolean mkdir(String dirname) {
		try {
			this.sftpclient.mkdir(dirname);
			return true;
		} catch (Exception ex) {
			LOGGER.error("删除文件失败", ex);
			return false;
		}
	}

	@Override
	public boolean cd(String dirname) {
		try {
			this.sftpclient.cd(dirname);
			return true;
		} catch (Exception ex) {
			LOGGER.error("变更目录失败" + dirname, ex);
			return false;
		}
	}
	@Override
	public List<String> listFilesAndDirs() {
		try {
			List<String> ls = null;
			ls = new ArrayList<String>();
			Vector<LsEntry> vector = sftpclient.ls("./");
			if (vector == null)
				vector = new Vector<LsEntry>();
			for (LsEntry lsEntry : vector) {
				ls.add(lsEntry.getFilename());
			}
			return ls;
		} catch (Exception ex) {
			LOGGER.error("列出sftp下当前文件失败", ex);
			return null;
		}
	}

	@Override
	public Long getDirsSize() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
