package com.sky.core.service.api.manage;

import com.sky.core.service.api.callback.task.ApiCallbackTask;
import com.sky.core.service.api.route.ApiRouteAware;
import com.sky.service.api.struct.context.FailRetryableApiContext;

public interface ApiRelayManager<T, K> {
	
	public void commit(FailRetryableApiContext<T> context, ApiRouteAware<T, K> aware, ApiCallbackTask callbackTask, Integer callbackTimeout);
	
	public void commit(FailRetryableApiContext<T> context, ApiRouteAware<T, K> aware);
	
}
