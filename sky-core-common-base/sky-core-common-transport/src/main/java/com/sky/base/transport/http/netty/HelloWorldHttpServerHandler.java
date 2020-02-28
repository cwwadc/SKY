package com.sky.base.transport.http.netty;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.AsciiString;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.lang.constant.CharsetConstants;

/**
 * 
 * @title
 * @description
 * @author lizp
 * @version 1.0.0
 * @date 2019-05-23
 */
@Sharable
public class HelloWorldHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldHttpServerHandler.class);

	private static final byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd' };

	private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
	private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");
	private static final AsciiString CONNECTION = AsciiString.cached("Connection");
	private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
		LOGGER.info("Request uri -> [{}], content -> [{}]", msg.uri(), StringUtils
		        .toEncodedString(ByteBufUtil.getBytes(msg.content()), Charset.forName(CharsetConstants.UTF_8)));

		boolean keepAlive = HttpUtil.isKeepAlive(msg);
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(CONTENT));
		response.headers().set(CONTENT_TYPE, "text/plain");
		response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

		LOGGER.info("Response -> [{}]", StringUtils.toEncodedString(ByteBufUtil.getBytes(response.content()),
		        Charset.forName(CharsetConstants.UTF_8)));
		if (!keepAlive) {
			ctx.write(response).addListener(ChannelFutureListener.CLOSE);
		} else {
			response.headers().set(CONNECTION, KEEP_ALIVE);
			ctx.write(response);
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		LOGGER.error(cause.getMessage(), cause);
		ctx.close();
	}
}
