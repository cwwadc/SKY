package com.sky.service.api.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ApiHandlerMapping(value= {"apicode2", "apicode3"})
public class RquestApiHandlerMockBean2 extends ApiHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(RquestApiHandlerMockBean2.class);

	@Override
	public void doHandle(ApiContext context) {
		
		logger.info("on {} ...", this.getClass().getSimpleName());
	}
	
	
}
