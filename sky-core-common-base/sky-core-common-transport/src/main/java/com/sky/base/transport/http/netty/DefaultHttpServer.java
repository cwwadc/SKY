package com.sky.base.transport.http.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @title
 * @description
 * @author lizp
 * @version 1.0.0
 * @date 2019-05-23
 */
public class DefaultHttpServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHttpServer.class);

	private final String name;

	private final Class<? extends BaseHttpServerInitializer> httpServerInitializerClass;

	private int port = 8080;

	private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);

	private final EventLoopGroup workerGroup = new NioEventLoopGroup();

	public DefaultHttpServer(String name, Class<? extends BaseHttpServerInitializer> httpServerInitializerClass) {
		super();
		this.name = name;
		this.httpServerInitializerClass = httpServerInitializerClass;
	}

	public DefaultHttpServer(String name, Class<? extends BaseHttpServerInitializer> httpServerInitializerClass,
	        int port) {
		super();
		this.name = name;
		this.httpServerInitializerClass = httpServerInitializerClass;
		this.port = port;
	}

	public void launch() throws Exception {

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
			        .childHandler(httpServerInitializerClass.newInstance());

			Channel channel = bootstrap.bind(port).sync().channel();
			LOGGER.info("Http server [{}] started and listening for connections on {}", name, channel.localAddress());
			channel.closeFuture().sync();
		} finally {
			shutdownGracefully();
		}
	}

	public void shutdownGracefully() throws Exception {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}

}
