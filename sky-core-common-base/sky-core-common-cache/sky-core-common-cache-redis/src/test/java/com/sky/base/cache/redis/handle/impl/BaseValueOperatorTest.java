package com.sky.base.cache.redis.handle.impl;

import com.sky.base.cache.redis.OperatorTest;
import com.sky.base.cache.redis.constants.MockConstantsTest;
import com.sky.base.cache.redis.handle.Operator;
import com.sky.base.cache.redis.handle.ValueOperator;

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
public class BaseValueOperatorTest extends OperatorTest {

    private ValueOperator valueOperator;
    private Operator operator;

    @Before
    public void before() {
        super.before();
        valueOperator = getRedisSupport().valueOperator();
        operator = getRedisSupport().baseOperator();
    }

    @After
    public void after() {
        Set<String> keys = keys();
        del(keys);
    }

    @Test
    @Ignore
    public void testValueOperator() {
        String key = MockConstantsTest.REDIS_KEY_STRING + "1";
        set(key);
        set1(key);
        set2(key);
        get(key);
        append(key);
        getSet(key);
    }

    private void set(String key) {
        assertTrue(valueOperator.set(key, "test"));
    }

    private void set1(String key) {
        assertFalse(valueOperator.set(key, "test", true));
        assertTrue(valueOperator.set(key + System.currentTimeMillis() + RandomStringUtils.randomNumeric(10),
                "test", true));
        assertTrue(valueOperator.set(key, "test", false));
        assertTrue(valueOperator.set(key + System.currentTimeMillis() + RandomStringUtils.randomNumeric(10),
                "test", false));
    }

    private void set2(String key) {
        assertTrue(valueOperator.set(key, "test", 100));
    }

    private void get(String key) {
        assertEquals("test", valueOperator.get(key));
    }

    private void getSet(String key) {
        assertEquals("test1", valueOperator.getSet(key, "test2"));
    }


    private void append(String key) {
        assertEquals("5", String.valueOf(valueOperator.append(key, "1")));
    }

    private Set<String> keys() {
        Set<String> keys = operator.keys(MockConstantsTest.REDIS_KEY_STRING + "*");
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