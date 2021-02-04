package com.brframework.cms2.utils;


import com.brframework.commonweb.exception.HandleException;

/**
 * 时间区间格式
 *
 * @author 揭光智
 * @date 2019/03/08
 */
public class DataTimeBetweenUtil {

    /**
     * 获取开始时间
     *
     * @param time 时间区间
     * @return 开始时间
     */
    public static String getStartTime(String time) {
        return dealWithTime(time)[0];
    }

    /**
     * 获取结束时间
     *
     * @param time 时间区间
     * @return 结束时间
     */
    public static String getEndTime(String time) {
        return dealWithTime(time)[1];
    }

    private static String[] dealWithTime(String time) {
        if (time == null) {
            throw new HandleException("时间区间不能为null");
        }
        String[] times = time.split(",");
        if (times.length != 2) {
            throw new HandleException("时间区间格式不正确,正确的格式eg:2019-01-01 00:00:00,2019-02-01 00:00:00");
        }
        return times;
    }
}
