package com.sky.service.api.struct.pojo;

import java.io.Serializable;

import com.sky.base.jdbc.spring.annotation.Table;

@Table(name = "SKY_MQ_CHNL_CFG", primary = {"ID"})
public class SkyMQChnlCfg implements Serializable {
	private static final long serialVersionUID = 5343017236944126860L;
	
	private Long id;
	private String configName;
	private Integer threadNum;
	private String qType;  //IBMMQ
	private String bindingUri;  //username:password@host:port:qmgr:ccsid:channel:queue
	private Long recoveryInterval;  //断线重连间隔
	private Long receiveTimeout; //接收超时
	private String remark;
	private String useDataCompress; //是否启用数据压缩发送：1-启用数据压缩发送 0-不启用数据压缩发送
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public Integer getThreadNum() {
		return threadNum;
	}
	public void setThreadNum(Integer threadNum) {
		this.threadNum = threadNum;
	}
	public String getqType() {
		return qType;
	}
	public void setqType(String qType) {
		this.qType = qType;
	}
	public String getBindingUri() {
		return bindingUri;
	}
	public void setBindingUri(String bindingUri) {
		this.bindingUri = bindingUri;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getRecoveryInterval() {
		return recoveryInterval;
	}
	public void setRecoveryInterval(Long recoveryInterval) {
		this.recoveryInterval = recoveryInterval;
	}
	public Long getReceiveTimeout() {
		return receiveTimeout;
	}
	public void setReceiveTimeout(Long receiveTimeout) {
		this.receiveTimeout = receiveTimeout;
	}
	public String getUseDataCompress() {
		return useDataCompress;
	}
	public void setUseDataCompress(String useDataCompress) {
		this.useDataCompress = useDataCompress;
	}
}
