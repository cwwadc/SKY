package com.sky.base.lock.redis;

import static com.sky.base.test.util.MatcherUtils.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sky.base.cache.redis.client.RedisClient;
import com.sky.base.cache.redis.client.impl.RedisClientImpl;
import com.sky.base.cache.redis.config.RedisProperties;
import com.sky.base.lock.redis.RedisDistributedLock;
import com.sky.base.lock.redis.RedisLockObjectMap;
import com.sky.base.test.matcher.NotLessThanMatcher;
import com.sky.base.test.net.NetUtils;
import com.sky.base.test.util.AssertUtils;

import redis.embedded.RedisServer;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-01-16
 */
public class RedisDistributedLockTest {

	private static RedisServer redisServer;
	private static RedisDistributedLock lock;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		int port = NetUtils.getAvailablePort();
		redisServer = new RedisServer(port);
		redisServer.start();
		RedisProperties properties = new RedisProperties();
		properties.setHost("localhost");
		properties.setPort(port);
		RedisClient redisClient = new RedisClientImpl(properties);
		lock = new RedisDistributedLock(redisClient, 10L, 60 * 1000L);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		redisServer.stop();
	}

	@Test
	public void testRedisDistributedLockConstructorNoArgs() {
		RedisDistributedLock lock = new RedisDistributedLock(new MockRedisClient());

		assertNotNull(lock.getRedisClient());
		assertEquals(RedisDistributedLock.DEFAULT_RETRY_AWAIT_TIME, lock.getRetryAwaitTime());
		assertEquals(RedisDistributedLock.DEFAULT_EXPIRE_TIME, lock.getExpireTime());
		assertEquals(RedisDistributedLock.DEFAULT_TIME_UNIT, lock.getTimeUnit());
	}

	@Test
	public void testRedisDistributedLockConstructorThreeArgs() {
		long expectedRetryAwaitTime = 1000L;
		long expectedExpireTime = 10 * 60 * 1000L;

		RedisDistributedLock lock = new RedisDistributedLock(new MockRedisClient(), expectedRetryAwaitTime,
		        expectedExpireTime);

		assertNotNull(lock.getRedisClient());
		assertEquals(expectedRetryAwaitTime, lock.getRetryAwaitTime());
		assertEquals(expectedExpireTime, lock.getExpireTime());
		assertEquals(RedisDistributedLock.DEFAULT_TIME_UNIT, lock.getTimeUnit());
	}

	@Test
	public void testRedisDistributedLockConstructorFourArgs() {
		long expectedRetryAwaitTime = 1L;
		long expectedExpireTime = 10 * 60L;
		TimeUnit expectedTimeUnit = TimeUnit.SECONDS;

		RedisDistributedLock lock = new RedisDistributedLock(new MockRedisClient(), expectedRetryAwaitTime,
		        expectedExpireTime, expectedTimeUnit);

		assertNotNull(lock.getRedisClient());
		assertEquals(expectedRetryAwaitTime, lock.getRetryAwaitTime());
		assertEquals(expectedExpireTime, lock.getExpireTime());
		assertEquals(expectedTimeUnit, lock.getTimeUnit());
	}

	@Test
	public void testTryLock() throws InterruptedException {
		String key = "ursa-base-lock-testTryLock";
		testTryLockConcurrent(10, lock, key);
        testTryLockConcurrent(100, lock, key);
        testTryLockConcurrent(200, lock, key);
	}

	private void testTryLockConcurrent(long threadNum, RedisDistributedLock lock, String key)
	        throws InterruptedException {
		lock.getRedisClient().set(key, String.valueOf(System.currentTimeMillis() + 10));
		doSomething(50);
		assertTrue(lock.getRedisClient().exists(key));

		List<Runnable> runnables = new ArrayList<>();
		Set<String> success = new CopyOnWriteArraySet<String>();
		AtomicLong counter = new AtomicLong(0);

		for (long i = 0; i < threadNum; i++) {
			final String threadNo = String.valueOf(i);
			runnables.add(new Runnable() {
				@Override
				public void run() {
					if (lock.tryLock(key)) {
						try {
							assertTrue(success.add(threadNo));
							while (counter.get() != (threadNum - 1)) {
								doSomething(20);
							}
						} finally {
							lock.unLock(key);
							assertFalse(lock.getRedisClient().exists(key));
							assertThat(success.size(), is(1));
							assertTrue(success.contains(threadNo));
						}
					} else {
						counter.incrementAndGet();
					}
				}

			});
		}
		AssertUtils.assertConcurrent("Happen concurrent error", runnables, 10);
	}

	private static void doSomething(long milliseconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	@Test
	public void testTryLockWithTimeout() throws InterruptedException {
		String key = "ursa-base-lock-testTryLockWithTimeout";
		testTryLockWithTimeoutConcurrent(10, lock, key, 800L);
		testTryLockWithTimeoutConcurrent(100, lock, key, 800L);
		testTryLockWithTimeoutConcurrent(200, lock, key, 800L);
	}

	private void testTryLockWithTimeoutConcurrent(long threadNum, RedisDistributedLock lock, String key, long timeout)
	        throws InterruptedException {
		List<Runnable> runnables = new ArrayList<>();
		Set<String> currentLock = new CopyOnWriteArraySet<>();
		Set<String> successLock = new CopyOnWriteArraySet<>();
		AtomicLong counter = new AtomicLong(0);
		for (long i = 0; i < threadNum; i++) {
			final String threadNo = String.valueOf(i);
			runnables.add(new Runnable() {
				@Override
				public void run() {
					if (lock.tryLock(key, timeout)) {
						try {
							assertTrue(currentLock.add(threadNo));
							assertTrue(successLock.add(threadNo));
							doSomething(100);
							assertThat(currentLock.size(), is(1));
							assertTrue(currentLock.remove(threadNo));
						} finally {
							lock.unLock(key);
						}
					} else {
						counter.incrementAndGet();
					}
				}

			});
		}
		AssertUtils.assertConcurrent("Happen concurrent error", runnables, 10);
		assertThat(counter.get(), is(threadNum - successLock.size()));
		assertThat(successLock.size(), new NotLessThanMatcher(1));
	}

	@Test
	public void testLock() throws InterruptedException {
		String key = "ursa-base-lock-testLock";
		testLockConcurrent(10, lock, key);
		testLockConcurrent(100, lock, key);
		testLockConcurrent(200, lock, key);
	}

	private void testLockConcurrent(long threadNum, RedisDistributedLock lock, String key) throws InterruptedException {
		List<Runnable> runnables = new ArrayList<>();
		Set<String> success = new CopyOnWriteArraySet<>();
		AtomicLong counter = new AtomicLong(0);

		for (long i = 0; i < threadNum; i++) {
			final String threadNo = String.valueOf(i);
			runnables.add(new Runnable() {
				@Override
				public void run() {
					assertTrue(lock.lock(key));
					try {
						assertTrue(success.add(threadNo));
						doSomething(20);
						assertThat(success.size(), is(1));
						assertTrue(success.remove(threadNo));
					} finally {
						assertTrue(lock.unLock(key));
						counter.incrementAndGet();
					}
				}
			});
		}
		AssertUtils.assertConcurrent("Happen concurrent error", runnables, 30);
		assertThat(counter.get(), is(threadNum));
	}

	@Test
	public void testUnLock() throws InterruptedException {
		String key = "ursa-base-lock-testUnLock";
		if (lock.tryLock(key)) {
			RedisLockObjectMap.remove(key);
			assertFalse(lock.unLock(key));
			assertTrue(lock.getRedisClient().exists(key));
			assertThat(lock.getRedisClient().del(key), is(1L));
		} else {
			fail("expected lock");
		}
	}

}
