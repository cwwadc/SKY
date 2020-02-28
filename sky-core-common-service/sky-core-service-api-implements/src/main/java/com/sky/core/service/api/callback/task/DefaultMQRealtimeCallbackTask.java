package com.sky.core.service.api.callback.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.core.service.api.manage.ApiRelayManager;
import com.sky.core.service.api.route.ApiRouteAware;
import com.sky.core.service.api.route.ApiRouteManager;
import com.sky.core.service.api.serialize.ApiSerializer;
import com.sky.core.service.api.transaction.plugin.MessageRecordingPlugin;
import com.sky.core.service.api.utils.SkyApiMsgUtils;
import com.sky.service.api.struct.context.SkyApiContext;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.struct.pojo.SkyApiRoute;

public class DefaultMQRealtimeCallbackTask extends ApiCallbackTask {
	private static final Logger logger = LoggerFactory.getLogger(DefaultHttpApiCallbackTask.class);
	
	private ApiRelayManager<String, Object> apiRelayManager;
	private MessageRecordingPlugin recordingApiPlugin;
	private ApiSerializer<SkyMsg> apiSerializer;
	private ApiRouteManager apiRouteManager;	

	@SuppressWarnings({ "unchecked", "rawtypes"})
	@Override
	public void onCallback(Object callbackMsg) {
		// MQ-实时类交易回调处理逻辑：
		// 1、回调结果转型为字符串型（税局默认返回值类型），将返回字符串反序列化为系统支持的消息对象
		// 1.1、使用插件进行离线消息写库对象交易类型
		// 1.2、根据对象交易类型获取路由适配器并进行回应消息发送(使用不帶callback參數的commit方法，响应发送后不需要获取应答)
		String callbackMsgMetadata = String.valueOf(callbackMsg);
		String msgType = SkyApiMsgUtils.unpackMsgType(callbackMsgMetadata);
		Class<?> deserializeType = apiSerializer.lookupDeserializeType(msgType);
		SkyMsg callbackMsgWrapper = SkyApiMsgUtils.unpackMsg(callbackMsgMetadata, deserializeType);
		SkyApiContext context = new SkyApiContext();
		context.setRecordablePlainText(callbackMsgMetadata);
		context.setResponseMsgType(msgType);
		context.setResponseSkyMsg(callbackMsgWrapper);
		context.setRouteMsg(callbackMsgMetadata);
		recordingApiPlugin.doPluginCore(context);
		
		SkyApiRoute route = apiRouteManager.matchRoute(msgType);
		ApiRouteAware routeAware = apiRouteManager.matchRouteAware(route);
		apiRelayManager.commit(context, routeAware);
	}

	@Override
	public void onCallbackTimeout() {
		// MQ-实时类交易回调处理逻辑：
		// 1、默认不做处理
		
	}
	
	public DefaultMQRealtimeCallbackTask() {
		super();
	}

	public DefaultMQRealtimeCallbackTask(ApiRelayManager<String, Object> apiRelayManager,
			MessageRecordingPlugin recordingApiPlugin, 
			ApiSerializer<SkyMsg> apiSerializer,
			ApiRouteManager apiRouteManager) {
		this.apiRelayManager = apiRelayManager;
		this.recordingApiPlugin = recordingApiPlugin;
		this.apiSerializer = apiSerializer;
		this.apiRouteManager = apiRouteManager;
	}

	public ApiRelayManager<String, Object> getApiRelayManager() {
		return apiRelayManager;
	}

	public void setApiRelayManager(ApiRelayManager<String, Object> apiRelayManager) {
		this.apiRelayManager = apiRelayManager;
	}

	public MessageRecordingPlugin getRecordingApiPlugin() {
		return recordingApiPlugin;
	}

	public void setRecordingApiPlugin(MessageRecordingPlugin recordingApiPlugin) {
		this.recordingApiPlugin = recordingApiPlugin;
	}

	public ApiSerializer<SkyMsg> getApiSerializer() {
		return apiSerializer;
	}

	public void setApiSerializer(ApiSerializer<SkyMsg> apiSerializer) {
		this.apiSerializer = apiSerializer;
	}

	public ApiRouteManager getApiRouteManager() {
		return apiRouteManager;
	}

	public void setApiRouteManager(ApiRouteManager apiRouteManager) {
		this.apiRouteManager = apiRouteManager;
	}

	
	
}
