package com.sky.service.api.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ApiHandlerAdapter implements ApiHandler {
	@SuppressWarnings("unused")
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void initialize() {}

	@Override
	public void reload() {}

	@Override
	public void doHandle(ApiContext context) {}

	@Override
	public void destory() {}

}
