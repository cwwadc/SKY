package com.sky.core.service.api.io;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.nio.charset.Charset;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.lang.string.StringUtils;
import com.sky.service.api.support.ApiReplyChannelAdapter;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.util.CharsetUtil;

public class HttpApiReplyChannelAdapter implements ApiReplyChannelAdapter{
	private static final Logger logger = LoggerFactory.getLogger(HttpApiReplyChannelAdapter.class);
	
	public static final String REPLY_CONTEXT_TYPE = "GBK";
	public HttpConnectorSession session;
	public boolean keepAlive;
	
	public HttpApiReplyChannelAdapter(HttpConnectorSession session, boolean keepAlive) {
		this.session = session;
		this.keepAlive = keepAlive;
	}

	@Override
	public Future<?> adaptReply(String responseMetaData) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(responseMetaData.getBytes(Charset.forName(REPLY_CONTEXT_TYPE))));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpConnectorUtils.HeaderValues.CONTENETTYPE_PLAINTEXT);
		response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
		logger.debug("Response -> [{}]", StringUtils.toEncodedString(ByteBufUtil.getBytes(response.content()), CharsetUtil.UTF_8));
		if (!keepAlive) {
			return session.getChannel().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		} else {
			response.headers().set(HttpHeaderNames.CONNECTION, HttpConnectorUtils.HeaderValues.CONNECTION_KEEPALIVE);
			return session.getChannel().writeAndFlush(response);
		}
	}

}
