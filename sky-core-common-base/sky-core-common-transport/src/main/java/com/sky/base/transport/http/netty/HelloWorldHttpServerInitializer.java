package com.sky.base.transport.http.netty;

import io.netty.channel.ChannelPipeline;

/**
 * 
 * @title
 * @description
 * @author lizp
 * @version 1.0.0
 * @date 2019-05-23
 */
public class HelloWorldHttpServerInitializer extends BaseHttpServerInitializer {

	@Override
	protected void initCustomHandler(ChannelPipeline p) {
		p.addLast(new HelloWorldHttpServerHandler());
	}
}
