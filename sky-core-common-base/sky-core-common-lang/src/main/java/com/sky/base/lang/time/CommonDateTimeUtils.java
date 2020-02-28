package com.sky.base.lang.time;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @Title
 * @Description
 * @author dengny
 * @version 1.0.0
 * @date 2018-12-26
 */
public class CommonDateTimeUtils {
    /**
     * 获取今天日期，格式为 yyyyMMdd
     * @return 
     */
    public static String getReqDate() {
        return new SimpleDateFormat(DateTimeUtils.YYYYMMDD).format(new Date());
    }

    /**
     * 格式化日期 yyyyMMdd
     * @param Date date
     * @return 
     */
    public static String getReqDate(Date date) {
        return new SimpleDateFormat(DateTimeUtils.YYYYMMDD).format(date);
    }

    /**
     * 获取今天日期，格式为 yyyy-MM-dd
     * @return 
     */
    public static String getShortDateStr() {
        return new SimpleDateFormat(DateTimeUtils.ISO_8601_DATE).format(new Date());
    }

    /**
     * 格式化日期  yyyy-MM-dd
     * @param Date date
     * @return 
     */
    public static String getShortDateStr(Date date) {
        return new SimpleDateFormat(DateTimeUtils.ISO_8601_DATE).format(date);
    }

    /***
     * 时间戳格式化成 yyyy-MM-dd 类型
     * @param Timestamp tmp
     * @return
     */
    public static String timestampToDateStr(Timestamp tmp) {
        return new SimpleDateFormat(DateTimeUtils.ISO_8601_DATE).format(tmp);
    }

    /**
     * 获取今天日期，格式为 yyyy-MM-dd HH:mm:ss
     * @return 
     */
    public static String getLongDateStr() {
        return new SimpleDateFormat(DateTimeUtils.YYYY_MM_DD_HH_MM_SS).format(new Date());
    }

    /**
     * 格式化日期 yyyy-MM-dd HH:mm:ss
     * @param Date date
     * @return 
     */
    public static String getTimeStampStr(Date date) {
        return new SimpleDateFormat(DateTimeUtils.YYYY_MM_DD_HH_MM_SS).format(date);
    }

    /**
     * 格式化时间戳 yyyy-MM-dd HH:mm:ss
     * @param Timestamp time
     * @return 
     */
    public static String getLongDateStr(Timestamp time) {
        return new SimpleDateFormat(DateTimeUtils.YYYY_MM_DD_HH_MM_SS).format(time);
    }

    public static Timestamp strToTimestamp(Date date) {
        return Timestamp.valueOf(new SimpleDateFormat(DateTimeUtils.YYYY_MM_DD_HH_MM_SS).format(date));
    }

    public static Timestamp strToTimestamp() {
        return Timestamp.valueOf(new SimpleDateFormat(DateTimeUtils.YYYY_MM_DD_HH_MM_SS).format(new Date()));
    }

    public static String getSysDateTimeString() {
        return DateTimeUtils.toString(new Date(System.currentTimeMillis()), DateTimeUtils.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 格式化时间戳，格式 yyyy-MM-dd
     * @param Timestamp timestamp
     * @return
     */
    public static String timestampToStringYMD(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtils.ISO_8601_DATE);
        String createTimeStr = sdf.format(timestamp);
        return createTimeStr;
    }

}
