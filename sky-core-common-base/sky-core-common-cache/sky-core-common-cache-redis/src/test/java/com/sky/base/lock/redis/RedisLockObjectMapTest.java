package com.sky.base.lock.redis;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.sky.base.lock.redis.RedisLockObjectMap;
import com.sky.base.test.util.AssertUtils;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-01-16
 */
public class RedisLockObjectMapTest {

    @Test
    public void test() throws InterruptedException {
        testConcurrent(10);
        testConcurrent(100);
        testConcurrent(1000);
    }

    private void testConcurrent(int threadNum) throws InterruptedException {
        Random random = new Random();
        List<Runnable> runnables = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            final String threadNo = String.valueOf(i);
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    String key = String.valueOf(random.nextInt(10));
                    assertNull(RedisLockObjectMap.put(key, threadNo));
                    assertEquals(threadNo, RedisLockObjectMap.get(key));
                    assertEquals(threadNo, RedisLockObjectMap.remove(key));
                }
            });
        }
        AssertUtils.assertConcurrent("Happen concurrent error", runnables, 10);
    }
}
