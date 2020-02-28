package com.sky.core.service.api.transaction.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sky.base.context.spring.SpringContext;
import com.sky.core.service.api.contants.ApiConstants;
import com.sky.core.service.api.manage.ApiRelayManager;
import com.sky.core.service.api.route.ApiRouteAware;
import com.sky.core.service.api.route.ApiRouteManager;
import com.sky.core.service.api.serialize.ApiSerializer;
import com.sky.core.service.api.transaction.plugin.MessageRecordingPlugin;
import com.sky.core.service.api.utils.SkyApiMsgUtils;
import com.sky.service.api.struct.context.SkyApiContext;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.struct.pojo.SkyApiRoute;
import com.sky.service.api.support.ApiContext;
import com.sky.service.api.support.ApiHandlerAdapter;
import com.sky.service.api.support.ApiHandlerMapping;

/**
 * 默认HTTP异步交易处理实现（如：签约预储划缴扣款批量信息）
 * 交易方向：ETS -> 征管系统
 *
 */
@Component
@ApiHandlerMapping(value = {"B10101"})
public class DefaultHttpAsyncTransactionHandler extends ApiHandlerAdapter {
	private static final Logger logger  = LoggerFactory.getLogger(DefaultHttpAsyncTransactionHandler.class);
	@Autowired
	private ApiRouteManager ApiRouteManager;
	@Autowired
	private ApiRelayManager<String, Object> apiRelayManager;
	@Value("${sky.api.trade.callback.wait-timeout}")
	private Integer callbackWaitTimeout;   //异步回调等待超时时间（秒）
	@SuppressWarnings("unchecked")
	@Override
	public void doHandle(ApiContext context) {
	    //HTTP-异步类交易处理逻辑：
		// 1、根据请求交易类型路由适配器转发交易，同时提交回调任务（ETS异步回调时将触发执行该任务对应的回调或回调超时方法）
		// 1.1、（废弃该逻辑，按1.2为准）目前异步交易的请求依赖于ETS的通用应答，收取到ETS通用应答之后才进行HTTP端的同步应答，所以此处回调任务使用与实时交易相同的DefaultHttpApiCallbackTask实现，若后续异步类交易处理逻辑有变化可实现自定义的Task
		// 1.2、异步交易处理逻辑调整为：收到征管系统请求后转发至ETS异步交易队列，发送成功后构建税库银通用应答同步写回；后续收取到ETS的通用应答回复后采用通知方式转发至征管系统
		SkyApiContext contextWrapper = (SkyApiContext)context;
		contextWrapper.setCouplerId(contextWrapper.getRequestSkyMsg().getHead().getMsgId());
		contextWrapper.setRouteMsg(contextWrapper.getRequestPlainText());
		SkyApiRoute route = ApiRouteManager.matchRoute(contextWrapper.getRequstMsgType());
		contextWrapper.setUseDataCompress(route.getUseDataCompress());
		ApiRouteAware<String, Object> routeAware = ApiRouteManager.matchRouteAware(route);
		//apiRelayManager.commit(contextWrapper, routeAware, new DefaultHttpApiCallbackTask(contextWrapper), callbackWaitTimeout);
		apiRelayManager.commit(contextWrapper, routeAware);
		doAcceptanceReply(contextWrapper);
	}
	
	@SuppressWarnings("unchecked")
	private void doAcceptanceReply(SkyApiContext requestContext) {
		ApiSerializer<SkyMsg> apiSerializer = SpringContext.getBean(ApiSerializer.class);
		MessageRecordingPlugin messageRecordingPlugin = SpringContext.getBean(MessageRecordingPlugin.class);
		
		SkyMsg acceptanceReply = SkyApiMsgUtils.buildCommonReplyMsg(requestContext.getRequestSkyMsg(), ApiConstants.STATUS_REQUEST_ACCEPTED_REPLY, "request accept success and relay completed");
		String acceptanceReplyText = apiSerializer.serialize(acceptanceReply);
		SkyApiContext recordingContext = new SkyApiContext();
		BeanUtils.copyProperties(requestContext, recordingContext);
		recordingContext.setRecordablePlainText(acceptanceReplyText);
		//记录超时应答
		messageRecordingPlugin.doPluginCore(recordingContext);
		//税库银通用应答写回
		requestContext.getReplyAdapter().adaptReply(acceptanceReplyText);
	}
	
}
