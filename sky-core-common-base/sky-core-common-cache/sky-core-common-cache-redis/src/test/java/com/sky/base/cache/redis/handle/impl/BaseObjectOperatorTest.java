package com.sky.base.cache.redis.handle.impl;

import com.sky.base.cache.redis.OperatorTest;
import com.sky.base.cache.redis.bean.MockObjectTest;
import com.sky.base.cache.redis.constants.MockConstantsTest;
import com.sky.base.cache.redis.handle.ObjectOperator;
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
public class BaseObjectOperatorTest extends OperatorTest {

    private ObjectOperator objectOperator;
    private Operator operator;

    @Before
    public void before() {
        super.before();
        objectOperator = getRedisSupport().objectOperator();
        operator = getRedisSupport().baseOperator();
    }

    @After
    public void after() {
        Set<String> keys = keys();
        del(keys);
    }

    @Test
    @Ignore
    public void testObjectOperator() {
        String key = MockConstantsTest.REDIS_KEY_OBJECT + "1";
        set(key);
        set1(key);
        set3(key);
        set4(key);
        get(key);
        getSet(key);
    }

    private void set(String key) {
        assertTrue(objectOperator.set(key, MockObjectTest.newInstance()));
    }

    private void set1(String key) {
        assertTrue(objectOperator.set(key, MockObjectTest.newInstance(), 100));
    }

    private void set3(String key) {
        assertFalse(objectOperator.set(key, MockObjectTest.newInstance(), true));
        assertTrue(objectOperator.set(key, MockObjectTest.newInstance(), false));
        assertTrue(objectOperator.set(key + System.currentTimeMillis() + RandomStringUtils.randomNumeric(10),
                MockObjectTest.newInstance(), true));
        assertTrue(objectOperator.set(key + System.currentTimeMillis() + RandomStringUtils.randomNumeric(10),
                MockObjectTest.newInstance(), false));
    }

    private void set4(String key) {
        assertTrue(objectOperator.set(key, MockObjectTest.newInstance(), 100));
    }

    private void get(String key) {
        assertEquals(MockObjectTest.newInstance(), objectOperator.get(key, MockObjectTest.class));
    }

    private void getSet(String key) {
        assertEquals(MockObjectTest.newInstance(), objectOperator.get(key, MockObjectTest.class));
    }

    private Set<String> keys() {
        Set<String> keys = operator.keys(MockConstantsTest.REDIS_KEY_OBJECT + "*");
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