package com.sky.base.test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sky.base.test.common.TestExecutor;

/**
 * @Title 断言工具类
 * @Description 用于单元测试
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-13
 */
public class AssertUtils {

	/**
	 * 多线程并发断言
	 * 
	 * @param message           错误消息
	 * @param runnables         线程组
	 * @param maxTimeoutSeconds 最大超时时间, 单位秒
	 * @throws InterruptedException
	 */
	public static void assertConcurrent(final String message, final List<? extends Runnable> runnables,
	        final int maxTimeoutSeconds) throws InterruptedException {
		final int numThreads = runnables.size();
		final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
		final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("assert-pool-%d").build();
		final ExecutorService threadPool = new ThreadPoolExecutor(numThreads, numThreads, 0L, TimeUnit.MILLISECONDS,
		        new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
		try {
			final CountDownLatch allExecutorThreadsReady = new CountDownLatch(numThreads);
			final CountDownLatch afterInitBlocker = new CountDownLatch(1);
			final CountDownLatch allDone = new CountDownLatch(numThreads);
			for (final Runnable submittedTestRunnable : runnables) {
				threadPool.submit(new Runnable() {
					@Override
					public void run() {
						allExecutorThreadsReady.countDown();
						try {
							afterInitBlocker.await();
							submittedTestRunnable.run();
						} catch (final Throwable e) {
							e.printStackTrace();
							exceptions.add(e);
						} finally {
							allDone.countDown();
						}
					}
				});
			}
			// wait until all threads are ready
			assertTrue(
			        "Timeout initializing threads! Perform long lasting initializations before passing runnables to assertConcurrent",
			        allExecutorThreadsReady.await(runnables.size() * 10L, TimeUnit.MILLISECONDS));
			// start all test runners
			afterInitBlocker.countDown();
			assertTrue(message + " timeout! More than " + maxTimeoutSeconds + " seconds",
			        allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS));
		} finally {
			threadPool.shutdownNow();
		}
		assertTrue(message + " failed with exception(s) " + exceptions, exceptions.isEmpty());
	}

	/**
	 * 异常断言
	 * 
	 * @param executor       执行器
	 * @param throwableClass 异常类型
	 * @param errorMsg       错误信息
	 */
	public static <T extends Throwable> void assertException(TestExecutor executor, Class<T> throwableClass,
	        String errorMsg) {
		try {
			executor.execute();
			fail("Expected happen " + throwableClass.getName());
		} catch (Throwable e) {
			assertTrue(throwableClass.isAssignableFrom(e.getClass()));
			assertEquals(errorMsg, e.getMessage());
		}
	}
}