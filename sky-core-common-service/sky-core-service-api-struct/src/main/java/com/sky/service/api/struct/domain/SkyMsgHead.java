package com.sky.service.api.struct.domain;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Head")
public class SkyMsgHead implements Serializable{
	private static final long serialVersionUID = 3101522535839087292L;
	@XStreamAlias("Src")
	private String src;
	@XStreamAlias("Dest")
	private String dest;
	@XStreamAlias("OrigSrc")
	private String origSrc;
	@XStreamAlias("MsgType")
	private String msgType;
	@XStreamAlias("WorkDate")
	private String workDate;
	@XStreamAlias("MsgId")
	private String msgId;
	@XStreamAlias("RefMsgType")
	private String refMsgType;
	@XStreamAlias("RefMsgId")
	private String refMsgId;
	@XStreamAlias("SendDateTime")
	private String sendDateTime;
	@XStreamAlias("MacFlag")
	private String macFlag;
	@XStreamAlias("RetCode")
	private String retCode;
	@XStreamAlias("RetMsg")
	private String retMsg;
	
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public String getOrigSrc() {
		return origSrc;
	}
	public void setOrigSrc(String origSrc) {
		this.origSrc = origSrc;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getRefMsgType() {
		return refMsgType;
	}
	public void setRefMsgType(String refMsgType) {
		this.refMsgType = refMsgType;
	}
	public String getRefMsgId() {
		return refMsgId;
	}
	public void setRefMsgId(String refMsgId) {
		this.refMsgId = refMsgId;
	}
	public String getSendDateTime() {
		return sendDateTime;
	}
	public void setSendDateTime(String sendDateTime) {
		this.sendDateTime = sendDateTime;
	}
	public String getMacFlag() {
		return macFlag;
	}
	public void setMacFlag(String macFlag) {
		this.macFlag = macFlag;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	
	
}
