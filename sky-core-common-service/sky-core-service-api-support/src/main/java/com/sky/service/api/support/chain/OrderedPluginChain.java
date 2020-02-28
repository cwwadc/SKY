package com.sky.service.api.support.chain;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.service.api.support.ApiContext;
import com.sky.service.api.support.ApiHandler;
import com.sky.service.api.support.ApiPlugin;
import com.sky.service.api.support.ApiPluginChain;

public class OrderedPluginChain implements ApiPluginChain {
	private static final Logger logger = LoggerFactory.getLogger(OrderedPluginChain.class);
	
	private Iterator<ApiPlugin> apiPluginsIterator;

	private ApiHandler apiHandler;
	
	public OrderedPluginChain(Iterable<ApiPlugin> apiPlugins) {
		super();
		this.apiPluginsIterator = apiPlugins.iterator();
	}
	public OrderedPluginChain(Iterable<ApiPlugin> apiPlugins, ApiHandler handler) {
		super();
		this.apiPluginsIterator = apiPlugins.iterator();
		this.apiHandler = handler;
	}
	public ApiHandler getApiHandler() {
		return apiHandler;
	}

	public void setApiHandler(ApiHandler apiHandler) {
		this.apiHandler = apiHandler;
	}

	@Override
	public void activatePluginChain(ApiContext context) {
		if(apiPluginsIterator.hasNext()) {
			ApiPlugin plugin = apiPluginsIterator.next();
			plugin.doPlugin(context, this);
		}else if(apiHandler != null) {
			apiHandler.doHandle(context);
		}
		
	}

}
