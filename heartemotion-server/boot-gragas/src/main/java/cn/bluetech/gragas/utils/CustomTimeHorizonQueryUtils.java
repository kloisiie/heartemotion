package cn.bluetech.gragas.utils;

/**
 * 自定义时间范围查询工具
 * @author ljf
 * @date 2020/4/24 16:32
 */

public class CustomTimeHorizonQueryUtils {

    /**
     *  开始日期于结束日期合成范围查询条件
     *  @param start 开始日期
     *  @param end 结束日期
     *  @return 时间范围
      */
    public static String startDateAndEndDateCompoundString(String start,String end){
        // 因为给的参数是日期，需要拼接秒钟到时间字符串里，参数为每天开始的第一秒和最后一秒
        if (start != null && end != null){
            String startDate = start + " 00:00:00";
            String endDate = end + " 23:59:59";
            return startDate + "," +endDate;
        }else{
            return null;
        }
    }

}
