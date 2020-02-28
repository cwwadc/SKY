package com.sky.service.api.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ApiHandlerMapping(errorHandler=true)
public class ErrorApiHandlerMockBean extends ApiHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(ErrorApiHandlerMockBean.class);

	@Override
	public void doHandle(ApiContext context) {
		
		logger.info("on {} ...", this.getClass().getSimpleName());
	}
	
	
}
