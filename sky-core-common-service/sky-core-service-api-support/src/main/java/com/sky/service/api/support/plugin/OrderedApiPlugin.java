package com.sky.service.api.support.plugin;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.service.api.exception.ApiException;
import com.sky.service.api.support.ApiContext;
import com.sky.service.api.support.ApiPlugin;
import com.sky.service.api.support.ApiPluginChain;

public abstract class OrderedApiPlugin implements ApiPlugin{
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String pluginName = this.getClass().getSimpleName();
	private Integer order;
	private Boolean avaiableState;
	
	@Override
	public void doPlugin(ApiContext context, ApiPluginChain chain) throws ApiException {
		if(!this.getAvaiableState()) {
			logger.info("plugin {} unavaiable, skipped.", this.getPluginName());
		} else if(ArrayUtils.contains(context.getSkippedPlugins(), this.getClass())) {
			logger.info("plugin {} found in coentxt skipped plugins, skipped.", this.getPluginName());
		} else {
			logger.info("on plugin {}", this.getPluginName());
			onDoPlugin(context, chain);
		}
	}
	
	protected abstract void onDoPlugin(ApiContext context, ApiPluginChain chain) throws ApiException;
	
	public String getPluginName() {
		return pluginName;
	}
	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Boolean getAvaiableState() {
		return avaiableState;
	}
	public void setAvaiableState(Boolean avaiableState) {
		this.avaiableState = avaiableState;
	}

}
