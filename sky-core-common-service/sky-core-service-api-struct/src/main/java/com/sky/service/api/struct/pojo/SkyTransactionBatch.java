package com.sky.service.api.struct.pojo;

import java.sql.Timestamp;

import com.sky.base.jdbc.spring.annotation.Table;

/**
 * 征收机关批量交易信息实体
 *
 */
@Table(name = "SKY_TRANSACTION_BATCH", primary = {"ID"})
public class SkyTransactionBatch {
	private Long id;
	private String refBatchId;
	private String msgType;
	private String headMetadata;
	private String batchInfoMetadata;
	private String relayError;
	private Timestamp createTime;
	private String reserved;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRefBatchId() {
		return refBatchId;
	}
	public void setRefBatchId(String refBatchId) {
		this.refBatchId = refBatchId;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getHeadMetadata() {
		return headMetadata;
	}
	public void setHeadMetadata(String headMetadata) {
		this.headMetadata = headMetadata;
	}
	public String getBatchInfoMetadata() {
		return batchInfoMetadata;
	}
	public void setBatchInfoMetadata(String batchInfoMetadata) {
		this.batchInfoMetadata = batchInfoMetadata;
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
