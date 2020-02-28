package com.sky.base.lang.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.sky.base.lang.AbstractUtilsTest;
import com.sky.base.lang.time.DateTimeUtils;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-06
 */
public class DateTimeUtilsTest extends AbstractUtilsTest {

	@Test(expected = InvocationTargetException.class)
	public void testNewInstance() throws Exception {
		assertInvokeUtilityClassConstructor(DateTimeUtils.class, "Utility class");
	}

	@Test
	public void testFormat() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 11, 6);
		assertEquals("20181206", DateTimeUtils.format(calendar, DateTimeUtils.YYYYMMDD));
		calendar.set(2018, 11, 7);
		assertEquals("20181207", DateTimeUtils.format(calendar.getTime(), DateTimeUtils.YYYYMMDD));
		calendar.set(2018, 1, 8);
		assertEquals("20180208", DateTimeUtils.format(calendar.getTimeInMillis(), DateTimeUtils.YYYYMMDD));
		calendar.set(2018, 1, 8, 22, 30, 05);
		assertEquals("20180208", DateTimeUtils.format(calendar.getTimeInMillis(), DateTimeUtils.YYYYMMDD));
		assertEquals("2018-02-08 22:30:05",
				DateTimeUtils.format(calendar.getTimeInMillis(), DateTimeUtils.YYYY_MM_DD_HH_MM_SS));
		long oneSecond = 1000L;
		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis() + oneSecond);
		assertEquals("2018/02/08 22:30:06", DateTimeUtils.format(timestamp, "yyyy/MM/dd HH:mm:ss"));
	}

	@Test
	public void testParseDate() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 11, 6, 23, 44, 12);
		calendar.set(Calendar.MILLISECOND, 0);
		Date date = DateTimeUtils.parseDate("2018-12-06 23:44:12", DateTimeUtils.YYYY_MM_DD_HH_MM_SS);
		assertTrue(calendar.getTime().compareTo(date) == 0);
	}

	@Test
	public void testParseTimestamp() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 11, 6, 23, 44, 12);
		calendar.set(Calendar.MILLISECOND, 567);
		Timestamp timestamp = DateTimeUtils.parseTimestamp("2018-12-06 23:44:12.567",
				DateTimeUtils.YYYY_MM_DD_HH_MM_SS_SSS);
		assertTrue(calendar.getTime().getTime() == timestamp.getTime());
	}

	@Test
	public void testToCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 11, 6, 23, 44, 12);
		calendar.set(Calendar.MILLISECOND, 567);
		Calendar newCalendar = DateTimeUtils.toCalendar(calendar.getTime());
		assertTrue(calendar.compareTo(newCalendar) == 0);
	}

	@Test
	public void testGetCurrentTimestamp() {
		Timestamp timestamp = DateTimeUtils.getCurrentTimestamp();
		assertNotNull(timestamp);
	}

	@Test
	public void getDays() throws ParseException {
		String startDate = "2019-03-10";
		String endDate = "2019-03-19";
		System.out.println(DateTimeUtils.getDays(startDate,endDate));
	}
}
