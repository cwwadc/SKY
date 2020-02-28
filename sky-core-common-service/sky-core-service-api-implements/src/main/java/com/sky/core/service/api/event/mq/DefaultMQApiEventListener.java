package com.sky.core.service.api.event.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.sky.base.lang.time.DateTimeUtils;
import com.sky.core.service.api.route.ApiFailRetryManager;
import com.sky.core.service.api.route.ApiRouteConstants;
import com.sky.core.service.api.route.WSRouteAware;
import com.sky.service.api.event.ApiEventListener;
import com.sky.service.api.struct.context.SkyApiContext;

/**
  * 默认MQ-API事件监听器
 *
 */
@Component
public class DefaultMQApiEventListener implements ApiEventListener {
	private static final Logger logger = LoggerFactory.getLogger(DefaultMQApiEventListener.class);
	
	@Autowired
	private ApiFailRetryManager apiFailRetryManager;
	@Autowired
	@Qualifier("asyncNotifyAware")
	private WSRouteAware asyncNotifyAware;
	@Value("${sky.api.binding.ws.notify.retry.avaiable}")    //默认接口异步通知是否启用失败重试机制 0-启用 1-不启用
	private String asyncNotifyFailRetryAvaiable; 
	@Value("${sky.api.binding.ws.notify.retry.interval}")    //默认接口异步通知失败重试间隔，单位：分钟
	private Long asyncNotifyFailRetryInterval;
	@Value("${sky.api.binding.ws.notify.retry.timelimit}")   //默认接口异步通知失败重试期限，单位：分钟，即首次通知失败后在多少分钟期限内执行重试机制
	private Long asyncNotifyFailRetryTimeLimit;
	
	/**
	 * 转接MQ交易错误事件
	 * @param event
	 */
	@Subscribe
	public void onMQRelayError(MQRelayErrorEvent event) {
		//预留事件，暂做空处理
	}
	
	/**
	 * 异步响应事件
	 * @param event
	 */
	@Subscribe
	public void onAsyncReply(AsyncReplyEvent event) {
		//预留事件，暂做空处理
	}
	
	/**
	 * 异步超时响应事件
	 * @param event
	 */
	@Subscribe
	@AllowConcurrentEvents
	public void onAsyncTimeoutReply(AsyncTimeoutReplyEvent event) {
		//根据业务约定异步超时响应交易需做主动通知补偿
		SkyApiContext context = (SkyApiContext) event.getEventContext();
		context.setRouteMsg(context.getRequestPlainText());
		context.setUseDataCompress(ApiRouteConstants.ROUTE_UNUSE_DATA_COMPRESS);
		context.setFailRetryAvaiable(asyncNotifyFailRetryAvaiable);
		context.setFailRetryInterval(asyncNotifyFailRetryInterval);
		context.setFailRetryTimeLimit(asyncNotifyFailRetryTimeLimit);
		try {
			asyncNotifyAware.deliver(context.getRouteMsg(), context.getUseDataCompress());
			logger.debug("async-timeout-reply notify finish, msg-id -> {}", context.getMsgId());
		} catch (Exception e) {
			logger.error("async-timeout-reply notify error, msg-id -> " + context.getMsgId(), e);
			
			if(ApiRouteConstants.ROUTE_GROUP_FAILRETRY_AVAIABLE.equals(context.getFailRetryAvaiable())){
				apiFailRetryManager.prepareRetryApiContext(context);
				apiFailRetryManager.registerFailRetryTask(context, asyncNotifyAware);
				logger.info("register api-notify-retry task, msg-id -> {}, trace-id -> {}, next time to run -> {}.",
						context.getMsgId(), context.getTraceId(), DateTimeUtils.format(context.getNextTime(), "yyyy-MM-dd HH:mm:ss"));
			}
		}
	}
	
	/**
	 * 回调调用失败事件
	 * @param event
	 */
	@Subscribe
	public void onCallbackInvokeFail(CallbackInvokeFailEvent event) {
		//预留事件，暂做空处理
	}
}
