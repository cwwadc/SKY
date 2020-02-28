package com.sky.core.service.api.transaction.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sky.core.service.api.serialize.ApiSerializer;
import com.sky.service.api.exception.ApiException;
import com.sky.service.api.struct.context.SkyApiContext;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.support.ApiContext;
import com.sky.service.api.support.ApiPluginChain;
import com.sky.service.api.support.ApiPluginMapping;
import com.sky.service.api.support.ApiPluginTypeEnum;
import com.sky.service.api.support.plugin.OrderedApiPlugin;

/**
  *   通用请求预检查插件
  *
  */
@Component
@ApiPluginMapping(order=1, type=ApiPluginTypeEnum.STANDARD)
public class MessageValidateApiPlugin extends OrderedApiPlugin {
	
	@Autowired
	private ApiSerializer<SkyMsg> apiSerializer;

	@Override
	protected void onDoPlugin(ApiContext context, ApiPluginChain chain) throws ApiException {
		//请求预检查处理，放置检查逻辑
		SkyApiContext contextWrapper = (SkyApiContext)context;
		if(StringUtils.isEmpty(contextWrapper.getRequestPlainText())) {
			throw new ApiException("request plain text cannot be null.");
		}
		Class<?> deserializeType = apiSerializer.lookupDeserializeType(contextWrapper.getRequstMsgType());
		if(deserializeType == null) {
			throw new ApiException("cannot found deserialize-type or unrecognized msg type, please check.");
		}
		logger.debug("api-deserialize type -> {}", deserializeType.getName());
		try {
			SkyMsg requestMsg = apiSerializer.deserialize(contextWrapper.getRequestPlainText(), deserializeType);
			contextWrapper.setRequestSkyMsg(requestMsg);
			contextWrapper.setMsgId(requestMsg.getHead().getMsgId());
			chain.activatePluginChain(context);
		} catch (Exception e) {
			throw new ApiException(e);
		}
	}

}
