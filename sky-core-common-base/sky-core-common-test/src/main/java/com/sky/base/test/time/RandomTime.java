package com.sky.base.test.time;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * @Title 随机时间生成
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-13
 */
public class RandomTime {

    private long beginTime;
    private long endTime;
    private String timeFormat;

    public RandomTime(String beginTimeString, String endTimeString, String timeFormat) {
        super();
        try {
            this.timeFormat = timeFormat;
            SimpleDateFormat format = getFormat();
            this.beginTime = format.parse(beginTimeString).getTime();
            this.endTime = format.parse(endTimeString).getTime();
            if (endTime < beginTime) {
                throw new IllegalArgumentException("endTime > beginTime");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Date getRandomDate() {
        return new Date(random(beginTime, endTime));
    }

    public ImmutablePair<Date, String> getRandomDatePair() {
        Date newDate = getRandomDate();
        return new ImmutablePair<Date, String>(newDate, getFormat().format(newDate));
    }

    public Timestamp getRandomTimestamp() {
        return new Timestamp(random(beginTime, endTime));
    }

    public ImmutablePair<Timestamp, String> getRandomTimestampPair() {
        Timestamp newTimestamp = getRandomTimestamp();
        return new ImmutablePair<Timestamp, String>(newTimestamp, getFormat().format(newTimestamp));
    }

    private SimpleDateFormat getFormat() {
        return new SimpleDateFormat(timeFormat);
    }

    private long random(long begin, long end) {
        long newTime = begin + (long) (Math.random() * (end - begin));
        if (newTime == begin || newTime == end) {
            return random(begin, end);
        }
        return newTime;
    }

}
