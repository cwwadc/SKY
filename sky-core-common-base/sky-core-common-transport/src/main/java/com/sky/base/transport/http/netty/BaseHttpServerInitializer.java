package com.sky.base.transport.http.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 
 * @title
 * @description
 * @author lizp
 * @version 1.0.0
 * @date 2019-05-23
 */
public abstract class BaseHttpServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new HttpServerCodec());
		p.addLast(new HttpObjectAggregator(65535));
		initCustomHandler(p);
	}

	protected abstract void initCustomHandler(ChannelPipeline p);
}
