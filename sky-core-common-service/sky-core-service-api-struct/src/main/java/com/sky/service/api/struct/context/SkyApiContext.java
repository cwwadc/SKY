package com.sky.service.api.struct.context;

import com.sky.service.api.struct.domain.SkyMsg;

public class SkyApiContext extends FailRetryableApiContext<String> {
	private static final long serialVersionUID = 8066747922287762609L;
	private String requstMsgType;
	private String responseMsgType;
	private SkyMsg requestSkyMsg;
	private SkyMsg responseSkyMsg;
	private String requestPlainText;
	private String responsePlainText;
	private String recordablePlainText;
	private String msgId;
	private String refMsgId;
	private Throwable errorCause;
	
	public String getRequstMsgType() {
		return requstMsgType;
	}
	public void setRequstMsgType(String requstMsgType) {
		this.requstMsgType = requstMsgType;
	}
	public String getResponseMsgType() {
		return responseMsgType;
	}
	public void setResponseMsgType(String responseMsgType) {
		this.responseMsgType = responseMsgType;
	}
	public String getRequestPlainText() {
		return requestPlainText;
	}
	public void setRequestPlainText(String requestPlainText) {
		this.requestPlainText = requestPlainText;
	}
	public String getResponsePlainText() {
		return responsePlainText;
	}
	public void setResponsePlainText(String responsePlainText) {
		this.responsePlainText = responsePlainText;
	}
	public String getRecordablePlainText() {
		return recordablePlainText;
	}
	public void setRecordablePlainText(String recordablePlainText) {
		this.recordablePlainText = recordablePlainText;
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
	public Throwable getErrorCause() {
		return errorCause;
	}
	public void setErrorCause(Throwable errorCause) {
		this.errorCause = errorCause;
	}
	public SkyMsg getRequestSkyMsg() {
		return requestSkyMsg;
	}
	public void setRequestSkyMsg(SkyMsg requestSkyMsg) {
		this.requestSkyMsg = requestSkyMsg;
	}
	public SkyMsg getResponseSkyMsg() {
		return responseSkyMsg;
	}
	public void setResponseSkyMsg(SkyMsg responseSkyMsg) {
		this.responseSkyMsg = responseSkyMsg;
	}
}
