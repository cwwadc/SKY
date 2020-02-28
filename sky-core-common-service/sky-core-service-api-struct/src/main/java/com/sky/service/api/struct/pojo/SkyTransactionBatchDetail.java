package com.sky.service.api.struct.pojo;

import java.sql.Timestamp;

import com.sky.base.jdbc.spring.annotation.Table;

/**
 * 征收机关批量交易明细实体
 *
 */
@Table(name = "SKY_TRANSACTION_BATCH_DETAIL", primary = {"ID"})
public class SkyTransactionBatchDetail {
	private Long id;
	private String batchId;
	private String msgId;
	private String refMsgId;
	private String msgMetadata;
	private Timestamp createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
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
	public String getMsgMetadata() {
		return msgMetadata;
	}
	public void setMsgMetadata(String msgMetadata) {
		this.msgMetadata = msgMetadata;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
}
