package com.sky.standalone.jms.connector.webspheremq;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.SessionAwareMessageListener;

import com.sky.base.context.spring.SpringContext;
import com.sky.core.service.api.io.MQApiReplyChannelAdapter;
import com.sky.core.service.api.route.ApiRouteConstants;
import com.sky.core.service.api.serialize.ApiCompressFactory;
import com.sky.core.service.api.serialize.GZipBasedApiCompressFactory;
import com.sky.service.api.struct.domain.common.IBMMQBinding;
import com.sky.service.api.support.ApiDispatcher;

public class MQApiProxy implements SessionAwareMessageListener<BytesMessage> {
	private static final Logger logger = LoggerFactory.getLogger(MQApiProxy.class);
	private static final String MQ_API_LISTENER_CHARSET = "GBK";
	
	private MQApiRoundup roundup;
	private IBMMQBinding binding;
	private String useDataCompress;
	private ApiCompressFactory compressFactory;

	public void onMessage(BytesMessage message, Session session) throws JMSException {
		try {
			logger.debug("onMessage start");
			if (roundup != null) {
				roundup.addRunning();
			}
			handleMessage(message, session);
		} catch (Throwable e) {
			logger.error("exception", e);
		} finally {
			if (roundup != null) {
				roundup.decRunning();
			}
			logger.debug("onMessage finish");
		}
	}

	private void handleMessage(Message message, Session session) throws Exception  {
		//ETS消息队列处理逻辑
		if (!(message instanceof BytesMessage)) {
			logger.error("get a NONE BytesMessage -> {} ", message.toString());
			return;
		}
		BytesMessage bytesMsg = (BytesMessage) message;
		int dataLength = new Long(bytesMsg.getBodyLength()).intValue();
        byte[] textBuffer = new byte[dataLength];
        bytesMsg.readBytes(textBuffer, dataLength);
        
        String msgText = null;
        if(ApiRouteConstants.ROUTE_USE_DATA_COMPRESS.equals(useDataCompress)) {
        	msgText = getCompressFactory().uncompress(textBuffer);   //通道监听数据类为压缩数据时需进行解压处理
        } else {
        	msgText = new String(textBuffer, MQ_API_LISTENER_CHARSET);
        }
		logger.debug("handling message from {} :\n{}", binding.getQueue(), msgText);
		ApiDispatcher apiDispatcher = SpringContext.getBean(ApiDispatcher.class);
		apiDispatcher.doDispatch(msgText, new MQApiReplyChannelAdapter());
	}

	public MQApiRoundup getRoundup() {
		return roundup;
	}

	public void setRoundup(MQApiRoundup roundup) {
		this.roundup = roundup;
	}

	public IBMMQBinding getBinding() {
		return binding;
	}

	public void setBinding(IBMMQBinding binding) {
		this.binding = binding;
	}

	public String getUseDataCompress() {
		return useDataCompress;
	}

	public void setUseDataCompress(String useDataCompress) {
		this.useDataCompress = useDataCompress;
	}
	
	public ApiCompressFactory getCompressFactory() {
		if(this.compressFactory == null) {
			if(logger.isDebugEnabled()) {
				logger.debug("did not set a api-compress-factory in current api-route-aware, use default -> {}", GZipBasedApiCompressFactory.class.getSimpleName());
			}
			return GZipBasedApiCompressFactory.instance();
		}
		return this.compressFactory;
	}

	public void setCompressFactory(ApiCompressFactory compressFactory) {
		this.compressFactory = compressFactory;
	}

}
