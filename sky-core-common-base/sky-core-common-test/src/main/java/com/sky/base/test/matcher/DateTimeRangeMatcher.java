package com.sky.base.test.matcher;

import java.sql.Timestamp;
import java.text.ParseException;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * @Title 日期范围匹配器
 * @Description 用于单元测试
 * @author lizp
 * @version 1.0.0
 * @date 2019-03-02
 */
public class DateTimeRangeMatcher extends BaseMatcher<Timestamp> {

	private Timestamp actual;
	private String expectedBeginInterval;
	private String expectedEndInterval;
	private String format;

	public DateTimeRangeMatcher(String expectedBeginInterval, String expectedEndInterval, String format) {
		super();
		this.expectedBeginInterval = expectedBeginInterval;
		this.expectedEndInterval = expectedEndInterval;
		this.format = format;
	}

	@Override
	public boolean matches(Object item) {
		actual = (Timestamp) item;
		try {
			long actualTime = DateUtils.parseDate(DateFormatUtils.format(actual.getTime(), format), format).getTime();
			return DateUtils.parseDate(expectedBeginInterval, format).getTime() <= actualTime
			        && actualTime <= DateUtils.parseDate(expectedEndInterval, format).getTime();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("actual Timestamp [" + actual + "] not in the expected range [" + expectedBeginInterval
		        + "] - [" + expectedEndInterval + "]");
	}

}
