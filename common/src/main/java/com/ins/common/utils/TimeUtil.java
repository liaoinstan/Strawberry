package com.ins.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 时间处理工具
 * 进行时间的转换等
 */
public class TimeUtil {

    private static GregorianCalendar gc = new GregorianCalendar();

    /**
     * 日期转字符串  格式:yyyy年MM月dd日 E (带星期)
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        if (date == null) {
            return "";
        } else {
            return new SimpleDateFormat("yyyy年MM月dd日 E").format(date);
        }
    }

    /**
     * 根据格式日期转字符串
     *
     * @param date
     * @return
     */
    public static String getTimeFor(String format, Date date) {
        if (date == null) {
            return "";
        } else {
            return new SimpleDateFormat(format).format(date);
        }
    }

    /**
     * 根据格式转日期
     *
     * @param format
     * @param dateStr
     * @return
     */
    public static Date getDateByStr(String format, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    /**
     * 把时间字符串转为指定格式的时间字符串
     *
     * @param formatfrom
     * @param formatto
     * @param dateStr
     * @return
     */
    public static String getStrByStr(String formatfrom, String formatto, String dateStr) {
        Date date = getDateByStr(formatfrom, dateStr);
        String ret = getTimeFor(formatto, date);
        return ret;
    }

    /**
     * 日期加减
     * *gc.add(1,-1)表示年份减一.  YEAR
     * gc.add(2,-1)表示月份减一.  MONTH
     * gc.add(3.-1)表示周减一.   WEEK_OF_YEAR
     * gc.add(5,-1)表示天减一.   DATE
     */
    public static Date add(Date date, int field, int value) {
        gc.setTime(date);
        gc.add(field, value);
        return gc.getTime();
    }

    public static String add(String format, String datestr, int field, int value) {
        Date date = TimeUtil.getDateByStr(format, datestr);
        Date datelast = TimeUtil.add(date, field, value);
        return TimeUtil.getTimeFor(format, datelast);
    }

    /**
     * 两天相减获取天数
     */
    public static int subDay(Date beginDate, Date endDate) {
        return (int) ((endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000));
    }

    /**
     * 日起相减获取月份差
     */
    public static int subMouth(Date start, Date end) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == Calendar.SUNDAY) && (temp.get(Calendar.DATE) == Calendar.SUNDAY)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != Calendar.SUNDAY) && (temp.get(Calendar.DATE) == Calendar.SUNDAY)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == Calendar.SUNDAY) && (temp.get(Calendar.DATE) != Calendar.SUNDAY)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }

    //格式化时长为（天，小时，分钟 前），超过10天直接显示日期
    public static String formatTimeRangeBefore(long l) {
        int day = getDay((int) (l / 1000));
        int hour = getHour((int) (l / 1000));
        int minute = getMinite((int) (l / 1000));
        String format;
        if (day > 0 && day < 10) {
            format = "d天";
        } else if (day >= 10) {
            return getTimeFor("yyyy年M月d日", new Date(System.currentTimeMillis() - l));
        } else {
            if (hour != 0) {
                format = "H小时";
            } else {
                if (minute != 0) {
                    format = "m分钟";
                } else {
                    if (l / 1000 != 0) {
                        format = "s秒";
                    } else {
                        return "刚刚";
                    }
                }
            }
        }
        return formatTimeRange(l, format) + "前";
    }

    //格式化秒为（小时-分钟）
    public static String formatSecond(int s) {
        int hour = getHour(s);
        int minute = getMinite(s);
        String format;
        if (hour != 0) {
            format = "H小时m分钟";
        } else {
            if (minute != 0) {
                format = "m分钟";
            } else {
                format = "s秒";
            }
        }
        return formatTimeRange(s * 1000, format);
    }

    //格式化时间段为format
    public static String formatTimeRange(long time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        // 设置格式化器的时区为格林威治时区，否则格式化的结果不对，中国的时间比格林威治时间早8小时，比如0点会被格式化为8:00
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.format(time);
    }

    /**
     * 获取一个时间的特定单位（时分秒等等）
     * 当前年
     * int year = cal.get(Calendar.YEAR);
     * 当前月
     * int month = (cal.get(Calendar.MONTH)) + 1;
     * 当前月的第几天：即当前日
     * int day_of_month = cal.get(Calendar.DAY_OF_MONTH);
     * 当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
     * int hour = cal.get(Calendar.HOUR_OF_DAY);
     * 当前分
     * int minute = cal.get(Calendar.MINUTE);
     * 当前秒
     * int second = cal.get(Calendar.SECOND);
     * 0-上午；1-下午
     * int ampm = cal.get(Calendar.AM_PM);
     * 当前年的第几周
     * int week_of_year = cal.get(Calendar.WEEK_OF_YEAR);
     * 当前月的第几周
     * int week_of_month = cal.get(Calendar.WEEK_OF_MONTH);
     * 当前年的第几天
     * int day_of_year = cal.get(Calendar.DAY_OF_YEAR);
     */
    public static int getTimeCell(long time, int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int cell = cal.get(field);
        return cell;
    }

    public static int getHour(int s) {
        return (s / (60 * 60));
    }

    public static int getMinite(int s) {
        return (s / 60);
    }

    public static int getDay(int s) {
        return (s / (60 * 60 * 24));
    }

    public static void main(String[] args) {
//		System.out.println(TimeUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
//		System.out.println(TimeUtil.getNowTime("yyyy年MM月dd日 HH:mm"));
        System.out.println(TimeUtil.getTime(new Date()));
    }
}
