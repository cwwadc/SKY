package com.sky.standalone.http.connector.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sky.base.lang.logging.LoggingFormatter;
import com.sky.standalone.container.component.AbstractLifecycleComponent;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.SystemPropertyUtil;

@Component
public class NettyEmbeddedContainer extends AbstractLifecycleComponent<NettyEmbeddedContainer> {
	private static final Logger logger = LoggerFactory.getLogger(NettyEmbeddedContainer.class);
	
	static final int DEFAULT_BINDING_PORT = 8080;  //默认绑定监听端口8080
	static final String DEFAULT_BINDING_HTTP_URI = "/api";   //默认绑定http-uri
	static final int DEFAULT_MAX_SELECTOR_THREAD = 1;   //默认selector线程数为1
	static final int DEFAULT_MAX_IO_THREAD = Runtime.getRuntime().availableProcessors() * 2;   //默认IO线程数为可用CPU核心数*2
	static final int DEFAULT_MAX_SERVICE_THREAD = 512;  //默认service线程数512
	static final int DEFAULT_MAX_CONNECTION_BACKLOG = 1024;    //默认客户端连接等待队列长度1024
	
	protected Channel serverChannel;
	protected ServerBootstrap bootstrap;
	protected EventLoopGroup bossGroup;
	protected EventLoopGroup workerGroup;
	protected EventLoopGroup serviceGroup;
	protected int bindingPort;
	protected String bindingUri;
	protected int maxSelectorThreadSize;  
	protected int maxIOThreadSize;  
	protected int maxServiceThreadSize;  
	protected int maxConnectionBacklog;	  
	protected boolean epollAvailable;

	/**
	 * 初始化容器配置项
	 */
	protected void initializeContainerConfiguration() {
		this.bindingPort = SystemPropertyUtil.getInt("gateway.server.binding.port", DEFAULT_BINDING_PORT);
		this.bindingUri = SystemPropertyUtil.get("gateway.server.binding.http.uri", DEFAULT_BINDING_HTTP_URI);
		this.maxSelectorThreadSize = SystemPropertyUtil.getInt("gateway.server.thread.selector", DEFAULT_MAX_SELECTOR_THREAD);
		this.maxIOThreadSize = SystemPropertyUtil.getInt("gateway.server.thread.io", DEFAULT_MAX_IO_THREAD);
		this.maxServiceThreadSize = SystemPropertyUtil.getInt("gateway.server.thread.serivce", DEFAULT_MAX_SERVICE_THREAD);
		this.maxConnectionBacklog = SystemPropertyUtil.getInt("gateway.server.connection.backlog", DEFAULT_MAX_CONNECTION_BACKLOG);
		this.epollAvailable = Epoll.isAvailable();
		logger.info(LoggingFormatter.stdPrettyInfo("Embbed Netty Container Environment", 76));
		logger.info(LoggingFormatter.subPrettyInfo(String.format("OS-Environment -> [%s], EPoll-Supported -> [%s]", System.getProperty("os.name").trim(), this.epollAvailable), 76));
		logger.info(LoggingFormatter.subPrettyInfo(String.format("Gateway.server.binding.port -> [%s]", this.bindingPort), 76));
		logger.info(LoggingFormatter.subPrettyInfo(String.format("Gateway.server.binding.http.uri -> [%s]", this.bindingUri), 76));
		logger.info(LoggingFormatter.subPrettyInfo(String.format("Gateway.server.thread-group.selector.size -> [%s]", this.maxSelectorThreadSize), 76));
		logger.info(LoggingFormatter.subPrettyInfo(String.format("Gateway.server.thread-group.io.size -> [%s]", this.maxIOThreadSize), 76));
		logger.info(LoggingFormatter.subPrettyInfo(String.format("Gateway.server.thread-group.service.size -> [%s]", this.maxServiceThreadSize), 76));
		logger.info(LoggingFormatter.subPrettyInfo(String.format("Gateway.server.connection.backlog -> [%s]", this.maxConnectionBacklog), 76));
		logger.info(LoggingFormatter.stdPrettyInfo("Embbed Netty Container Environment", 76));
	}
	
	/**
	 * 启动容器
	 */
	public void startServer() {
		try {
			initializeContainerConfiguration();
			this.serviceGroup = new DefaultEventLoopGroup(this.maxServiceThreadSize);
			ChannelHandler childHandler = new HttpChannelInitializer(this.serviceGroup, this.bindingUri);
			startNioServer(childHandler);
//			if(this.epollAvailable) {
//				startEpollServer(childHandler);
//			}else {
//				startNioServer(childHandler);
//			}
		} catch (Exception e) {
			logger.error("start server error", e);
			shutdownServer();
		}
	}
	

