package com.sky.service.api.event;

import com.sky.service.api.support.ApiContext;

public class ContextualApiEvent implements ApiEvent {
	private ApiContext eventContext;

	public ApiContext getEventContext() {
		return eventContext;
	}

	public void setEventContext(ApiContext eventContext) {
		this.eventContext = eventContext;
	}
	
	
}
