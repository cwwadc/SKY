package com.sky.base.test.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * @Title  字符串包含匹配器
 * @Description 用于单元测试
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-13
 */
public class StringContainMatcher extends BaseMatcher<String> {

    private String actual;

    private String expected;

    public StringContainMatcher(String expected) {
        super();
        this.expected = expected;
    }

    @Override
    public boolean matches(Object item) {
        actual = (String) item;
        return actual.contains(expected);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("actual string [" + actual + "] not contain expected string [" + expected + "]");
    }

}
