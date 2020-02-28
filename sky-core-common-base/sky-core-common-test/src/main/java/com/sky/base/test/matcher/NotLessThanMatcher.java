package com.sky.base.test.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * @Title 数字比较匹配器，不小于
 * @Description 用于单元测试
 * @author lizp
 * @version 1.0.0
 * @date 2019-01-17
 */
public class NotLessThanMatcher extends BaseMatcher<Number> {

	private Number actual;

	private Number expected;

	public NotLessThanMatcher(Number expected) {
		super();
		this.expected = expected;
	}

	@Override
	public boolean matches(Object item) {
		actual = (Number) item;
		if (Integer.class.isAssignableFrom(item.getClass())) {
			return actual.intValue() >= expected.intValue();
		}
		if (Long.class.isAssignableFrom(item.getClass())) {
			return actual.longValue() >= expected.longValue();
		}
		if (Float.class.isAssignableFrom(item.getClass())) {
			return actual.floatValue() >= expected.floatValue();
		}
		if (Double.class.isAssignableFrom(item.getClass())) {
			return actual.doubleValue() >= expected.doubleValue();
		}
		if (Byte.class.isAssignableFrom(item.getClass())) {
			return actual.byteValue() >= expected.byteValue();
		}
		if (Short.class.isAssignableFrom(item.getClass())) {
			return actual.shortValue() >= expected.shortValue();
		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("actual number [" + actual + "] less than expected number  [" + expected + "]");
	}

}
