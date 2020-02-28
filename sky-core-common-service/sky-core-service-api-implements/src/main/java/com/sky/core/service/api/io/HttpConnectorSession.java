package com.sky.core.service.api.io;

import java.io.Serializable;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.lang.sequence.UUID;
import com.sky.service.api.support.ApiDispatcher;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpUtil;

public class HttpConnectorSession implements Serializable {
	
	private static final long serialVersionUID = 420265252067767438L;
	private static final Logger logger = LoggerFactory.getLogger(HttpConnectorSession.class);
	private static final Charset SESSION_CHARSET =  Charset.forName("GBK");
	private String sessionId;
	private Channel channel;
	private ApiDispatcher dispatcher;

	public void handleRequest(FullHttpRequest msg) {
		String requestMetaData = StringUtils.toEncodedString(ByteBufUtil.getBytes(msg.content()), SESSION_CHARSET);
		boolean keepAlive = HttpUtil.isKeepAlive(msg);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Request uri -> [{}], content -> [{}]", msg.uri(), requestMetaData);
		}
		HttpApiReplyChannelAdapter replyAdapter = new HttpApiReplyChannelAdapter(this, keepAlive);
		dispatcher.doDispatch(requestMetaData, replyAdapter);
	}
	
	public HttpConnectorSession() {
		super();
		this.sessionId = UUID.toString(UUID.random());
	}

	public HttpConnectorSession(Channel channel, ApiDispatcher dispatcher) {
		super();
		this.sessionId = UUID.toString(UUID.random());
		this.channel = channel;
		this.dispatcher = dispatcher;
	}

	public String getSessionId() {
		return sessionId;
	}


	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public ApiDispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(ApiDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	
	
	
}
