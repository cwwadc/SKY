package com.sky.core.service.api.callback.reactor;

/**
  *   回调接口定义
 *     callback：默认回调方法，responseMetadata：回调内容  
 *
 */
public interface ApiCallbackReactor {
	
	void callback(String responseMetadata);
}
