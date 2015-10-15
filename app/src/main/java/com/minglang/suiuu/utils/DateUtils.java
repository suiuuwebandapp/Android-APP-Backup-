package com.minglang.suiuu.utils;

import com.minglang.suiuu.entity.TimeInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2015/10/15.
 * <p>
 * 时间日期辅助类
 */
public class DateUtils {

    public static String getTimestamp2String(Date date) {
        String timeFormat;
        long secondTime = date.getTime();
        if (isSameDay(secondTime)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int hourForDay = calendar.get(Calendar.HOUR_OF_DAY);
            if (hourForDay > 17) {
                timeFormat = "晚上 hh:mm";
            } else if (hourForDay >= 0 && hourForDay <= 6) {
                timeFormat = "凌晨 hh:mm";
            } else if (hourForDay > 11 && hourForDay <= 17) {
                timeFormat = "下午 hh:mm";
            } else {
                timeFormat = "上午 hh:mm";
            }
        } else if (isYesterday(secondTime)) {
            timeFormat = "昨天 HH:mm";
        } else {
            timeFormat = "M月d日 HH:mm";
        }

        return (new SimpleDateFormat(timeFormat, Locale.CHINA)).format(date);
    }

    public static boolean isCloseEnough(long time1, long time2) {
        long time = time1 - time2;
        if (time < 0L) {
            time = -time;
        }
        return time < 30000L;
    }

    public static boolean isSameDay(long time) {
        TimeInfo timeInfo = getTodayStartAndEndTime();
        return time > timeInfo.getStartTime() && time < timeInfo.getEndTime();
    }

    public static boolean isYesterday(long time) {
        TimeInfo timeInfo = getYesterdayStartAndEndTime();
        return time > timeInfo.getStartTime() && time < timeInfo.getEndTime();
    }

    public static TimeInfo getTodayStartAndEndTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar1.getTime();
        long startTime = startDate.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        calendar2.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar2.getTime();
        long endTime = endDate.getTime();
        TimeInfo timeInfo = new TimeInfo();
        timeInfo.setStartTime(startTime);
        timeInfo.setEndTime(endTime);
        return timeInfo;
    }

    public static TimeInfo getYesterdayStartAndEndTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, -1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar1.getTime();
        long startTime = startDate.getTime();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, -1);
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        calendar2.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar2.getTime();
        long endTime = endDate.getTime();
        TimeInfo timeInfo = new TimeInfo();
        timeInfo.setStartTime(startTime);
        timeInfo.setEndTime(endTime);
        return timeInfo;
    }

    public static Date String2Date(String timeFormat, String timeString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat, Locale.CHINA);
        return simpleDateFormat.parse(timeString);
    }

    public static long String2Long(String timeFormat, String timeString) throws ParseException {
        return String2Date(timeFormat, timeString).getTime();
    }

}