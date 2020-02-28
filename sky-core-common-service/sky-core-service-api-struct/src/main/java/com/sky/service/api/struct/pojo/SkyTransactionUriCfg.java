package com.sky.service.api.struct.pojo;

import java.sql.Timestamp;

import com.sky.base.jdbc.spring.annotation.Table;

/**
 * 系统交易转接配置实体
 *
 */
@Table(name = "SKY_TRANSACTION_URI_CFG", primary = {"ID"})
public class SkyTransactionUriCfg {
	private Long id;
	private String msgType;
	private String msgGroup;
	private String inputProtocal;
	private String intputUri;
	private String relayProtocal;
	private String relayUri;
	private String serializableClass;
	private String asyncFeedback;
	private String msgAvaiable;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Timestamp avaiableTime;
	private String reserved;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgGroup() {
		return msgGroup;
	}
	public void setMsgGroup(String msgGroup) {
		this.msgGroup = msgGroup;
	}
	public String getInputProtocal() {
		return inputProtocal;
	}
	public void setInputProtocal(String inputProtocal) {
		this.inputProtocal = inputProtocal;
	}
	public String getIntputUri() {
		return intputUri;
	}
	public void setIntputUri(String intputUri) {
		this.intputUri = intputUri;
	}
	public String getRelayProtocal() {
		return relayProtocal;
	}
	public void setRelayProtocal(String relayProtocal) {
		this.relayProtocal = relayProtocal;
	}
	public String getRelayUri() {
		return relayUri;
	}
	public void setRelayUri(String relayUri) {
		this.relayUri = relayUri;
	}
	public String getSerializableClass() {
		return serializableClass;
	}
	public void setSerializableClass(String serializableClass) {
		this.serializableClass = serializableClass;
	}
	public String getAsyncFeedback() {
		return asyncFeedback;
	}
	public void setAsyncFeedback(String asyncFeedback) {
		this.asyncFeedback = asyncFeedback;
	}
	public String getMsgAvaiable() {
		return msgAvaiable;
	}
	public void setMsgAvaiable(String msgAvaiable) {
		this.msgAvaiable = msgAvaiable;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Timestamp getAvaiableTime() {
		return avaiableTime;
	}
	public void setAvaiableTime(Timestamp avaiableTime) {
		this.avaiableTime = avaiableTime;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	
}
