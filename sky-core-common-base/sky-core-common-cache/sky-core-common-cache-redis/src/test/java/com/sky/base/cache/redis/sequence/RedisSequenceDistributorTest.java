package com.sky.base.cache.redis.sequence;

import static com.sky.base.test.util.MatcherUtils.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import org.junit.Test;

import com.sky.base.cache.redis.AbstractRedisTest;
import com.sky.base.cache.redis.sequence.RedisSequenceDistributor;
import com.sky.base.test.util.AssertUtils;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-03-09
 */
public class RedisSequenceDistributorTest extends AbstractRedisTest {

	@Test
	public void testCurrent() {
		RedisSequenceDistributor sequenceDistributor = new RedisSequenceDistributor(redisClient);
		assertThat(sequenceDistributor.current("a1"), is(0L));
		redisClient.set("a2", 30L);
		assertThat(sequenceDistributor.current("a2"), is(30L));
	}

	@Test
	public void testNext() {
		RedisSequenceDistributor sequenceDistributor = new RedisSequenceDistributor(redisClient);
		assertThat(sequenceDistributor.next("a3"), is(1L));
		redisClient.set("a4", 40L);
		assertThat(sequenceDistributor.next("a4"), is(41L));
	}

	@Test
	public void testNextWithStepWidth() {
		RedisSequenceDistributor sequenceDistributor = new RedisSequenceDistributor(redisClient);
		assertThat(sequenceDistributor.next("a6", 20), is(20L));
		redisClient.set("a7", 40L);
		assertThat(sequenceDistributor.next("a7", 8), is(48L));
	}

	@Test
	public void testSingleConcurrentNext() throws InterruptedException {
		singleConcurrentNext(10);
		singleConcurrentNext(100);
		singleConcurrentNext(1000);
	}

	private void singleConcurrentNext(int threadNum) throws InterruptedException {
		final Set<Long> set = new CopyOnWriteArraySet<>();
		final RedisSequenceDistributor sequenceDistributor = new RedisSequenceDistributor(redisClient);
		List<Runnable> runnables = new ArrayList<>();
		for (int i = 0; i < threadNum; i++) {
			runnables.add(new Runnable() {
				@Override
				public void run() {
					long v = sequenceDistributor.next("a5");
					if (!set.add(v)) {
						throw new RuntimeException("Next value conflict, value : " + v);
					}
				}
			});
		}
		AssertUtils.assertConcurrent("Happen next value conflict", runnables, 10);
	}

	@Test
	public void testSingleConcurrentCurrent() throws InterruptedException {
		singleConcurrentCurrent(10);
		singleConcurrentCurrent(100);
		singleConcurrentCurrent(1000);
	}

	private void singleConcurrentCurrent(int threadNum) throws InterruptedException {
		final Set<Long> set = new CopyOnWriteArraySet<>();
		final RedisSequenceDistributor sequenceDistributor = new RedisSequenceDistributor(redisClient);
		redisClient.set("a11", 40L);
		set.add(40L);
		List<Runnable> runnables = new ArrayList<>();
		for (int i = 0; i < threadNum; i++) {
			runnables.add(new Runnable() {
				@Override
				public void run() {
					long v = sequenceDistributor.current("a11");
					if (!set.contains(v)) {
						throw new RuntimeException("Current value error, value : " + v);
					}
				}
			});
		}
		AssertUtils.assertConcurrent("Happen current value error", runnables, 10);
	}

	@Test
	public void testConcurrentNext() throws InterruptedException {
		concurrentNext(10);
		concurrentNext(100);
		concurrentNext(1000);
	}

	private void concurrentNext(int threadNum) throws InterruptedException {
		final Set<Long> set1 = new CopyOnWriteArraySet<>();
		final Set<Long> set2 = new CopyOnWriteArraySet<>();
		final RedisSequenceDistributor sequenceDistributor = new RedisSequenceDistributor(redisClient);
		List<Runnable> runnables = new ArrayList<>();
		for (int i = 0; i < threadNum; i++) {
			runnables.add(new Runnable() {
				@Override
				public void run() {
					long v = sequenceDistributor.next("a9");
					if (!set1.add(v)) {
						throw new RuntimeException("Next value conflict, value : " + v);
					}
				}
			});
			runnables.add(new Runnable() {
				@Override
				public void run() {
					long v = sequenceDistributor.next("a10");
					if (!set2.add(v)) {
						throw new RuntimeException("Next value conflict, value : " + v);
					}
				}
			});
		}
		AssertUtils.assertConcurrent("Happen next value conflict", runnables, 10);
	}

	@Test
	public void testConcurrentCurrent() throws InterruptedException {
		concurrentCurrent(10);
		concurrentCurrent(100);
		concurrentCurrent(1000);
	}

	private void concurrentCurrent(int threadNum) throws InterruptedException {
		final Set<Long> set1 = new CopyOnWriteArraySet<>();
		final Set<Long> set2 = new CopyOnWriteArraySet<>();
		final RedisSequenceDistributor sequenceDistributor = new RedisSequenceDistributor(redisClient);
		redisClient.set("a11", 40L);
		set1.add(40L);
		redisClient.set("a12", 50L);
		set2.add(50L);
		List<Runnable> runnables = new ArrayList<>();
		for (int i = 0; i < threadNum; i++) {
			runnables.add(new Runnable() {
				@Override
				public void run() {
					long v = sequenceDistributor.current("a11");
					if (!set1.contains(v)) {
						throw new RuntimeException("Current value error, value : " + v);
					}
				}
			});
			runnables.add(new Runnable() {
				@Override
				public void run() {
					long v = sequenceDistributor.current("a12");
					if (!set2.contains(v)) {
						throw new RuntimeException("Current value error, value : " + v);
					}
				}
			});
		}
		AssertUtils.assertConcurrent("Happen current value error", runnables, 10);
	}

	@Test
	public void testConcurrentNextWithStepWidth() throws InterruptedException {
		concurrentNextWithStepWidth(10);
		concurrentNextWithStepWidth(100);
		concurrentNextWithStepWidth(1000);
	}

	private void concurrentNextWithStepWidth(int threadNum) throws InterruptedException {
		final int stepWidth = 5;
		final Set<Long> set = new CopyOnWriteArraySet<>();
		final List<Long> list = new CopyOnWriteArrayList<>();
		final RedisSequenceDistributor sequenceDistributor = new RedisSequenceDistributor(redisClient);
		redisClient.set("a13", 40L);
		list.add(40L);
		List<Runnable> runnables = new ArrayList<>();
		for (int i = 0; i < threadNum; i++) {
			runnables.add(new Runnable() {
				@Override
				public void run() {
					long v = sequenceDistributor.next("a13", stepWidth);
					if (!set.add(v)) {
						throw new RuntimeException("Next value conflict, value : " + v);
					}
				}
			});
		}
		AssertUtils.assertConcurrent("Happen next value conflict", runnables, 10);
		for (int i = 0, size = list.size() - 1; i < size; i++) {
			assertThat(Math.abs(list.get(i) - list.get(i + 1)) % 5, is(0));
		}
	}

}
