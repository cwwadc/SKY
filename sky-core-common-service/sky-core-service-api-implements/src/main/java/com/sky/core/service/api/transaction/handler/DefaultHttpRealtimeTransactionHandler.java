package com.sky.core.service.api.transaction.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


import com.sky.core.service.api.callback.task.DefaultHttpApiCallbackTask;
import com.sky.core.service.api.manage.ApiRelayManager;
import com.sky.core.service.api.route.ApiRouteAware;
import com.sky.core.service.api.route.ApiRouteManager;
import com.sky.service.api.struct.context.SkyApiContext;
import com.sky.service.api.struct.pojo.SkyApiRoute;
import com.sky.service.api.support.ApiContext;
import com.sky.service.api.support.ApiHandlerAdapter;
import com.sky.service.api.support.ApiHandlerMapping;

/**
 * 默认HTTP实时交易处理实现（如：签约预储划缴扣款）
 * 交易方向：征管系统 -> ETS
 *
 */
@Component
@ApiHandlerMapping(value = {
		"A10101",
		"A10201",
		"A10301",
		"A10401",
		"A10501",
		"C10101",
		"C10201",
		"C10301",
		"C10401",
		"C10501",
		"C10601"
		})
@Configuration
public class DefaultHttpRealtimeTransactionHandler extends ApiHandlerAdapter {
	private static final Logger logger  = LoggerFactory.getLogger(DefaultHttpRealtimeTransactionHandler.class);

	@Autowired
	private ApiRouteManager ApiRouteManager;
	@Autowired
	private ApiRelayManager<String, Object> apiRelayManager;
	@Value("${sky.api.trade.callback.wait-timeout}")
	private Integer callbackWaitTimeout;   //异步回调等待超时时间（秒）

	@SuppressWarnings("unchecked")
	@Override
	public void doHandle(ApiContext context) {
		SkyApiContext contextWrapper = (SkyApiContext)context;
		//调用路由组件方法获取路由和发送适配器
		contextWrapper.setCouplerId(contextWrapper.getRequestSkyMsg().getHead().getMsgId());
		contextWrapper.setRouteMsg(contextWrapper.getRequestPlainText());
		SkyApiRoute route = ApiRouteManager.matchRoute(contextWrapper.getRequstMsgType());
		contextWrapper.setUseDataCompress(route.getUseDataCompress());
		ApiRouteAware<String, Object> routeAware = ApiRouteManager.matchRouteAware(route);
		apiRelayManager.commit(contextWrapper, routeAware, new DefaultHttpApiCallbackTask(contextWrapper), callbackWaitTimeout);
	}
	
	
	
}
