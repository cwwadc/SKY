package com.sky.standalone.http.connector.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sky.standalone.http.connector.netty.NettyEmbeddedContainer;

@Configuration
public class NettyHttpServerConfiguration {
	
	@Bean
	public NettyEmbeddedContainer nettyEmbeddedContainer() throws Exception {
		NettyEmbeddedContainer nettyContainer = new NettyEmbeddedContainer();
		return nettyContainer;
	}
}
