package com.sky.base.test.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * @Title 字符串数组包含匹配器
 * @Description 用于单元测试
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-13
 */
public class StringArrayContainMatcher extends BaseMatcher<String[]> {

    private String[] actual;

    private String[] expected;

    public StringArrayContainMatcher(String[] expected) {
        super();
        this.expected = expected;
    }

    @Override
    public boolean matches(Object item) {
        actual = (String[]) item;
        for (String e : expected) {
            boolean isContain = false;
            for (String a : actual) {
                if (a.equals(e)) {
                    isContain = true;
                    break;
                }
            }
            if (!isContain) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("actual string [" + actual + "] not contain expected string [" + expected + "]");
    }

}
