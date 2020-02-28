package com.sky.service.api.support;

public interface ApiHandler {
	void initialize();
	void reload();
	void doHandle(ApiContext context);
	void destory();

}
