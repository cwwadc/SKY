package com.sky.service.api.struct.domain;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Message")
public class SkyMsg implements Serializable{
	private static final long serialVersionUID = 8290270615764629121L;

	@XStreamAlias("Head")
	private SkyMsgHead head;
	@XStreamAlias("Body")
	private List<?> body;
	
	public SkyMsgHead getHead() {
		return head;
	}
	public void setHead(SkyMsgHead head) {
		this.head = head;
	}
	public List<?> getBody() {
		return body;
	}
	public void setBody(List<?> body) {
		this.body = body;
	}
	
	
	
	
}
