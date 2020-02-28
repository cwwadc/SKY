package com.sky.base.cache.redis.handle.impl;

import com.sky.base.cache.redis.OperatorTest;
import com.sky.base.cache.redis.bean.MockObjectTest;
import com.sky.base.cache.redis.constants.MockConstantsTest;
import com.sky.base.cache.redis.handle.ListOperator;
import com.sky.base.cache.redis.handle.Operator;
import com.sky.base.cache.redis.handle.impl.BaseListOperator;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author zzq
 * @version v1.0
 * @Title
 * @Description
 * @date 2018/12/12
 */
public class BaseListOperatorTest extends OperatorTest {

    private ListOperator listOperator;
    private Operator operator;

    @Before
    public void before() {
        super.before();
        listOperator = getRedisSupport().listOperator();
        operator = getRedisSupport().baseOperator();
    }

    @After
    public void after() {
        Set<String> keys = keys();
        del(keys);
    }

    @Test
    @Ignore
    public void testListOperator() {
        String key = MockConstantsTest.REDIS_KEY_LIST;
        set(key);
        get(key);
        set1(key);
        get1(key);
        get2(key);
        get3(key);
        size(key);
        set2(key);
        set3(key);
        del(key);
    }

    private void set(String key) {
        List<String> lists = new ArrayList<>();
        lists.add("1");
        lists.add("2");
        lists.add("3");
        assertEquals(3L,(long)listOperator.set(key + "1", lists));
    }

    private void get(String key) {
        List<String> lists = new ArrayList<>();
        lists.add("1");
        lists.add("2");
        lists.add("3");
        assertEquals(lists, listOperator.get(key + "1"));
    }

    private void set1(String key) {
        List<MockObjectTest> lists = new ArrayList<>();
        lists.add(MockObjectTest.newInstance());
        lists.add(MockObjectTest.newInstance());
        lists.add(MockObjectTest.newInstance());
        assertEquals(3L,(long)listOperator.setT(key + "2", lists));
    }

    private void get1(String key) {
        List<MockObjectTest> lists = new ArrayList<>();
        lists.add(MockObjectTest.newInstance());
        lists.add(MockObjectTest.newInstance());
        lists.add(MockObjectTest.newInstance());
        assertEquals(lists, listOperator.get(key + "2", MockObjectTest.class));

    }

    private void get2(String key) {
        assertEquals("2", listOperator.get(key + "1", 1));
    }

    private void get3(String key) {
        assertEquals(MockObjectTest.newInstance(), listOperator.get(key + "2", MockObjectTest.class, 1));
    }

    private void size(String key) {
        assertEquals(3, (long) listOperator.size(key + "1"));
    }

    private void set2(String key) {
         assertEquals(4L,(long)listOperator.set(key + "1", "4", BaseListOperator.Direction.LEFT));
         assertEquals(5L,(long)listOperator.set(key + "1", "0", BaseListOperator.Direction.RIGHT));
    }

    private void set3(String key) {
         assertEquals(4L,(long)listOperator.set(key + "2", MockObjectTest.class, BaseListOperator.Direction.LEFT));
         assertEquals(5L,(long)listOperator.set(key + "2", MockObjectTest.class, BaseListOperator.Direction.RIGHT));
    }

    private void del(String key) {
        assertEquals("4", listOperator.del(key + "1", BaseListOperator.Direction.LEFT));
        assertEquals("0", listOperator.del(key + "1", BaseListOperator.Direction.RIGHT));
    }

    private Set<String> keys() {
        Set<String> keys = operator.keys(MockConstantsTest.REDIS_KEY_LIST + "*");
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