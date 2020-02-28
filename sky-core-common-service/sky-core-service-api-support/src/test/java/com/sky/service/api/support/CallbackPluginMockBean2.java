package com.sky.service.api.support;

import org.springframework.stereotype.Component;

import com.sky.service.api.exception.ApiException;
import com.sky.service.api.support.plugin.OrderedApiPlugin;

@Component
@ApiPluginMapping(order = 4, type = ApiPluginTypeEnum.STANDARD)
public class CallbackPluginMockBean2 extends OrderedApiPlugin {

	@Override
	protected void onDoPlugin(ApiContext context, ApiPluginChain chain) throws ApiException {
		this.logger.info("on do plugin {} ...", this.getPluginName());
		chain.activatePluginChain(context);
	}

}
