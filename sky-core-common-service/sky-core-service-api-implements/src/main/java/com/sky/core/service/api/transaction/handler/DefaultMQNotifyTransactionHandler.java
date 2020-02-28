package com.sky.core.service.api.transaction.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.base.context.spring.SpringContext;
import com.sky.base.lang.time.DateTimeUtils;
import com.sky.core.service.api.callback.endpoint.ApiCallbackEndpoint;
import com.sky.core.service.api.callback.endpoint.ApiEndpointManager;
import com.sky.core.service.api.event.ApiEventManger;
import com.sky.core.service.api.event.mq.AsyncTimeoutReplyEvent;
import com.sky.core.service.api.manage.ApiRelayManager;
import com.sky.core.service.api.route.ApiFailRetryManager;
import com.sky.core.service.api.route.ApiRouteAware;
import com.sky.core.service.api.route.ApiRouteConstants;
import com.sky.core.service.api.route.ApiRouteManager;
import com.sky.core.service.api.utils.SkyApiMsgUtils;
import com.sky.service.api.struct.context.SkyApiContext;
import com.sky.service.api.struct.pojo.SkyApiRoute;
import com.sky.service.api.struct.pojo.SkyApiRouteGroup;
import com.sky.service.api.support.ApiContext;
import com.sky.service.api.support.ApiHandlerAdapter;
import com.sky.service.api.support.ApiHandlerMapping;

/**
 * 默认MQ通知交易处理实现(如：签约预储划缴扣款批量信息响应)
 * 交易方向：ETS -> 征管系统
 *
 */
@Component
@ApiHandlerMapping(value = {"B10102","T10101","B10501","B10601"})
public class DefaultMQNotifyTransactionHandler extends ApiHandlerAdapter {

	@Autowired
	private ApiRouteManager ApiRouteManager;
	@Autowired
	private ApiRelayManager<String, Object> apiRelayManager;
	@Autowired
	private ApiFailRetryManager apiFailRetryManager;
	
	@Override
	public void doHandle(ApiContext context) {
		// MQ-通知类交易回调处理逻辑：
		// 1、取得请求内容中的消息类型，根据报文头返回码判断是通用应答请求
		// 1.1、若是通用应答请求则进入主动通知处理流程
		// 1.2、若非通用应答请求则均视为异步交易结果，进入路由转发处理流程
		SkyApiContext contextWrapper = (SkyApiContext) context;
		if(SkyApiMsgUtils.isCommonReplyContext(contextWrapper.getRequestSkyMsg())) {
			invokeReplyNotify(contextWrapper);
		} else {
			invokeResultNotify(contextWrapper);
		}
	}

