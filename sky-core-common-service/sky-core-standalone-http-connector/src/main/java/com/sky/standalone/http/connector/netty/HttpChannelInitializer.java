package com.sky.standalone.http.connector.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {
	private static final Logger logger = LoggerFactory.getLogger(HttpChannelInitializer.class);
	private EventLoopGroup handlerGroup;
	private String contextUri;
	
	public HttpChannelInitializer(EventLoopGroup handlerGroup, String contextUri) {
		if(logger.isDebugEnabled()) {
			logger.info("{} initialize...", this.getClass().getSimpleName());
		}
		this.handlerGroup = handlerGroup;
		this.contextUri = contextUri;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		logger.info("{} channel initialize....", this.getClass().getSimpleName());
		ChannelPipeline ph = ch.pipeline();
		ch.config().setAllocator(PooledByteBufAllocator.DEFAULT);
		ch.config().setOption(ChannelOption.TCP_NODELAY, true);
		ph.addFirst(new LoggingHandler(LogLevel.INFO));
		ph.addLast("encoder", new HttpResponseEncoder());
		ph.addLast("decoder", new HttpRequestDecoder());
		ph.addLast("aggregator", new HttpObjectAggregator(5 * 1024 * 1024 * 1024));
		ph.addLast(handlerGroup, "Gateway-Handler-Group", new HttpChannelHandler(contextUri));
		
	}
	
	

}
