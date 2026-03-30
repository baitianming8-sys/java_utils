package com.btm.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 *
 * @author bai
 * @since 2024/06/15 09:08
 */

public class DateUtils {
    private final static Logger log = LoggerFactory.getLogger(DateUtils.class);
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_PATTERN_2 = "yyyy/MM/dd";
    public static final String DATE_PATTERN_3 = "yyyy年MM月dd日";
    public static final String TIME_PATTERN = "HH:mm";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_PATTERN_1 = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_PATTERN_2 = "yyyyMMdd";



    public static LocalDate parse(String date, String... pattern) {
        if (StringUtils.isNotEmpty(date)) {
            try {
                LocalDate localDate = DateUtils.toLocalDate(org.apache.commons.lang3.time.DateUtils.parseDate(date, pattern));
                if (localDate.getYear() >=1900 && localDate.getYear() < 2300) {
                    return localDate;
                }
            } catch (ParseException parseException) {}
        }
        return null;
    }
    public static Date localDatetoDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }
    /**
     * 获取当前时间
     */
    public static String now(String format) {
        if (StringUtils.isEmpty(format)) format = DATE_TIME_PATTERN;
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(new Date());
    }

    /**
     * 获取当前时间
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 获取当前时间的毫秒数
     */
    public static long nowMillis() {
        return System.currentTimeMillis();
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime,String format){
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern(format);
        return dtf2.format(localDateTime);
    }
    /**
     * 字符串转换成日期
     */
    public static Date StrToDate(String str, String fm) {
        if (StringUtils.isEmpty(fm)) fm = DATE_TIME_PATTERN;
        if (str != null) {
            SimpleDateFormat format = new SimpleDateFormat(fm);
            Date date = null;
            try {
                date = format.parse(str);
            } catch (ParseException e) {
                log.error("", e);
            }
            return date;
        } else {
            return null;
        }
    }
    public static Long getHoursBetween(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
//        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
//        fromCalendar.set(Calendar.MINUTE, 0);
//        fromCalendar.set(Calendar.SECOND, 0);
//        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
//        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
//        toCalendar.set(Calendar.MINUTE, 0);
//        toCalendar.set(Calendar.SECOND, 0);
//        toCalendar.set(Calendar.MILLISECOND, 0);

        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 );
    }

    /**
     * 格式化日期
     */
    public static String formatDate(Date sourceDate, String format) {
        if (sourceDate == null) {
            return "";
        }
        if (format.isEmpty()) format = DATE_TIME_PATTERN;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();//创建一个实例
        calendar.setTime(sourceDate);//实例化一个Calendar。 年、月、日、时、分、秒
        return dateFormat.format(calendar.getTime());
    }


    /**
     * 定时任务 日期时间转con格式
     */
    public static String taskDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("s m H d M ? yyyy");
        Date now = new Date();
        String cron = null;
        if (now.getTime() > date.getTime()) {
            cron = sdf.format(new Date(now.getTime() + 5000));
        } else {
            cron = sdf.format(date);
        }
        return cron;
    }

    /**
     * 得到指定日期加上指定类型的时间
     */
    public static Date getAddDate(Date sourceDate, int type, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        calendar.add(type, count);
        return calendar.getTime();
    }

    /**
     * 获取指定日期是星期几
     */
    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /**
     * 获取两个日期相差的秒数
     */
    public static Long getDateDiffSecond(Date sdate, Date edate) {
        long day = 0;
        try {
            day = (edate.getTime() - sdate.getTime()) / 1000;
        } catch (Exception e) {
            return null;
        }
        return day;
    }

    public static String getChinaTime(int minutes) {
        int day = 0, hours = 0;
        day = minutes / (60 * 24);
        minutes -= day * 60 * 24;
        hours = minutes / (60);
        minutes -= hours * 60;
        StringBuffer str = new StringBuffer();
        if (day > 0) {
            str.append(day + "天");
        }
        if (hours > 0) {
            str.append(hours + "小时");
        }
        if (minutes > 0) {
            str.append(minutes + "分");
        }
        return str.toString();
    }

    /**
     * 获得开始时间
     * @param date 参考时间
     * @param days 变动天数
     * @return
     */
    public static Date getStartTime(Date date, int days){
        if (date == null) {
            return date;
        }
        date = org.apache.commons.lang3.time.DateUtils.addDays(date, days);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获得结束时间
     * @param date 参考时间
     * @param days 变动天数
     * @return
     */
    public static Date getEndTime(Date date, int days){
        if (date == null) {
            return date;
        }
        date = org.apache.commons.lang3.time.DateUtils.addDays(date, days);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return calendar.getTime();
    }

    /**
     * 一周开始的时间 （周一）
     * @param date 参考时间
     * @param days 变动天数
     * @return
     */
    public static Date getWeekStartTime(Date date, int days){
        if (date == null) {
            return date;
        }
        // Java星期是以星期日为一周的开始，业务中计算是以周一开始的，所以减1是为了获取到正确的周数
        date = org.apache.commons.lang3.time.DateUtils.addDays(date, days - 1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 一周开始的时间 （周日）
     * @param date 参考时间
     * @param days 变动天数
     * @return
     */
    public static Date getWeekEndTime(Date date, int days){
        if (date == null) {
            return date;
        }
        // Java星期是以星期日为一周的开始，业务中计算是以周一开始的，所以减1是为了获取到正确的周数
        date = org.apache.commons.lang3.time.DateUtils.addDays(date, days - 1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH) + 6, 23, 59, 59);
        return calendar.getTime();
    }

    /**
     * 某月开始的时间
     * @param date 参考时间
     * @param months 变动月份
     * @return
     */
    public static Date getMonthStartTime(Date date, int months){
        if (date == null) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + months, 1, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 某月结束的时间
     * @param date 参考时间
     * @param months 变动月份
     * @return
     */
    public static Date getMonthEndTime(Date date, int months){
        if (date == null) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + months + 1, 1, 0, 0, 0);
        return org.apache.commons.lang3.time.DateUtils.addSeconds(calendar.getTime(), -1);
    }

    public static Long getDaysBetween(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
    }

    public static LocalDateTime toLocalDateTime(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate toLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String strFormat(String input,String format){

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(format);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 解析输入时间
        LocalDateTime dateTime = LocalDateTime.parse(input, inputFormatter);
        // 格式化输出时间
        String formattedDate = dateTime.format(outputFormatter);
        return formattedDate;
    }

    /**
     *
     * @param input  2025-01-02T10:22:59.000+08:00
     * @return
     */
    public static String strISO8601Format(String input){
        OffsetDateTime odt = OffsetDateTime.parse(input);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = odt.format(formatter);
        return formatted;
    }

    public static LocalDate strToLocalDate(String dateStr,String Format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Format);
        LocalDate date = LocalDate.parse(dateStr, formatter);
        return date;
    }
/*
* 转汉字
* */
    public static String localDateToChineseDate(String date1) {

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM").parse(date1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String str = new SimpleDateFormat("yyyy年MM月").format(date);

        return str;
    }

    // 返回时间格式如：2020-02-17 00:00:00
    public static String getStartOfDay(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }
    // 返回时间格式如：2020-02-19 23:59:59
    public static String getEndOfDay(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    /**
     * 根据当前时间获取往前n天的时间
     */
    public static List<String> getWeekDateByCurrentDate(Date currentDate,int n) {
        List<String> listDate = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, -n);
            for (int i = 0; i < n; i++) {
                listDate.add(dateFormat.format(calendar.getTime()));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDate;
    }

    /**
     * 获取取暖季开始时间，当前年度 7月 至次年 7月
     * @return
     */
    public static String getHeatSeasonStart(int year) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 6);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return formatDate(calendar.getTime(), DATE_PATTERN);
    }

    /**
     * 获取取暖季结束时间 次年 7 月
     * @return
     */
    public static String getHeatSeasonEnd(int year) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year + 1);
        calendar.set(Calendar.MONTH, 6);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return formatDate(calendar.getTime(), DATE_PATTERN);
    }

    /**
     * 获取取暖机季 年份   7月1日之后 返回当前年份，  7月1日之前 减一年
     * @return
     */
    public static int getFiscalYear() {
        LocalDate today = LocalDate.now();
        LocalDate julyFirst = LocalDate.of(today.getYear(), Month.JULY, 1);

        if (today.isAfter(julyFirst) || today.isEqual(julyFirst)) {
            return today.getYear();
        } else {
            return today.getYear() - 1;
        }
    }

    /**
     * 给定起止时间，获取中间的所有日期
     * @author bai
     * @since 2025/9/3 14:13
     */
    public static List<String> getAllDateBetweenTwoDates(String startDateStr,String endDateStr) {
        List<String> dateList = new ArrayList<>();

        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
            // 解析日期字符串
            LocalDate startDate = LocalDate.parse(startDateStr, dateTimeFormatter);
            LocalDate endDate = LocalDate.parse(endDateStr, dateTimeFormatter);

            // 确保开始日期不晚于结束日期
            if (startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("开始日期不能晚于结束日期");
            }

            // 遍历日期范围
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                dateList.add(currentDate.format(dateTimeFormatter));
                currentDate = currentDate.plusDays(1);
            }

        } catch (Exception e) {
            // 处理日期解析错误或其他异常
            throw new IllegalArgumentException("日期格式错误或无效日期", e);
        }

        return dateList;
    }

    /**
     * 将各种格式的字符转成 截止时间格式 2025-07-01 23:59:59
     * 现有系统参数中有各种格式
     */
    public static String strToEndStr(String date) {
        if (StringUtils.isNotEmpty(date)) {
            try {
                return DateUtils.formatDate( DateUtils.getEndTime(org.apache.commons.lang3.time.DateUtils.parseDate(date, DATE_PATTERN, DATE_TIME_PATTERN, DATE_TIME_PATTERN_1), 0), DATE_TIME_PATTERN);
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 将各种格式的字符转成 截止时间格式 2025-07-01 00:00:00
     * 现有系统参数中有各种格式
     */
    public static String strToStartStr(String date) {
        if (StringUtils.isNotEmpty(date)) {
            try {
                return DateUtils.formatDate( DateUtils.getStartTime(org.apache.commons.lang3.time.DateUtils.parseDate(date, DATE_PATTERN, DATE_TIME_PATTERN, DATE_TIME_PATTERN_1), 0), DATE_TIME_PATTERN);
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(strToEndStr("2025-07-01 08:59:59"));
        System.out.println(strToStartStr("2025-07-01 08:00:00"));
        System.out.println(strToEndStr("2025-07-01"));
        System.out.println(strToStartStr("2025-07-01"));

    }
}
