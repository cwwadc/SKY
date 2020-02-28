package com.sky.core.service.api.configuration;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
  * 线程池配置bean：封装系统使用的各类业务线程池
 *
 */
@Configuration
public class ExecutorConfiguration {
	
	@Value("${sky.api.pool.recording.coresize}")
	private int coreRecordingPoolSize;
	@Value("${sky.api.pool.recording.maxsize}")
	private int maxRecordingPoolSize;
	@Value("${sky.api.pool.recording.queuecapacity}")
	private int recordingPoolQueueCapacity;
	@Value("${sky.api.pool.recording.keepaliveseconds}")
	private int recordingPoolKeepAliveSeconds;
	
	/**
	 * @return 离线接口写库任务处理线程池：所有的请求或应答信息异步入库，异步rabbit消息投递操作使用该池处理
	 */
	@Bean(name="apiRecordingPool")
    public TaskExecutor apiRecordingPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(coreRecordingPoolSize);
        executor.setMaxPoolSize(maxRecordingPoolSize);
        executor.setQueueCapacity(recordingPoolQueueCapacity);
        executor.setKeepAliveSeconds(recordingPoolKeepAliveSeconds);
        executor.setThreadNamePrefix("api-recording-pool-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
	}
	
	@Value("${sky.api.pool.eventbus.async.coresize}")
	private int coreEventBusPoolSize;
	@Value("${sky.api.pool.eventbus.async.maxsize}")
	private int maxEventBusPoolSize;
	@Value("${sky.api.pool.eventbus.async.queuecapacity}")
	private int EventBusPoolQueueCapacity;
	@Value("${sky.api.pool.eventbus.async.keepaliveseconds}")
	private int EventBusPoolKeepAliveSeconds;
	
	/**
	 * @return 接口事件总线事件执行线程池：所有的接口回调事件、接口超时事件等使用该线程池处理
	 */
	@Bean(name="asyncEventBusPool")
    public TaskExecutor asyncEventBusPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(coreEventBusPoolSize);
        executor.setMaxPoolSize(maxEventBusPoolSize);
        executor.setQueueCapacity(EventBusPoolQueueCapacity);
        executor.setKeepAliveSeconds(EventBusPoolKeepAliveSeconds);
        executor.setThreadNamePrefix("async-eventbus-pool-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
	}
	
	@Value("${sky.api.pool.apictrl.failretry.coresize}")
	private int coreApiFailRetryPoolSize;
	@Value("${sky.api.pool.apictrl.failretry.maxsize}")
	private int maxApiFailRetryPoolSize;
	@Value("${sky.api.pool.apictrl.failretry.queuecapacity}")
	private int apiFailRetryPoolQueueCapacity;
	@Value("${sky.api.pool.apictrl.failretry.keepaliveseconds}")
	private int apiFailRetryPoolKeepAliveSeconds;
	
	/**
	 * @return 接口失败重试任务执行线程池：所有的交易通知失败重试任务等使用该线程池处理
	 */
	@Bean(name="apiFailRetryPool")
    public TaskExecutor apiRetryBusPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(coreApiFailRetryPoolSize);
        executor.setMaxPoolSize(maxApiFailRetryPoolSize);
        executor.setQueueCapacity(apiFailRetryPoolQueueCapacity);
        executor.setKeepAliveSeconds(apiFailRetryPoolKeepAliveSeconds);
        executor.setThreadNamePrefix("apictrl-failretry-pool-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
	}
}
