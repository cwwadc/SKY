package com.sky.service.api.support;

public interface ApiDispatcher {
	public ApiContext doDispatch(String requestMetaData, ApiReplyChannelAdapter replyChannelAdapter);
}