	/** 异步交易结果通知处理
	 * @param contextWrapper
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void invokeResultNotify(SkyApiContext contextWrapper) {
		SkyApiRoute route = ApiRouteManager.matchRoute(contextWrapper.getRequstMsgType());
		SkyApiRouteGroup group = ApiRouteManager.matchRouteGroup(route);
		ApiRouteAware routeAware = ApiRouteManager.matchRouteAware(route);
		contextWrapper.setRouteMsg(contextWrapper.getRequestPlainText());
		contextWrapper.setUseDataCompress(route.getUseDataCompress());
		
		contextWrapper.setFailRetryAvaiable(group.getFailRetryAvaiable());
		contextWrapper.setFailRetryInterval(group.getFailRetryInterval());
		contextWrapper.setFailRetryTimeLimit(group.getFailRetryTimeLimit());
		
		try {
			apiRelayManager.commit(contextWrapper, routeAware);
			logger.debug("invoke mq-result-reply notify finish, msg-id -> {}", contextWrapper.getMsgId());
		} catch (Exception e) {
			logger.error("invoke mq-result-reply notify error, msg-id -> " + contextWrapper.getMsgId(), e);
			
			if(ApiRouteConstants.ROUTE_GROUP_FAILRETRY_AVAIABLE.equals(contextWrapper.getFailRetryAvaiable())){
				apiFailRetryManager.prepareRetryApiContext(contextWrapper);
				apiFailRetryManager.registerFailRetryTask(contextWrapper, routeAware);
				logger.info("register api-notify-retry task, msg-id -> {}, trace-id -> {}, next time to run -> {}.", 
						contextWrapper.getMsgId(), contextWrapper.getTraceId(), DateTimeUtils.format(contextWrapper.getNextTime(), "yyyy-MM-dd HH:mm:ss"));
			}
		}
	}

	/** 通用应答通知处理(异步转同步回调模式)
	 * @param contextWrapper
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private void invokeReplyCallback(SkyApiContext contextWrapper) {
		//通过应答消息的关联键(报文头中的refMsgId)查询是否存在回调缓存
		ApiEndpointManager apiEndpointManager = SpringContext.getBean(ApiEndpointManager.class);
		ApiEventManger apiEventManger = SpringContext.getBean(ApiEventManger.class);
		ApiCallbackEndpoint callbackEndpoint = apiEndpointManager.unRegisterCallbackEndpoint(contextWrapper.getRequestSkyMsg().getHead().getRefMsgId());
		//若不存在回调缓存，即取得MQ交易响应时前端交易已超时，触发超时响应事件
		if(callbackEndpoint == null) {
			AsyncTimeoutReplyEvent asyncTimeoutReplyEvent = new AsyncTimeoutReplyEvent();
			asyncTimeoutReplyEvent.setEventContext(contextWrapper);
			apiEventManger.postEvent(asyncTimeoutReplyEvent);
			logger.debug("fire timeout-mq-reply event, msg-id -> {}", contextWrapper.getMsgId());
		} else {
		//若存在回调缓存，则根据取得的回调端点进行路由将响应报文发送至调用端
			ApiRouteAware routeAware = ApiRouteManager.matchCallbackRouteAware(callbackEndpoint);
			routeAware.deliver(contextWrapper.getRequestPlainText(), ApiRouteConstants.ROUTE_UNUSE_DATA_COMPRESS);
			logger.debug("callback mq-reply finish, msg-id -> {}", contextWrapper.getMsgId());
		}
	}
	
	/** 通用应答通知处理（异步主动通知模式）
	 * @param contextWrapper
	 */
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	private void invokeReplyNotify(SkyApiContext contextWrapper) {
//		ApiRouteAware routeAware = SpringContext.getBean("asyncNotifyAware");
		SkyApiRoute route = ApiRouteManager.matchRoute(contextWrapper.getRequstMsgType());
		SkyApiRouteGroup group = ApiRouteManager.matchRouteGroup(route);
		ApiRouteAware routeAware = ApiRouteManager.matchRouteAware(route);
		contextWrapper.setRouteMsg(contextWrapper.getRequestPlainText());
		contextWrapper.setUseDataCompress(route.getUseDataCompress());
		
		contextWrapper.setFailRetryAvaiable(group.getFailRetryAvaiable());
		contextWrapper.setFailRetryInterval(group.getFailRetryInterval());
		contextWrapper.setFailRetryTimeLimit(group.getFailRetryTimeLimit());
		
		try {
			routeAware.deliver(contextWrapper.getRequestPlainText(), ApiRouteConstants.ROUTE_UNUSE_DATA_COMPRESS);
			logger.debug("invoke mq-common-reply notify finish, msg-id -> {}", contextWrapper.getMsgId());
		} catch (Exception e) {
			logger.error("invoke mq-common-reply notify error, msg-id -> {}" + contextWrapper.getMsgId(), e);
			
			if(ApiRouteConstants.ROUTE_GROUP_FAILRETRY_AVAIABLE.equals(contextWrapper.getFailRetryAvaiable())) {
				apiFailRetryManager.prepareRetryApiContext(contextWrapper);
				apiFailRetryManager.registerFailRetryTask(contextWrapper, routeAware);
				logger.info("register api-notify-retry task, msg-id -> {}, trace-id -> {}, next time to run -> {}.", 
						contextWrapper.getMsgId(), contextWrapper.getTraceId(), DateTimeUtils.format(contextWrapper.getNextTime(), "yyyy-MM-dd HH:mm:ss"));
			}
		}
		
	}
	

}
