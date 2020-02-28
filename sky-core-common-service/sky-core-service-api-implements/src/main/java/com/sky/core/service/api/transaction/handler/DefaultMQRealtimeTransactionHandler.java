package com.sky.core.service.api.transaction.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.base.context.spring.SpringContext;
import com.sky.core.service.api.callback.task.DefaultMQRealtimeCallbackTask;
import com.sky.core.service.api.manage.ApiRelayManager;
import com.sky.core.service.api.route.ApiRouteAware;
import com.sky.core.service.api.route.ApiRouteManager;
import com.sky.service.api.struct.context.SkyApiContext;
import com.sky.service.api.struct.pojo.SkyApiRoute;
import com.sky.service.api.support.ApiContext;
import com.sky.service.api.support.ApiHandlerAdapter;
import com.sky.service.api.support.ApiHandlerMapping;

/**
 * 默认MQ实时交易处理实现(如：查询虚拟户/个人应缴信息回应)
 * 交易方向：ETS -> 征管系统
 *
 */
@Component
@ApiHandlerMapping(value = {"A20101","A20201","A20301","A20401","A20501"})
public class DefaultMQRealtimeTransactionHandler extends ApiHandlerAdapter {

	@Autowired
	private ApiRouteManager ApiRouteManager;
	@Autowired
	private ApiRelayManager<String, Object> apiRelayManager;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void doHandle(ApiContext context) {
		//MQ-实时类交易处理逻辑：
		// 1、根据请求交易类型获取路由适配器转发交易，同时提交回调任务（使用自定义任务DefaultMQRealtimeCallbackTask，征管系统返回时将触发执行该任务对应的回调或回调超时方法，具体逻辑见该类onCallback或onCallbackTimeout）
		SkyApiContext contextWrapper = (SkyApiContext)context;
		SkyApiRoute reqroute = ApiRouteManager.matchRoute(contextWrapper.getRequstMsgType());
		contextWrapper.setRouteMsg(contextWrapper.getRequestPlainText());
		contextWrapper.setUseDataCompress(reqroute.getUseDataCompress());
		ApiRouteAware<String, Object> reqrouteAware = ApiRouteManager.matchRouteAware(reqroute);
		apiRelayManager.commit(contextWrapper, reqrouteAware, newMQRealtimeCallbackTask(), 0);
		logger.debug("deliver transaction finish, trace-id -> {}", contextWrapper.getMsgId());
	}
	
	@SuppressWarnings("unchecked")
	private DefaultMQRealtimeCallbackTask newMQRealtimeCallbackTask() {
		DefaultMQRealtimeCallbackTask task = new DefaultMQRealtimeCallbackTask();
		task.setApiRouteManager(SpringContext.getBean(ApiRouteManager.class));
		task.setApiRelayManager(SpringContext.getBean(ApiRelayManager.class));
		task.setRecordingApiPlugin(SpringContext.getBean("messageRecordingPlugin"));
		task.setApiSerializer(SpringContext.getBean("apiSerializer"));
		
		return task;
	}

}
