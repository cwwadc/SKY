package com.sky.standalone.http.connector.netty;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.context.spring.SpringContext;
import com.sky.core.service.api.io.HttpConnectorSession;
import com.sky.core.service.api.io.HttpConnectorUtils;
import com.sky.service.api.support.ApiDispatcher;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;

public class HttpChannelHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	private static final Logger logger = LoggerFactory.getLogger(Logger.class);

	private HttpConnectorSession session;	
	private String contextUri;
	
	public HttpChannelHandler(String contextUri) {
		this.contextUri = contextUri;
	}
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ApiDispatcher apiDispatcher = SpringContext.getBean(ApiDispatcher.class);
        session = new HttpConnectorSession(ctx.channel(), apiDispatcher);
        logger.debug("channel active, initialize session -> id:{}.", session.getSessionId());
    }

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		logger.info("fire flush now....");
		ctx.flush();
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
		if(msg.uri().equalsIgnoreCase(contextUri)) {
			session.handleRequest(msg);
		} else {
			logger.debug("No handler can be found for request-uri [{}], returning NOT_FOUND (404)", msg.uri());
			handler404(ctx, msg);
			
		}
	}

	private void handler404(ChannelHandlerContext ctx, FullHttpRequest msg) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer("No context-path matches the request URI.".getBytes()));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpConnectorUtils.HeaderValues.CONTENETTYPE_PLAINTEXT);
		response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
		response.setStatus(HttpResponseStatus.NOT_FOUND);
		
		if(!HttpUtil.isKeepAlive(msg)) {
			response.headers().set(HttpHeaderNames.CONNECTION, HttpConnectorUtils.HeaderValues.CONNECTION_CLOSE);
			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		} else {
			response.headers().set(HttpHeaderNames.CONNECTION, HttpConnectorUtils.HeaderValues.CONNECTION_KEEPALIVE);
			ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
		        @Override
		        public void operationComplete(ChannelFuture future) throws Exception {
		            if (future.isSuccess()){
		                logger.debug("on handle 404 finish.");
		            }else{
		            	logger.debug("on handle 404 error.", future.cause());
		            }
		        }
		    });	
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error("gateway http channel handler exception.", cause);
		ctx.close();
	}

}
