package com.sky.base.cache.redis.handle.impl;

import com.sky.base.cache.redis.OperatorTest;
import com.sky.base.cache.redis.bean.MockObjectTest;
import com.sky.base.cache.redis.constants.MockConstantsTest;
import com.sky.base.cache.redis.handle.HashOperator;
import com.sky.base.cache.redis.handle.Operator;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author zzq
 * @version v1.0
 * @Title
 * @Description
 * @date 2018/12/12
 */
public class BaseHashOperatorTest extends OperatorTest {

    private HashOperator hashOperator;
    private Operator operator;

    @Before
    public void before() {
        super.before();
        hashOperator = getRedisSupport().hashOperator();
        operator = getRedisSupport().baseOperator();
    }

    @After
    public void after() {
        Set<String> keys = keys();
        del(keys);
    }

    @Test
    @Ignore
    public void testHashOperatorTest() {
        String key = MockConstantsTest.REDIS_KEY_HASH;
        set(key);
        set1(key);
        set2(key);
        get(key);
        get1(key);
        set3(key);
        get2(key);
        get3(key);
        getMapKeys(key);
        getMapValues(key);
        del(key);
        exists(key);
    }

    private void set(String key) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("test1", "test1");
        map.put("test2", "test2");
        assertTrue(hashOperator.set(key + "1", map));
    }

    private void set1(String key) {
        Map<String, MockObjectTest> map = new LinkedHashMap<>();
        map.put("test1", MockObjectTest.newInstance());
        map.put("test2", MockObjectTest.newInstance());
        assertTrue(hashOperator.setT(key + "2", map));
    }

    private void set2(String key) {
        assertEquals("1", String.valueOf(hashOperator.set(key + "1", "test3", "test3")));
        assertEquals("0", String.valueOf(hashOperator.set(key + "1", "test2", "test2")));
    }

    private void set3(String key) {
        assertEquals("1", String.valueOf(hashOperator.set(key + "2", "test3", MockObjectTest.newInstance())));
        assertEquals("0", String.valueOf(hashOperator.set(key + "2", "test2", MockObjectTest.newInstance())));
    }

    private void get(String key) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("test1", "test1");
        map.put("test2", "test2");
        map.put("test3", "test3");
        assertEquals(map, hashOperator.get(key + "1"));
    }

    private void get1(String key) {
        Map<String, MockObjectTest> map = new LinkedHashMap<>();
        map.put("test1", MockObjectTest.newInstance());
        map.put("test2", MockObjectTest.newInstance());
        assertEquals(map, hashOperator.get(key + "2", MockObjectTest.class));
    }

    private void get2(String key) {
        assertEquals("test1", hashOperator.get(key + "1", "test1"));
    }

    private void get3(String key) {
        assertEquals(MockObjectTest.newInstance(), hashOperator.get(key + "2", "test1", MockObjectTest.class));
    }

    private void getMapKeys(String key) {
        Set<String> mapKeys = hashOperator.getMapKeys(key + "1");
        System.out.println(mapKeys);
        Set<String> mapKeys2 = hashOperator.getMapKeys(key + "1");
        System.out.println(mapKeys2);
    }

    private void getMapValues(String key) {
        List<String> mapValues = hashOperator.getMapValues(key + "1");
        System.out.println(mapValues);
        List<MockObjectTest> mapValues1 = hashOperator.getMapValues(key + "2", MockObjectTest.class);
        System.out.println(mapValues1);
    }

    private void del(String key) {
        assertTrue(hashOperator.del(key + "1", "test1"));
        assertFalse(hashOperator.del(key + "3", "test1"));
    }

    private void exists(String key) {
        assertTrue(hashOperator.exists(key + "1", "test2"));
        assertFalse(hashOperator.exists(key + "1", "test1"));
    }

    private Set<String> keys() {
        Set<String> keys = operator.keys(MockConstantsTest.REDIS_KEY_HASH + "*");
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