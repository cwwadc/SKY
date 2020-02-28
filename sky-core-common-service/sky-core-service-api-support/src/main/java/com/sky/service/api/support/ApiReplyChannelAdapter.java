package com.sky.service.api.support;

import java.util.concurrent.Future;

/**
  * 应答标记接口
 *
 */
public interface ApiReplyChannelAdapter {
	
	Future<?> adaptReply(String responseMetaData);
}
