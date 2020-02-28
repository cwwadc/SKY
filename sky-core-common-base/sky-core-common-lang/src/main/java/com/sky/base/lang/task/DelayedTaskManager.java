package com.sky.base.lang.task;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.lang.time.DateTimeUtils;

public class DelayedTaskManager {
	private static final Logger logger = LoggerFactory.getLogger(DelayedTaskManager.class);
	private final DelayQueue<DelayedTask<?>> delayQueue = new DelayQueue<DelayedTask<?>>();
	
	private String name;
	private Executor executor;
	private final AtomicLong atomic = new AtomicLong(0);

	public long atomicNumber() {
		return atomic.getAndIncrement();
	}
	
	public DelayedTaskManager(String name, Executor executor) {
		this.name = name;
		this.executor = executor;
		if(this.executor == null) {
			this.executor = ForkJoinPool.commonPool();
		}
//		initialize();
	}
	
	public DelayedTaskManager() {
		if(executor == null) {
			this.executor = ForkJoinPool.commonPool();
		}
		initialize();
    }

	/**
	 * 后台检测执行线程
	 */
	private Thread daemonThread;

	/**
	 * 初始化守护线程 init()方法，要在容器初始化的时候就要执行， 或是在第一次put延迟对象任务之前就要初始化完成，
	 * 当设定的延迟时间到期时会执行任务对象中的run()
	 */
	public void initialize() {
		daemonThread = new Thread(() -> {
			try {
				execute();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("initialize " + this.name + " error", e);
			}
		});
		daemonThread.setDaemon(true);
		daemonThread.setName(this.name + "-daemon-thread");
		daemonThread.start();
	}

	private void execute() {
		logger.debug("{} execute start...", this.name);
		while (true) {
			int taskNum = delayQueue.size();
			try {
				// 从延迟队列中取值,如果无对象过期则队列一直等待
				// 如果Task中timeout大于当前的时间则可以通过take()取出到期的对象，如果没有则该队列一直等待
				DelayedTask<?> taskDelay = delayQueue.take();
				if (taskDelay != null) {
					logger.debug("fetch delayed task, task deadline -> {}, daemon thread alive -> {}.", DateTimeUtils.format(taskDelay.getDeadline(), "yyyy-MM-dd HH:mm:ss"), daemonThread.isAlive());
					Runnable task = taskDelay.getTask();
					if (task == null) {
						logger.warn("task runnable didnot set yet.");
						continue;
					}
					try {
						this.executor.execute(task);
					} catch (Exception e) {
						logger.error("execute delayed task error", e);
					}
				}
			} catch (Exception e) {
				logger.error(this.name + " execute error", e);
			}
		}
	}

	/**
	 * 向队列中添加任务，time task
	 *
	 * @param time 为延迟时间
	 * @param task 添加的任务
	 */
	public void register(DelayedTask<?> delayTask) {
		delayQueue.put(delayTask);
		logger.debug(
				"register delayed task, task id -> {}, deadline -> {}, daemon thread alive -> {}, current task count -> {}.",
				delayTask.getId(), DateTimeUtils.format(delayTask.getDeadline(), "yyyy-MM-dd HH:mm:ss"),
				daemonThread.isAlive(), delayQueue.size());
	}
	
	public void unregister(DelayedTask<?> delayTask) {
		delayQueue.remove(delayTask);
	}

	/**
	 * 从队列中获取任务 zhoukai7
	 */
	public Object[] get() {
		Object[] objArry = delayQueue.toArray();
		return objArry;
	}

}
