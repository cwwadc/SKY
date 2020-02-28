package com.sky.core.service.api.transaction.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.base.context.spring.SpringContext;
import com.sky.core.service.api.callback.endpoint.ApiCallbackEndpoint;
import com.sky.core.service.api.callback.endpoint.ApiEndpointManager;
import com.sky.core.service.api.event.ApiEventManger;
import com.sky.core.service.api.event.mq.AsyncTimeoutReplyEvent;
import com.sky.core.service.api.route.ApiRouteAware;
import com.sky.core.service.api.route.ApiRouteConstants;
import com.sky.core.service.api.route.ApiRouteManager;
import com.sky.service.api.struct.context.SkyApiContext;
import com.sky.service.api.support.ApiContext;
import com.sky.service.api.support.ApiHandlerAdapter;
import com.sky.service.api.support.ApiHandlerMapping;

/**
 * 默认MQ应答交易处理实现
 *
 */
@Component
@ApiHandlerMapping(value = {
		"A10102",
		"A10202",
		"A10302",
		"A10402",
		"A10502",
		"C10102",
		"C10202",
		"C10302",
		"C10402",
		"C10502",
		"C10602"
})
public class DefaultMQReplyTransactionHandler extends ApiHandlerAdapter {
	
	@Autowired
	private ApiRouteManager ApiRouteManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void doHandle(ApiContext context) {
		SkyApiContext contextWrapper = (SkyApiContext)context;
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
	
	
}
