package com.sky.core.service.api.callback.endpoint;

public interface ApiEndpointManager {
	public void registerCallbackEndpoint(String endpointKey, ApiCallbackEndpoint endpoint, int timeoutSeconds);
	
	public ApiCallbackEndpoint unRegisterCallbackEndpoint(String endpointKey);
	
	public ApiCallbackEndpoint findCallbackEndpoint(String endpointKey);
}
