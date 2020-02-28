package com.sky.core.service.api.transaction.plugin;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.sky.core.service.api.utils.RabbitUtil;
import com.sky.service.api.exception.ApiException;
import com.sky.service.api.struct.context.SkyApiContext;
import com.sky.service.api.support.ApiContext;
import com.sky.service.api.support.ApiPluginChain;
import com.sky.service.api.support.ApiPluginMapping;
import com.sky.service.api.support.ApiPluginTypeEnum;
import com.sky.service.api.support.plugin.OrderedApiPlugin;

/**
  *   通用请求/通知记录处理插件
  *
  */
@Component
@Configuration
@ApiPluginMapping(order=2, type=ApiPluginTypeEnum.STANDARD)
public class MessageRecordingPlugin extends OrderedApiPlugin {
	@Value("${rabbit.queue.offlineRecording}")
	private String queueName;
	
	@Autowired
	private RabbitUtil rabbitUtil;
	
	@Autowired
	@Qualifier("apiRecordingPool")
	private Executor apiRecordingPool;

	@Override
	protected void onDoPlugin(ApiContext context, ApiPluginChain chain) throws ApiException {
		// 请求记录处理（默认采用异步发送rabbimq消息，后台应用离线写入数据库方式进行记录），放置报文发送逻辑
		doPluginCore(context);
		chain.activatePluginChain(context);
	}
	
	public void doPluginCore(ApiContext context) {
		apiRecordingPool.execute(new MesssageRecordingRunnable(this.logger, context));
	}
	
	class MesssageRecordingRunnable implements Runnable {
		private Logger logger;
		private ApiContext context;
		public MesssageRecordingRunnable() {}
		public MesssageRecordingRunnable(Logger logger, ApiContext context) {
			this.logger = logger;
			this.context = context;
		}
		@Override
		public void run() {
			try {
				SkyApiContext contextWrapper = (SkyApiContext)context;
				String recordingData = String.format("%s->%s", contextWrapper.getTraceId(), contextWrapper.getRecordablePlainText());
				this.logger.info("message-recording runnable execute, trace-id -> {}", contextWrapper.getTraceId());
				rabbitUtil.sendToQueue(queueName, recordingData);
			} catch (Exception e) {
				this.logger.error("message-recording runnable execute error", e); 
			}
		}
	}

}
