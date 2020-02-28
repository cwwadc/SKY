package com.sky.core.service.api.event.http;

import com.sky.service.api.event.ContextualApiEvent;
import com.sky.service.api.support.ApiReplyChannelAdapter;

/**
  * 回调等待超时事件定义
 *
 */
public class CallbackWaitTimeoutEvent extends ContextualApiEvent {
	private ApiReplyChannelAdapter replyAdapter;

	public ApiReplyChannelAdapter getReplyAdapter() {
		return replyAdapter;
	}

	public void setReplyAdapter(ApiReplyChannelAdapter replyAdapter) {
		this.replyAdapter = replyAdapter;
	}

}
