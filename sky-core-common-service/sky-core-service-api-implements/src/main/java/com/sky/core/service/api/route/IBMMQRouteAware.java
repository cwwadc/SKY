package com.sky.core.service.api.route;

import javax.jms.BytesMessage;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.sky.base.serialize.json.JsonUtils;
import com.sky.core.service.api.serialize.ApiCompressFactory;
import com.sky.core.service.api.serialize.GZipBasedApiCompressFactory;
import com.sky.service.api.exception.ApiException;
import com.sky.service.api.struct.domain.common.IBMMQBinding;

/**
 * IBM-MQ协议路由适配器组件，封装IBM-MQ协议下的消息发送，实现路由适配器接口
 *
 */
public class IBMMQRouteAware extends ApiRouteAware<String, String> {
	private static final Logger logger = LoggerFactory.getLogger(IBMMQRouteAware.class);
	private static final String IBMMQ_ROUTE_AWARE_CHARSET = "GBK";
	
	protected IBMMQBinding binding;
	protected Long receiveTimeout;
	protected Integer defaultSessionCacheSize = 800;
	protected ConnectionFactory connectionFactory;
	protected JmsTemplate jmsTemplate;
	protected MQQueue targetQ;
	protected long count = 0L;
	protected long millsnow = 0L;
	final public static long DAY_MILL = (24 * 60 * 60 * 1000);
	
	protected synchronized long count() {             //计算每秒处理消息数
		long lt = System.currentTimeMillis() - millsnow;
		if (lt > (DAY_MILL / 12))
			count = 0;
		if (count == 0)
			millsnow = System.currentTimeMillis();
		count++;
		if (lt <= 0)
			lt = 1;
		return count * 1000  / lt;
	}

	public IBMMQRouteAware(IBMMQBinding binding, Long receiveTimeout) {
		try {
			this.binding = binding;
			this.connectionFactory = initConnectionFactory(binding);
			this.receiveTimeout = receiveTimeout;
			this.jmsTemplate = new JmsTemplate(this.connectionFactory);
			this.jmsTemplate.setReceiveTimeout(receiveTimeout);
			this.targetQ = new MQQueue(binding.getQueue());
			this.targetQ.setTargetClient(WMQConstants.WMQ_CLIENT_NONJMS_MQ);  //禁用RFH标targetClient送到非JMS队列的一种方法是使用targetClient队列URI属性
			this.syncReturn = Boolean.FALSE;
			
			logger.debug("IBMMQRouteAware initialize success, binding -> {}", JsonUtils.toJsonString(binding));
		} catch (Exception e) {
			throw new ApiException(String.format("IBMMQRouteAware initialize success, binding -> %s", JsonUtils.toJsonString(binding)), e);
		}
	}
	
	private ConnectionFactory initConnectionFactory(IBMMQBinding binding) throws JMSException {
		MQQueueConnectionFactory connectionFactory = new MQQueueConnectionFactory();
		connectionFactory.setHostName(binding.getHost());
		connectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
		connectionFactory.setCCSID(binding.getCcsid());
		connectionFactory.setChannel(binding.getChannel());
		connectionFactory.setPort(binding.getPort());
		connectionFactory.setQueueManager(binding.getQmgr());

		UserCredentialsConnectionFactoryAdapter credentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
		credentialsConnectionFactoryAdapter.setUsername(binding.getUsername());
		credentialsConnectionFactoryAdapter.setPassword(binding.getPassword());
		credentialsConnectionFactoryAdapter.setTargetConnectionFactory(connectionFactory);

		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setTargetConnectionFactory(credentialsConnectionFactoryAdapter);
		cachingConnectionFactory.setSessionCacheSize(this.defaultSessionCacheSize);
		cachingConnectionFactory.setReconnectOnException(true);

		JmsTransactionManager transactionManager = new JmsTransactionManager();
		transactionManager.setConnectionFactory(cachingConnectionFactory);
		return cachingConnectionFactory;
	}

	@Override
	public String deliver(String deliverContent, String useDataCompress) {
		this.jmsTemplate.send(targetQ, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				BytesMessage message = session.createBytesMessage();
				message.setIntProperty(WMQConstants.JMS_IBM_CHARACTER_SET, binding.getCcsid());
				try {
					byte[] deliverBytes = deliverContent.getBytes(IBMMQ_ROUTE_AWARE_CHARSET);
					if(ApiRouteConstants.ROUTE_USE_DATA_COMPRESS.equals(useDataCompress)) {
						deliverBytes = getCompressFactory().compress(deliverContent);
					}
					message.writeBytes(deliverBytes);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new JMSException(e.getMessage());
				}
				
				return message;
			}
		});
//		logger.debug("send speed per second: "+count()+" count: "+count);
		return null;
	}
	
	@Override
	public ApiCompressFactory getCompressFactory() {
		if(this.compressFactory == null) {
			if(logger.isDebugEnabled()) {
				logger.debug("did not set a api-compress-factory in current api-route-aware, use default -> {}", GZipBasedApiCompressFactory.class.getSimpleName());
			}
			return GZipBasedApiCompressFactory.instance();
		}
		return this.compressFactory;
	}

	@Override
	public void destory() {
		if(this.connectionFactory != null) {
			try {
				((CachingConnectionFactory)this.connectionFactory).destroy();
			} catch (Exception e) {
				
			}
			
		}
	}

	@Override
	public Boolean isRemoteEndReachable() {
		// 默认远端服务可达
		return Boolean.TRUE;
	}
	
	

}
