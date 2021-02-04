package com.brframework.common.utils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * DateTime工具
 * @author xu
 * @date 2019/10/20 12:59
 */
public class DateTimeUtils {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static DateTimeFormatter FORMATTER_DEFAULT_YMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter FORMATTER_DEFAULT_yyyyMMDDhhmmss = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * yyyy-MM-dd
     */
    public static DateTimeFormatter FORMATTER_DEFAULT_YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * HH:mm:ss
     */
    public static DateTimeFormatter FORMATTER_DEFAULT_HMS = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * 字符串转DateTime
     * @param dateTimeString
     * @param formatter
     * @return
     */
    public static LocalDateTime stringToDateTime(String dateTimeString, DateTimeFormatter formatter){
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    /**
     * 字符串转DateTime
     * 默认：yyyy-MM-dd HH:mm:ss
     * @param dateTimeString
     * @return
     */
    public static LocalDateTime stringToDateTime(String dateTimeString){
        return stringToDateTime(dateTimeString, FORMATTER_DEFAULT_YMDHMS);
    }

    /**
     * 字符串转Date
     * @param dateString   date字符串
     * @param formatter    格式
     * @return  LocalDate
     */
    public static LocalDate stringToDate(String dateString, DateTimeFormatter formatter){
        return LocalDate.parse(dateString, formatter);
    }

    /**
     * 字符串转Date
     * 默认：yyyy-MM-dd
     * @param dateString  date字符串
     * @return  LocalDate
     */
    public static LocalDate stringToDate(String dateString){
        return stringToDate(dateString, FORMATTER_DEFAULT_YMD);
    }

    /**
     * DateTime转字符串
     * @param dateTime
     * @param formatter
     * @return
     */
    public static String dateTimeToString(LocalDateTime dateTime, DateTimeFormatter formatter){
        return formatter.format(dateTime);
    }

    /**
     * LocalDateTime转秒
     * @param dateTime
     * @param offset
     * @return
     */
    public static long toEpochSecond(LocalDateTime dateTime, ZoneOffset offset){
        //获取秒数
        return dateTime.toEpochSecond(offset);
    }

    public static long toEpochSecond(LocalDateTime dateTime){
        //获取秒数
        return dateTime.toEpochSecond(ZoneOffset.of("+8"));
    }

    /**
     * LocalDateTime转毫秒
     * @param dateTime
     * @param offset
     * @return
     */
    public static long toEpochMilli(LocalDateTime dateTime, ZoneOffset offset){
        return dateTime.toInstant(offset).toEpochMilli();
    }

    public static long toEpochMilli(LocalDateTime dateTime){
        return dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * DateTime转字符串
     * 默认：yyyy-MM-dd HH:mm:ss
     * @param dateTime
     * @return
     */
    public static String dateTimeToString(LocalDateTime dateTime){
        return dateTimeToString(dateTime, FORMATTER_DEFAULT_YMDHMS);
    }

    /**
     * 时间格式是否正确
     * @param dateTimeString
     * @param formatter
     * @return
     */
    public static boolean isFormatter(String dateTimeString, DateTimeFormatter formatter){
        try {
            stringToDateTime(dateTimeString, formatter);
        } catch (DateTimeException e){
            return false;
        }

        return true;
    }

    /**
     * 时间格式是否正确
     * 默认：yyyy-MM-dd HH:mm:ss
     * @param dateTimeString
     * @return
     */
    public static boolean isFormatter(String dateTimeString){
        return isFormatter(dateTimeString, FORMATTER_DEFAULT_YMDHMS);
    }

}
