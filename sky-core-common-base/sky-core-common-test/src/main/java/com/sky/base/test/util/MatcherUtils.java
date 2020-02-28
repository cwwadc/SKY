package com.sky.base.test.util;

import org.hamcrest.Matcher;

/**
 * @Title 匹配器工具类
 * @Description 用于单元测试
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-13
 */
public class MatcherUtils {

    /**
     * is(equalTo(value))
     * @param value
     * @return
     */
    public static <T> Matcher<T> is(T value) {
        return org.hamcrest.CoreMatchers.is(value);
    }

}