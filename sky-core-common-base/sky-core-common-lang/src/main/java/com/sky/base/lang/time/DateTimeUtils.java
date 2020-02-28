package com.sky.base.lang.time;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.lang.string.StringUtils;

/**
 * @author dengny
 * @version 1.0.0
 * @Title 时间日期工具类
 * @Description
 * @date 2018-12-12
 */
public final class DateTimeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeUtils.class);

    /**
     * 日期格式 : yyyy-MM-ddTHH:mm:ss
     */
    public static final String ISO_8601_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";
    /**
     * 日期格式 : yyyy-MM-dd
     */
    public static final String ISO_8601_DATE = "yyyy-MM-dd";
    /**
     * 时间格式 : HH:mm:ss
     */
    public static final String ISO_8601_TIME = "HH:mm:ss";
    /**
     * 时间格式 : yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式 : yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 时间格式 : yyyyMMddHHmmss
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /**
     * 时间格式 : yyyyMMddHHmmssSSS
     */
    public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    /**
     * 日期格式 : yyyyMMdd
     */
    public static final String YYYYMMDD = "yyyyMMdd";
    /**
     * 时间格式 : HHmmss
     */
    public static final String HHMMSS = "HHmmss";
    /**
     * 时间格式 : HH:mm
     */
    public static final String TIME_WITH_MINUTE_PATTERN = "HH:mm";

    public static final long DAY_MILLI = 86400000L;

    public static final int LEFT_OPEN_RIGHT_OPEN = 1;

    /**
     * 一年的月份数 12
     */
    public static final int MONTH_NUMBER = 12;

    private DateTimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 日期格式化
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return 格式化后日期字符串
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 时间戳格式化
     *
     * @param millis  时间戳
     * @param pattern 时间格式
     * @return 格式化后时间字符串
     */
    public static String format(long millis, String pattern) {
        return DateFormatUtils.format(millis, pattern);
    }

    /**
     * 日历对象格式化
     *
     * @param calendar 日历对象
     * @param pattern  时间格式
     * @return 格式化后时间字符串
     */
    public static String format(Calendar calendar, String pattern) {
        return DateFormatUtils.format(calendar, pattern);
    }

    /**
     * 时间戳格式化
     *
     * @param timestamp 时间戳对象
     * @param pattern   时间格式
     * @return 格式化后时间字符串
     */
    public static String format(Timestamp timestamp, String pattern) {
        return format(timestamp.getTime(), pattern);
    }

    /**
     * 解析日期字符串
     *
     * @param str     时间字符串
     * @param pattern 解析格式
     * @return 日期对象
     */
    public static Date parseDate(String str, String pattern) {
        if (StringUtils.isBlank(str)) {
            throw new NullPointerException("date string == null");
        }
        if (StringUtils.isBlank(pattern)) {
            throw new NullPointerException("pattern == null");
        }

        try {
            return DateUtils.parseDate(str, pattern);
        } catch (ParseException e) {
            LOGGER.error("Parse date string error", e);
            throw new IllegalArgumentException(
                    "Parse date string " + str + " error, please check whether the input date format is " + pattern);
        }
    }

    /**
     * 解析时间字符串
     *
     * @param str     时间字符串
     * @param pattern 解析格式
     * @return 时间戳对象
     */
    public static Timestamp parseTimestamp(String str, String pattern) {
        return new Timestamp(parseDate(str, pattern).getTime());
    }

    /**
     * 获取当前时间戳对象
     *
     * @return 时间戳对象
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(currentTimeMillis());
    }

    /**
     * 日期对象转日历对象
     *
     * @param date 日期对象
     * @return 日历对象
     */
    public static Calendar toCalendar(Date date) {
        return DateUtils.toCalendar(date);
    }

    /**
     * 获取当前系统时间
     *
     * @return long类型
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 日期字符串转Timestamp对象
     *
     * @param dateStr 日期字符串 格式:yyyy-mm-dd hh:mm:ss[.fffffffff]
     * @return
     */
    public static Timestamp strToTimestamp(String dateStr) {
        return Timestamp.valueOf(dateStr);
    }

    /**
     * 根据日期增加指定年数值
     *
     * @param date  日期
     * @param years 增加的年数值
     * @return 增加年数值后的日期
     */
    public static Date addYears(Date date, long years) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return toDate(localDateTime.plusYears(years));
    }

    /**
     * 根据日期增加指定月数值
     *
     * @param date   日期
     * @param months 增加的月数值
     * @return 增加月数值后的日期
     */
    public static Date addMonths(Date date, long months) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return toDate(localDateTime.plusMonths(months));
    }

    /**
     * 根据日期增加指定天数值
     *
     * @param date 日期
     * @param days 增加的天数值
     * @return 增加天数值后的日期
     */
    public static Date addDays(Date date, long days) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return toDate(localDateTime.plusDays(days));
    }

    /**
     * 根据日期增加指定小时数值
     *
     * @param date  日期
     * @param hours 增加的小时数值
     * @return 增加小时数值后的日期
     */
    public static Date addHours(Date date, long hours) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return toDate(localDateTime.plusHours(hours));
    }

    /**
     * 根据日期增加指定分钟数值
     *
     * @param date    日期
     * @param minutes 增加的分钟数值
     * @return 增加分钟数值后的日期
     */
    public static Date addMinutes(Date date, long minutes) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return toDate(localDateTime.plusMinutes(minutes));
    }

    /**
     * 根据日期增加指定秒数值
     *
     * @param date    日期
     * @param seconds 增加的秒数值
     * @return 增加秒数值后的日期
     */
    public static Date addSeconds(Date date, long seconds) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return toDate(localDateTime.plusSeconds(seconds));
    }

    /**
     * 根据日期增加指定毫秒数值
     *
     * @param date    日期
     * @param seconds 增加的毫秒数值
     * @return 增加毫秒数值后的日期
     */
    public static Date addMilliseconds(Date date, long milliseconds) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return toDate(localDateTime.plus(milliseconds, ChronoUnit.MILLIS));
    }

    /**
     * 根据日期获取当年开始时间的日期对象
     *
     * @param date 日期
     * @return 指定日期当年开始时间(yyyy - 01 - 01 00 : 00 : 00.000)的日期对象
     */
    public static Date getYearStart(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDate localDate = LocalDate.of(localDateTime.getYear(), Month.JANUARY, 1);
        return toDate(localDate.atStartOfDay());
    }

    /**
     * 根据日期获取当年开始时间的日期对象
     *
     * @param date 日期
     * @return 指定日期当年开始时间(yyyy - 12 - 31 23 : 59 : 59.999)的日期对象
     */
    public static Date getYearEnd(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDate localDate = LocalDate.of(localDateTime.getYear() + 1, Month.JANUARY, 1);
        return toDate(localDate.atStartOfDay().plus(-1L, ChronoUnit.MILLIS));
    }

    /**
     * 根据日期获取当月开始时间的日期对象
     *
     * @param date 日期
     * @return 指定日期当月开始时间(yyyy - MM - 01 00 : 00 : 00.000)的日期对象
     */
    public static Date getMonthStart(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDate localDate = LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), 1);
        return toDate(localDate.atStartOfDay());
    }

    /**
     * 根据日期获取当月结束时间的日期对象
     *
     * @param date 日期
     * @return 指定日期当月结束时间(yyyy - MM - ( end) 23:59:59.999)的日期对象
     */
    public static Date getMonthEnd(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        int monthValue = localDateTime.getMonthValue() + 1;
        LocalDate localDate = LocalDate.of(localDateTime.getYear() + monthValue / MONTH_NUMBER,
                monthValue % MONTH_NUMBER, 1);
        return toDate(localDate.atStartOfDay().plus(-1L, ChronoUnit.MILLIS));
    }

    /**
     * 根据日期获取当天开始时间的日期对象
     *
     * @param date 日期
     * @return 指定日期当天开始时间(yyyy - MM - dd 00 : 00 : 00.000)的日期对象
     */
    public static Date getDayStart(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return toDate(localDateTime.toLocalDate().atStartOfDay());
    }

    /**
     * 根据日期获取当天结束时间的日期对象
     *
     * @param date 日期
     * @return 指定日期当天结束时间(yyyy - MM - dd 23 : 59 : 59.999)的日期对象
     */
    public static Date getDayEnd(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return toDate(localDateTime.toLocalDate().atTime(LocalTime.MAX));
    }

    /**
     * ISO-8601标准定义中一周的第一天为星期一, 中国采用ISO-8601标准
     *
     * @param date
     * @return
     */
    public static Date getIsoWeekStart(Date date) {
        return getWeekStart(date, WeekFields.ISO);
    }

    /**
     * 通用定义中一周的第一天为星期天
     *
     * @param date
     * @return
     */
    public static Date getWeekStart(Date date) {
        return getWeekStart(date, WeekFields.SUNDAY_START);
    }

    /**
     * 获取当前日期所在周的第一天
     *
     * @param date       当前日期
     * @param weekFields 星期字段
     * @return
     */
    public static Date getWeekStart(Date date, WeekFields weekFields) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDate localDate = localDateTime.toLocalDate();
        return toDate(localDate.with(weekFields.dayOfWeek(), 1).atStartOfDay());
    }

    /**
     * 获取今天是星期几
     *
     * @return the day-of-week, from 1 (Monday) to 7 (Sunday)
     */
    public static int getDayOfWeek() {
        LocalDateTime localDateTime = toLocalDateTime(new Date());
        return localDateTime.getDayOfWeek().getValue();
    }

    /**
     * 获取指定日期月份的总天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return localDateTime.toLocalDate().lengthOfMonth();
    }

    /**
     * 获取指定日期的当月第一天日期对象
     *
     * @param date
     * @return 指定日期的当月第一天日期对象
     */
    public static Date getFirstDateOfMonth(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDate localDate = LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), 1);
        return toDate(localDate.atTime(localDateTime.toLocalTime()));
    }

    /**
     * 获取指定日期的当月最后一天日期对象
     *
     * @param date
     * @return 指定日期的当月最后一天日期对象
     */
    public static Date getLastDateOfMonth(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        int monthValue = localDateTime.getMonthValue() + 1;
        LocalDate localDate = LocalDate.of(localDateTime.getYear() + monthValue / MONTH_NUMBER,
                monthValue % MONTH_NUMBER, 1);
        return toDate(localDate.plus(-1, ChronoUnit.DAYS).atTime(localDateTime.toLocalTime()));
    }

    public static ZoneId systemDefaultZoneId() {
        return ZoneId.systemDefault();
    }

    /**
     * Date对象转LocalDateTime对象
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), systemDefaultZoneId());
    }

    /**
     * LocalDateTime对象转Date对象
     *
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(systemDefaultZoneId()).toInstant();
        return Date.from(instant);
    }

    public static int truncatedCompareTo(final Date date1, final Date date2, final int field) {
        return DateUtils.truncatedCompareTo(date1, date2, field);
    }

    public static long daysBetween(Timestamp begin, Timestamp end) {
        if (begin == null || end == null) {
            throw new NullPointerException("begin == null or end == null");
        }
        long beginTime = begin.getTime();
        long endTime = end.getTime();
        if (beginTime > endTime) {
            throw new IllegalArgumentException("begin time > end time");
        }
        return (endTime - beginTime) / DAY_MILLI;
    }

    public static Timestamp toSqlTimestamp(String sDate) {
        parseTimestamp(sDate, ISO_8601_DATE);
        return sDate == null ? null
                : (sDate.length() != ISO_8601_DATE.length() ? null : toSqlTimestamp(sDate, ISO_8601_DATE));
    }

    public static Timestamp toSqlTimestamp(String dateStr, String format) {
        String temp = null;
        if (dateStr != null && format != null) {
            if (dateStr.length() != format.length()) {
                return null;
            } else {
                if (StringUtils.equals(format, YYYY_MM_DD_HH_MM_SS)) {
                    temp = dateStr.replace('/', '-');
                    temp = temp + ".000000000";
                } else {
                    if (!StringUtils.equals(format, ISO_8601_DATE)) {
                        return null;
                    }
                    temp = dateStr.replace('/', '-');
                    temp = temp + " 00:00:00.000000000";
                }
                return Timestamp.valueOf(temp);
            }
        } else {
            return null;
        }
    }

    public static String toString(Date dt, String sFmt) {
        return dt != null && sFmt != null && !"".equals(sFmt) ? toString(dt, new SimpleDateFormat(sFmt)) : "";
    }

    private static String toString(Date date, SimpleDateFormat formatter) {
        String str = null;
        try {
            str = formatter.format(date).toString();
        } catch (Exception e) {
            LOGGER.error("Date format error", e);
            str = null;
        }
        return str;
    }

    public static String toSqlTimestampString2(Timestamp dt) {
        if (dt == null) {
            return null;
        } else {
            String temp = toSqlTimestampString(dt, YYYY_MM_DD_HH_MM_SS);
            return Optional.ofNullable(temp).isPresent() ? temp.substring(0, 16) : temp;
        }
    }

    public static String toString(Timestamp dt) {
        return dt == null ? "" : toSqlTimestampString2(dt);
    }

    public static String toSqlTimestampString(Timestamp dt, String sFmt) {
        String temp = null;
        String out = null;
        if (dt != null && sFmt != null) {
            temp = dt.toString();
            if (StringUtils.equals(sFmt, YYYY_MM_DD_HH_MM_SS) || StringUtils.equals(sFmt, ISO_8601_DATE)) {
                temp = temp.substring(0, sFmt.length());
                out = temp.replace('/', '-');
            }
            return out;
        } else {
            return null;
        }
    }

    public static boolean isBetween(Date now, Date start, Date end, int model) {
        if (now != null && start != null && end != null) {
            switch (model) {
                case 1:
                    if (now.after(start) && now.before(end)) {
                        return true;
                    }
                    return false;
                default:
                    return false;
            }
        } else {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
    }

    public static Date getSeasonStart(Date date) {
        return getDayStart(getFirstDateOfMonth(getSeasonDate(date)[0]));
    }

    public static Date getSeasonEnd(Date date) {
        return getDayStart(getLastDateOfMonth(getSeasonDate(date)[2]));
    }

    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];
        int seasonIndex = getSeason(date);
        int seasonEndMonth = seasonIndex * 3 - 1;

        Calendar calendar = newCalendar(date);
        season[0] = cloneCalendarSet(calendar, Calendar.MONTH, seasonEndMonth - 2).getTime();
        season[1] = cloneCalendarSet(calendar, Calendar.MONTH, seasonEndMonth - 1).getTime();
        season[2] = cloneCalendarSet(calendar, Calendar.MONTH, seasonEndMonth).getTime();

        return season;
    }

    private static Calendar newCalendar(Date date) {
        Calendar calendar = newCalendar();
        calendar.setTime(date);
        return calendar;
    }

    private static Calendar newCalendar() {
        return Calendar.getInstance();
    }

    private static Calendar cloneCalendarSet(Calendar calendar, int field, int value) {
        Calendar copy = (Calendar) calendar.clone();
        copy.set(field, value);
        return copy;
    }

    /**
     * 获取季节
     *
     * @param date
     * @return 1 ~ 4
     */
    public static int getSeason(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        int monthValue = localDateTime.getMonthValue();
        for (int season = 1, seasonNumber = 4; season <= seasonNumber; season++) {
            int seasonEndMonth = season * 3;
            int seasonStartMonth = seasonEndMonth - 2;
            if (seasonStartMonth <= monthValue && monthValue <= seasonEndMonth) {
                return season;
            }
        }
        throw new IllegalStateException("Illegal month");
    }

    /**
     * 判断日期区间是否超过间隔日期数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param interval  间隔数值
     * @param dateUnit  间隔数值单位
     * @return true-超过, false-未超过
     */
    public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval, int dateUnit) {
        if (startDate.getTime() > endDate.getTime()) {
            throw new IllegalArgumentException("startDate > endDate");
        }
        if (interval <= 0) {
            throw new IllegalArgumentException("interval <= 0");
        }

        Calendar calendar = newCalendar(endDate);
        calendar.add(dateUnit, interval * -1);
        return startDate.getTime() < calendar.getTime().getTime();
    }

    /**
     * 判断日期区间是否超过间隔日期数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param interval  间隔日期数
     * @return true-超过, false-未超过
     */
    public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval) {
        return isOverIntervalLimit(startDate, endDate, interval, 5);
    }

    /**
     * 判断日期区间是否超过间隔日期数
     *
     * @param startDateStr 开始日期, 格式 : yyyy-MM-dd
     * @param endDateStr   结束日期, 格式 : yyyy-MM-dd
     * @param interval     间隔日期数
     * @return true-超过, false-未超过
     */
    public static boolean isOverIntervalLimit(String startDateStr, String endDateStr, int interval) {
        Date startDate = parseDate(startDateStr, ISO_8601_DATE);
        Date endDate = parseDate(endDateStr, ISO_8601_DATE);
        return isOverIntervalLimit(startDate, endDate, interval);
    }

    /***
     * 比较俩日期
     * date1 大于 date2，返回1，date1 等于 date2，返回0，date1 小于 date2，返回-1，
     * @param date1
     * @param date2
     * @param pattern 日期解析格式
     * @return
     */
    public static int compareDate(String date1, String date2, String pattern) {
        Date startDate = parseDate(date1, pattern);
        Date endDate = parseDate(date2, pattern);
        if (startDate.getTime() > endDate.getTime()) {
            return 1;
        } else if (startDate.getTime() == endDate.getTime()) {
            return 0;
        } else {
            return -1;
        }
    }

    public static List<String> getDays(String startTime, String endTime) throws ParseException {
        return getDays(startTime, endTime, ISO_8601_DATE);
    }

    public static List<String> getDays(String startTime, String endTime, String format) throws ParseException {
        // 返回的日期集合
        List<String> days = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date start = dateFormat.parse(startTime);
        Date end = dateFormat.parse(endTime);
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        // 日期加1(包含结束)
        tempEnd.add(Calendar.DATE, +1);
        while (tempStart.before(tempEnd)) {
            days.add(dateFormat.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return days;
    }
}
