package com.sky.service.api.support;

import com.sky.service.api.exception.ApiException;

public interface ApiPlugin {

	void doPlugin(ApiContext context, ApiPluginChain chain) throws ApiException;

}
