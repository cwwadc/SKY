package com.sky.core.service.api.transaction.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.base.lang.string.StringUtils;
import com.sky.core.service.api.utils.SkyApiMsgUtils;
import com.sky.service.api.exception.ApiException;
import com.sky.service.api.struct.context.SkyApiContext;
import com.sky.service.api.support.ApiContext;
import com.sky.service.api.support.ApiContextSupport;
import com.sky.service.api.support.ApiDispatcher;
import com.sky.service.api.support.ApiHandler;
import com.sky.service.api.support.ApiReplyChannelAdapter;
import com.sky.service.api.support.chain.OrderedPluginChain;

/**
  *   默认HTTP端交易分发实现
  *   交易方向 ：征管系统 -> ETS
  */
@Component
public class DefaultHttpApiDispatcher implements ApiDispatcher {
	private static final Logger logger = LoggerFactory.getLogger(DefaultHttpApiDispatcher.class);
	@Autowired
	private ApiContextSupport apiContextSupport;

	public ApiContext doDispatch(String requestMetaData, ApiReplyChannelAdapter replyChannelAdapter) {
		SkyApiContext context = new SkyApiContext();
		context.setRequestPlainText(requestMetaData);
		context.setRecordablePlainText(requestMetaData);
		context.setReplyAdapter(replyChannelAdapter);
		try {
			if(StringUtils.isEmpty(requestMetaData)) {
				throw new NullPointerException("request-metadata cannot be null or empty.");
			}
			if(logger.isDebugEnabled()) {
				logger.debug("on http-api-dispather, trace-id -> {}, request-metadata ->\n [{}]", context.getTraceId(), requestMetaData);
			}
			String msgType = SkyApiMsgUtils.unpackMsgType(requestMetaData);
			if(StringUtils.isEmpty(msgType)) {
				throw new ApiException("cannot found msgType");
			}
			context.setRequstMsgType(msgType);
			logger.info("msg-type -> {}, trace-id -> {}.", msgType, context.getTraceId());
			ApiHandler handler = apiContextSupport.findHandler(msgType);
			if(handler == null) {
				handler = apiContextSupport.defaultApiHandler();
				logger.warn("cannot found handler by msg type, use default handler -> {}.", handler.getClass().getSimpleName());
			}
			OrderedPluginChain defaultTransactionChain = new OrderedPluginChain(apiContextSupport.getStandardPlugins(), handler);
			defaultTransactionChain.activatePluginChain(context);
		} catch (Exception e) {
			logger.error("api dispatch error", e);
			context.setErrorCause(e);
			OrderedPluginChain defaultErrorReturnChain = new OrderedPluginChain(apiContextSupport.getErrorPlugins(), apiContextSupport.errorApiHandler());
			defaultErrorReturnChain.activatePluginChain(context);
		}
		return context;
	}

}