	/**启动NIO模式容器
	 * @param childHandler
	 * @throws Exception
	 */
	protected void startNioServer(ChannelHandler childHandler) throws Exception {
		this.bootstrap = new ServerBootstrap();
		this.bossGroup = new NioEventLoopGroup(this.maxSelectorThreadSize);
		this.workerGroup = new NioEventLoopGroup(this.maxIOThreadSize);
		
		this.bootstrap.group(this.bossGroup, this.workerGroup).channel(NioServerSocketChannel.class);
		setCommonBossChannelOption(this.bootstrap);
		this.bootstrap.handler(new LoggingHandler());
		this.bootstrap.childHandler(childHandler);
		
		ChannelFuture bindFuture = this.bootstrap.bind(this.bindingPort).sync();
		bindFuture.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(bindFuture.isSuccess()) {
					logger.info("embbed netty server started and listening for connections on {}", serverChannel.localAddress());  
				}else {
					logger.error("embbed netty server started fail", bindFuture);
				}
				
			}
		});
		this.serverChannel = bindFuture.channel();
//		this.serverChannel.closeFuture().sync();
	}
	
	/**启动EPOLL-IO模式容器，仅linux环境支持
	 * @param childHandler
	 * @throws Exception
	 */
	protected void startEpollServer(ChannelHandler childHandler) throws Exception {
		this.bootstrap = new ServerBootstrap();
		this.bossGroup = new EpollEventLoopGroup(this.maxSelectorThreadSize);
		this.workerGroup = new EpollEventLoopGroup(this.maxIOThreadSize);
		
		this.bootstrap.group(this.bossGroup, this.workerGroup).channel(EpollServerSocketChannel.class);
		setCommonBossChannelOption(this.bootstrap);
		setEpollChannelOption(this.bootstrap);
//		this.bootstrap.handler(new LoggingHandler());
		this.bootstrap.childHandler(childHandler);
		
		int cpuNum = Runtime.getRuntime().availableProcessors();
		logger.info("using epoll reuseport, avaiable cpu-number -> {}." + cpuNum);
		for (int i = 0; i < cpuNum; i++) {
			ChannelFuture future = this.bootstrap.bind(this.bindingPort).await();
			if (!future.isSuccess()) {
				throw new Exception("embbed netty server bind fail, port -> " + bindingPort) ;
			}
		}
		
		logger.info("embbed netty server started and listening for connections on {}", serverChannel.localAddress());
	}
	
	/**设置默认ServerChannel网络配置项
	 * @param serverBoostrap
	 */
	protected void setCommonBossChannelOption(ServerBootstrap serverBoostrap) {
		serverBoostrap.option(ChannelOption.SO_BACKLOG, this.maxConnectionBacklog)
				.option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 0)
				.option(ChannelOption.SO_LINGER, 0)
				.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
	}
	
	/**设置EPOLL环境子Channel网络配置项
	 * EpollChannelOption.SO_REUSEADDR, linux内核3.9(uname -a查看)以上支持改启该端口地址复用特性
	 * @param serverBoostrap
	 */
	protected void setEpollChannelOption(ServerBootstrap serverBoostrap) {
		serverBoostrap.childOption(EpollChannelOption.TCP_CORK, true)
				.childOption(EpollChannelOption.SO_REUSEADDR, true)
				.childOption(EpollChannelOption.SO_REUSEPORT, true);
	}

	/**关闭容器
	 * @return
	 */
	public ChannelFuture shutdownServer() {
		shutdownServerGroup(this.bossGroup, "BossGroup");
		shutdownServerGroup(this.workerGroup, "WorkerGroup");
		shutdownServerGroup(this.serviceGroup, "ServiceGroup");
		if(this.serverChannel != null) {
			return this.serverChannel.close();
		}
		return null;
	}
	
	/**关闭容器工作线程组
	 * @param serverGroup 工作线程组
	 * @param groupCode 工作线程组标识
	 */
	protected void shutdownServerGroup(EventLoopGroup serverGroup, String groupCode) {
		if(serverGroup != null) {
			serverGroup.shutdownGracefully();
			logger.debug("shutdown server-group -> {}.", groupCode);
		}
	}

	@Override
	protected void doStart() throws Exception {
		startServer();
	}

	@Override
	protected void doStop() throws Exception {
		shutdownServer();
	}

}
