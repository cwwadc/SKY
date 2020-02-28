package com.sky.base.transport.http.netty;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sky.base.test.net.NetUtils;
import com.sky.base.transport.http.HttpUtils;
import com.sky.base.transport.http.netty.DefaultHttpServer;
import com.sky.base.transport.http.netty.HelloWorldHttpServerInitializer;

/**
 * 
 * @title
 * @description
 * @author lizp
 * @version 1.0.0
 * @date 2019-05-23
 */
public class DefaultHttpServerTest {

	@Test
	public void testHttpServerGet() throws Exception {
		int port = getAvailablePort();
		DefaultHttpServer httpServer = launchHttpServer(port);
		try {
			assertEquals("Hello World", HttpUtils.get("http://127.0.0.1:" + port + "/abc?name=123"));
		} finally {
			httpServer.shutdownGracefully();
		}
	}

	@Test
	public void testHttpServerPost() throws Exception {
		int port = getAvailablePort();
		DefaultHttpServer httpServer = launchHttpServer(port);
		try {
			assertEquals("Hello World", HttpUtils.post("http://127.0.0.1:" + port + "/admin", "test"));
		} finally {
			httpServer.shutdownGracefully();
		}
	}

	private int getAvailablePort() {
		return NetUtils.getAvailablePort();
	}

	private DefaultHttpServer launchHttpServer(int port) {
		DefaultHttpServer httpServer = new DefaultHttpServer("hello world", HelloWorldHttpServerInitializer.class,
		        port);
		Runnable launch = new Runnable() {
			public void run() {
				try {
					httpServer.launch();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		Thread thread = new Thread(launch);
		thread.start();
		return httpServer;
	}

}
