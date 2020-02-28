package com.sky.core.service.api.event.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.sky.base.context.spring.SpringContext;
import com.sky.core.service.api.callback.ApiCallbackCoupler;
import com.sky.core.service.api.callback.task.ApiCallbackTask;
import com.sky.core.service.api.serialize.ApiSerializer;
import com.sky.core.service.api.utils.SkyApiMsgUtils;
import com.sky.service.api.event.ApiEventListener;
import com.sky.service.api.struct.context.SkyApiContext;
import com.sky.service.api.struct.domain.SkyMsg;

/**
  * 默认HTTP-API接口事件监听器实现
 *
 */
@Component
public class DefaultHttpApiEventListener implements ApiEventListener {
	private static final Logger logger = LoggerFactory.getLogger(DefaultHttpApiEventListener.class);
	
	/**
	 * 转接Http交易错误事件
	 * @param event
	 */
	@Subscribe
	public void onHttpRelayError(HttpRelayErrorEvent event) {
		//预留事件，暂做空处理
	}
	
	/**
	 * 异步回调事件
	 * @param event
	 */
	@Subscribe
	@AllowConcurrentEvents
	public  void onCallback(CallbackEvent event) {
		logger.debug("on callback start");
		SkyApiContext context = (SkyApiContext) event.getEventContext();
		String msgType = SkyApiMsgUtils.unpackMsgType(context.getResponsePlainText());
		ApiSerializer<SkyMsg> apiSerializer = SpringContext.getBean("apiSerializer");
		Class<?> deserializeType = apiSerializer.lookupDeserializeType(msgType);
		SkyMsg callbackMsg = SkyApiMsgUtils.unpackMsg(context.getResponsePlainText(), deserializeType);
		String couplerId = callbackMsg.getHead().getRefMsgId();
		ApiCallbackCoupler apiCallbackCoupler = event.getApiCallbackCouplerManager().matchCallbackCoupler(couplerId);
		if(apiCallbackCoupler == null) {
			logger.error("callback has return but expired, coupler-id -> {}, callback-msg ->\n{}", couplerId, context.getResponsePlainText());
		}
		apiCallbackCoupler.getCallbackTask().onCallback(callbackMsg);
		logger.debug("on callback finish");
	}
	
	/**
	 * 异步回调等待超时事件
	 * @param event
	 */
	@Subscribe
	@AllowConcurrentEvents
	public void onCallbackTimeout(CallbackTimeoutEvent event) {
		logger.debug("on callback-timeout start");
		ApiCallbackTask callbackTask = event.getApiCallbackTask();
		if(callbackTask == null) {
			logger.debug("callback-timeout task did not set.");
		}
		callbackTask.onCallbackTimeout();
		logger.debug("on callback-timeout finish");
	}
	
	/**
	 * 输出响应失败事件
	 * @param event
	 */
	@Subscribe
	public void onResponseWriteFail(HttpResponseWriteFailEvent event) {
		//预留事件，暂做空处理
	}

}
