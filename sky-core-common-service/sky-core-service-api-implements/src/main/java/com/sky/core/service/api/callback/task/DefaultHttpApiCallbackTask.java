package com.sky.core.service.api.callback.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.sky.base.context.spring.SpringContext;
import com.sky.core.service.api.contants.ApiConstants;
import com.sky.core.service.api.serialize.ApiSerializer;
import com.sky.core.service.api.transaction.plugin.MessageRecordingPlugin;
import com.sky.core.service.api.utils.SkyApiMsgUtils;
import com.sky.service.api.struct.context.SkyApiContext;
import com.sky.service.api.struct.domain.SkyMsg;

public class DefaultHttpApiCallbackTask extends ApiCallbackTask {
	private static final Logger logger = LoggerFactory.getLogger(DefaultHttpApiCallbackTask.class);
	
	private SkyApiContext apiContext;
	
	public DefaultHttpApiCallbackTask() {
		super();
	}

	public DefaultHttpApiCallbackTask(SkyApiContext apiContext) {
		super();
		this.apiContext = apiContext;
	}

	@Override
	public void onCallback(Object callbackMsg) {
		//正常异步回调处理逻辑
		SkyMsg responseMsg = (SkyMsg)callbackMsg;
		this.apiContext.getReplyAdapter().adaptReply(SkyApiMsgUtils.toPlainXml(responseMsg));  //正常响应写回
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCallbackTimeout() {
		//异步回调超时处理逻辑
		ApiSerializer<SkyMsg> apiSerializer = SpringContext.getBean(ApiSerializer.class);
		MessageRecordingPlugin messageRecordingPlugin = SpringContext.getBean(MessageRecordingPlugin.class);
		
		SkyMsg timeoutReply = SkyApiMsgUtils.buildReplyMsg(this.apiContext.getRequestSkyMsg(), ApiConstants.STATUS_FEEDBACK_TIMEOUT, "人行响应超时");
		String timeoutReplyText = apiSerializer.serialize(timeoutReply);
		SkyApiContext recordingContext = new SkyApiContext();
		BeanUtils.copyProperties(this.apiContext, recordingContext);
		recordingContext.setRecordablePlainText(timeoutReplyText);
		//记录超时应答
		messageRecordingPlugin.doPluginCore(recordingContext);
		//超时响应写回
		this.apiContext.getReplyAdapter().adaptReply(timeoutReplyText);
	}
	
}
