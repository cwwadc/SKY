package com.sky.base.cache.redis.handle.impl;

import com.sky.base.cache.redis.OperatorTest;
import com.sky.base.cache.redis.constants.MockConstantsTest;
import com.sky.base.cache.redis.handle.Operator;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;
import static org.junit.Assert.*;

/**
 * @author zzq
 * @version v1.0
 * @Title
 * @Description
 * @date 2018/12/12
 */
public class BaseOperatorTest extends OperatorTest {

    private Operator operator;

    @Before
    public void before() {
        super.before();
        operator = getRedisSupport().baseOperator();
    }

    @After
    public void after() {
        Set<String> keys = keys();
        del(keys);
    }

    @Test
    @Ignore
    public void testOperator() {
        incr();
        incr1();
        decr();
        decr1();
        Set<String> keys = keys();
        String key = keys.iterator().next();
        type(key);
        exists(key);
        expire(key);
        lifeTime(key);
        persist(key);
    }

    private void incr() {
        assertEquals("1", operator.incr(MockConstantsTest.REDIS_KEY + System.currentTimeMillis()));
    }

    private void incr1() {
        assertEquals("2", operator.incr(MockConstantsTest.REDIS_KEY + System.currentTimeMillis(), 2));
    }

    private void decr() {
        assertEquals("-1", operator.decr(MockConstantsTest.REDIS_KEY + System.currentTimeMillis()));
    }

    private void decr1() {
        assertEquals("-2", operator.decr(MockConstantsTest.REDIS_KEY + System.currentTimeMillis(), 2));
    }

    private void type(String key) {
        assertEquals("string", operator.type(key));
    }

    private void exists(String key) {
        assertTrue(operator.exists(key));
        assertFalse(operator.exists(String.valueOf(System.currentTimeMillis() + RandomStringUtils.randomNumeric(10))));
    }

    private void expire(String key) {
        assertTrue(operator.expire(key, 100));
        assertFalse(operator.expire(String.valueOf(System.currentTimeMillis() + RandomStringUtils.randomNumeric(10)), 100));
    }

    private void lifeTime(String key) {
        assertEquals("100", String.valueOf(operator.lifeTime(key)));
    }

    private void persist(String key) {
        assertTrue(operator.persist(key));
        assertFalse(operator.persist(String.valueOf(System.currentTimeMillis() + RandomStringUtils.randomNumeric(10))));
    }

    private Set<String> keys() {
        Set<String> keys = operator.keys(MockConstantsTest.REDIS_KEY + "*");
        System.out.println(String.format("keys[%s]:%s", MockConstantsTest.REDIS_KEY, keys));
        for (String key : keys) {
            assertTrue(key.contains(MockConstantsTest.REDIS_KEY));
        }
        return keys;
    }

    private void del(Set<String> keys) {
        assertEquals(String.valueOf(keys.size()), String.valueOf(operator.del(keys.toArray(new String[]{}))));
    }
}