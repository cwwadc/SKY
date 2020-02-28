package com.sky.service.api.struct.pojo;

import java.sql.Timestamp;

import com.sky.base.jdbc.spring.annotation.Table;

/**
 * 征收机关/商业银行实时交易信息实体
 *
 */
@Table(name = "SKY_TRANSACTION_ONLINE", primary = {"ID"})
public class SkyTransactionOnline {
	private Long id;
	private String msgType;
	private String msgId;
	private String refMsgId;
	private String relayError;
	private Timestamp createTime;
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
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getRefMsgId() {
		return refMsgId;
	}
	public void setRefMsgId(String refMsgId) {
		this.refMsgId = refMsgId;
	}
	public String getRelayError() {
		return relayError;
	}
	public void setRelayError(String relayError) {
		this.relayError = relayError;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	
	
}
