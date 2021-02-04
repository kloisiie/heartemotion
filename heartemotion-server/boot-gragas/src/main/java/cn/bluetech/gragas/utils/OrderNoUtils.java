package cn.bluetech.gragas.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author xu
 * @date 2020/3/1 2:01
 */
public class OrderNoUtils {

    /**
     * 要求外部订单号必须唯一。
     * @return
     */
    public static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        key = key + String.valueOf(Math.abs(Math.random())).substring(2, 7);
        return key;
    }

}
