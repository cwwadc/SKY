package com.sky.core.service.api.transaction.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.sky.base.context.spring.SpringContext;
import com.sky.core.service.api.contants.ApiConstants;
import com.sky.core.service.api.serialize.ApiSerializer;
import com.sky.core.service.api.transaction.plugin.MessageRecordingPlugin;
import com.sky.core.service.api.utils.SkyApiMsgUtils;
import com.sky.service.api.struct.context.SkyApiContext;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.struct.domain.SkyMsgHead;
import com.sky.service.api.support.ApiContext;
import com.sky.service.api.support.ApiHandlerAdapter;
import com.sky.service.api.support.ApiHandlerMapping;

/**
  *    默认交易异常处理实现
 *
 */
@Component
@ApiHandlerMapping(errorHandler=true)
public class DefaultTransactionErrorHandler extends ApiHandlerAdapter {

	@SuppressWarnings("unchecked")
	@Override
	public void doHandle(ApiContext context) {
		if(context == null) {
			throw new NullPointerException("transaction error context cannot be null");
		}
		if(!(context instanceof SkyApiContext)) {
			throw new RuntimeException("transaction error context must be an SkyApiContext instance");
		}
		SkyApiContext contextWrapper = (SkyApiContext)context;
		if(contextWrapper.getErrorCause() == null) {
			return;
		}
		try {
			this.logger.debug("on default-api-error-handler, trace-id -> {}", contextWrapper.getTraceId());
			if(contextWrapper.getReplyAdapter() != null) {
				SkyMsg requestMsg = getRequestMsg(contextWrapper);
				String statusText = contextWrapper.getErrorCause().getMessage();
				ApiSerializer<SkyMsg> apiSerializer = SpringContext.getBean(ApiSerializer.class);
				MessageRecordingPlugin messageRecordingPlugin = SpringContext.getBean(MessageRecordingPlugin.class);
				SkyMsg errorReply = SkyApiMsgUtils.buildReplyMsg(requestMsg, ApiConstants.STATUS_SYSTEM_EXCEPTION, StringUtils.isEmpty(statusText) ? "internal error" : statusText);
				String errorReplyText = apiSerializer.serialize(errorReply);
				
				//记录异常应答
				SkyApiContext recordingContext = new SkyApiContext();
				BeanUtils.copyProperties(context, recordingContext);
				recordingContext.setRecordablePlainText(errorReplyText);
				messageRecordingPlugin.doPluginCore(recordingContext);
				//异常响应写回
				contextWrapper.getReplyAdapter().adaptReply(errorReplyText);
			} else {
				this.logger.error("reply-adapter is null or unavaiable");
			}
		} catch (Exception e) {
			this.logger.error("api-error handle exception", e);
		}
	}
	
	private SkyMsg getRequestMsg(SkyApiContext requestContext) {
		SkyMsg requestMsg = null;
		if(requestContext.getRequestSkyMsg() != null) {
			requestMsg = requestContext.getRequestSkyMsg();
		}else {
			requestMsg = new SkyMsg();
			requestMsg.setHead(new SkyMsgHead());
		}
		return requestMsg;
	}

}
