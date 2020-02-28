package com.sky.standalone.jms.connector.webspheremq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;

import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.sky.base.jdbc.spring.CoreDao;
import com.sky.base.lang.logging.LoggingFormatter;
import com.sky.core.service.api.utils.SkyChnlUtils;
import com.sky.service.api.struct.domain.common.IBMMQBinding;
import com.sky.service.api.struct.pojo.SkyMQChnlCfg;
import com.sky.standalone.container.component.AbstractLifecycleComponent;

@Component
@Configuration
public class MQApiListenerContainer extends AbstractLifecycleComponent<MQApiListenerContainer> {
	private static final Logger logger = LoggerFactory.getLogger(MQApiListenerContainer.class);
	
	private Boolean transactioned = false;
	private Integer ackmod = Session.AUTO_ACKNOWLEDGE;
	private Integer defaultSessionCacheSize = 500;
	private Map<String, DefaultMessageListenerContainer> listenerManager = new HashMap<String, DefaultMessageListenerContainer>();
	private MQApiRoundup roundup = new MQApiRoundup();
	private List<SkyMQChnlCfg> chnls;
	
	@Autowired
	private CoreDao coreDao;
	
	@Value("${sky.api.sql.select.chnlcfg}")
	private String chnlsSelectSql;

	@Override
	protected void doStart() throws Exception {
		if(this.chnls == null) {
			this.chnls = findChnls();
		}
		for (SkyMQChnlCfg chnlCfg : chnls) {
			startChnl(chnlCfg);
		}
	}

	public void startChnl(SkyMQChnlCfg chnlCfg) throws JMSException {
		IBMMQBinding binding = SkyChnlUtils.parseIBMMQBinding(chnlCfg.getBindingUri());
		DefaultMessageListenerContainer listener = listenerManager.get(chnlCfg.getConfigName());
		
		if(listener == null) {
			listener = new DefaultMessageListenerContainer();
		}
		if(listener.isRunning()) {
			listener.destroy();
		}
		listener.setConnectionFactory(initConnectionFactory(binding));
		listener.setSessionTransacted(this.transactioned);
		listener.setSessionAcknowledgeMode(this.ackmod);
		listener.setDestination(new MQQueue(binding.getQueue()));
		listener.setBeanName(chnlCfg.getConfigName());
		listener.setAutoStartup(false);
		listener.setConcurrentConsumers(chnlCfg.getThreadNum());
		listener.setCacheLevel(DefaultMessageListenerContainer.CACHE_CONSUMER);
		listener.setRecoveryInterval(chnlCfg.getRecoveryInterval());
		listener.setReceiveTimeout(chnlCfg.getReceiveTimeout());
		
		MQApiProxy apiProxy = new MQApiProxy();
		apiProxy.setBinding(binding);
		apiProxy.setRoundup(roundup);
		apiProxy.setUseDataCompress(chnlCfg.getUseDataCompress());
		listener.setMessageListener(apiProxy);
		
		listenerManager.put(chnlCfg.getConfigName(), listener);
		listener.afterPropertiesSet();
		listener.start();
		
		logger.info(LoggingFormatter.stdPrettyInfo("MQ-Api Listener Container Environment", 76));
		logger.info(LoggingFormatter.subPrettyInfo(String.format("starting listen at Q -> [%s]", binding.getQueue()), 76));
		logger.info(LoggingFormatter.subPrettyInfo(String.format("listener.concurrent.consumer.size -> [%s]", listener.getConcurrentConsumers()), 76));
		logger.info(LoggingFormatter.stdPrettyInfo("MQ-Api Listener Container Environment", 76));
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

	private List<SkyMQChnlCfg> findChnls() {
		return coreDao.search(chnlsSelectSql, SkyMQChnlCfg.class);
	}

	@Override
	protected void doStop() throws Exception {
		while (roundup.getRunning() != 0) {
			Thread.sleep(800);
		}
		for (DefaultMessageListenerContainer dmlc : listenerManager.values()) {
			dmlc.shutdown();
		}
	}
	
	public List<SkyMQChnlCfg> getChnls() {
		return chnls;
	}

	public void setChnls(List<SkyMQChnlCfg> chnls) {
		this.chnls = chnls;
	}
	
}
