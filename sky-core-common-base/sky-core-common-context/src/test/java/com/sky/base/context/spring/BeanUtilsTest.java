package com.sky.base.context.spring;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sky.base.context.spring.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zzq
 * @version 1.0.0
 * @Title {@link}
 * @Description
 * @date 2019/2/15
 */
public class BeanUtilsTest {

    private List<MockTest1> list1 = new ArrayList<>();
    private List<MockTest2> list2 = new ArrayList<>();

    @Before
    public void before() {
        list1.add(new MockTest1("1", "1"));
        list1.add(new MockTest1("2", "2"));
        list1.add(new MockTest1("3", "3"));
        list1.add(new MockTest1("4", "4"));
        list1.add(new MockTest1("5", "5"));
    }


    @Test
    public void copyProperties() {
        BeanUtils.copyProperties(list1, list2, MockTest2.class);
        System.out.println(list2);
    }

    @Test
    public void copyProperties1() {
        BeanUtils.copyProperties(list1, list2, MockTest2.class, "a");
        System.out.println(list2);
    }


    @Test
    public void beanToMap() {
        Map<String, Object> map = new HashMap<>();
        MockTest1 mockTest1 = new MockTest1("1", "1");
        BeanUtils.beanToMap(mockTest1, map);
        Assert.assertEquals("1", map.get("a"));
        Assert.assertEquals("1", map.get("b"));
    }

    @Test
    public void beanToMapByList() {
        List<MockTest1> lists = new ArrayList<>();
        lists.add(new MockTest1("1", "1"));
        List<Map<String, Object>> maps = new ArrayList<>();
        BeanUtils.beanToMapByList(lists, maps);
        Assert.assertEquals(1, maps.size());
        Assert.assertEquals("1", maps.get(0).get("a"));
        Assert.assertEquals("1", maps.get(0).get("b"));
    }
}