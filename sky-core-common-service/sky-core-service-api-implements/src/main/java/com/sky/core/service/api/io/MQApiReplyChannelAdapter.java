package com.sky.core.service.api.io;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.core.service.api.route.ApiRouteConstants;
import com.sky.core.service.api.route.IBMMQRouteAware;
import com.sky.service.api.support.ApiReplyChannelAdapter;

public class MQApiReplyChannelAdapter implements ApiReplyChannelAdapter{
	private static final Logger logger = LoggerFactory.getLogger(MQApiReplyChannelAdapter.class);
	
	private IBMMQRouteAware routeAware;

	public Future<?> adaptReply(String responseMetaData) {
		routeAware.deliver(responseMetaData, ApiRouteConstants.ROUTE_UNUSE_DATA_COMPRESS);
		return null;
	}

}
