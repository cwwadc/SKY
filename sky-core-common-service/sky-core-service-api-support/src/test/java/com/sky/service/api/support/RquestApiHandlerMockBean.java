package com.sky.service.api.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ApiHandlerMapping(value="apicode", defaultHandler=true)
public class RquestApiHandlerMockBean extends ApiHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(RquestApiHandlerMockBean.class);

	@Override
	public void doHandle(ApiContext context) {
		
		logger.info("on {} ...", this.getClass().getSimpleName());
	}
	
	
}
