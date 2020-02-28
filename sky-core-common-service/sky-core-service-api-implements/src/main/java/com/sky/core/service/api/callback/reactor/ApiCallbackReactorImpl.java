package com.sky.core.service.api.callback.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.base.context.spring.SpringContext;
import com.sky.core.service.api.callback.ApiCallbackCouplerManager;
import com.sky.core.service.api.event.ApiEventManger;
import com.sky.core.service.api.event.http.CallbackEvent;
import com.sky.service.api.struct.context.SkyApiContext;


/**
  * 回调接口默认实现 
 *
 */
@Component
public class ApiCallbackReactorImpl implements ApiCallbackReactor {
	private static final Logger logger = LoggerFactory.getLogger(ApiCallbackReactorImpl.class);
	@Autowired
	private ApiEventManger apiEventManger;

	@Override
	public void callback(String responseMetadata) {
		//回调请求逻辑：异步触发本地回调事件，仅做分发，前端使用rmi协议暴露服务时保证客户端并发调用的性能
		SkyApiContext context = new SkyApiContext();
		context.setResponsePlainText(responseMetadata);
		CallbackEvent callbackEvent = new CallbackEvent();
		callbackEvent.setEventContext(context);
		callbackEvent.setApiCallbackCouplerManager(SpringContext.getBean(ApiCallbackCouplerManager.class));
		callbackEvent.setResponseMetadata(responseMetadata);
		apiEventManger.postEvent(callbackEvent);
		
		
	}

}
