package cn.bluetech.gragas.utils;

import java.math.BigDecimal;

/**
 * @author ljf
 * @Description 价格精确(精确到分)
 * @date 2020/4/13 9:54
 */
public class PricePreciseUtils {

    /**
      * 价格精确 -> 分
      * @param price 要精确的金额
      * @return 精确后的金额
      */
    public static <T> BigDecimal pricePrecisePenny(T price){
        if (price == null){
            return null;
        }
        if (price instanceof BigDecimal){
            return ((BigDecimal)price).divide(new BigDecimal(100));
        }
        if (price instanceof Long){
            return (new BigDecimal((Long)price)).divide(new BigDecimal(100));
        }
        return null;
    }

    /**
     * 价格精确到 -> 元
     * @param price 要精确的金额
     * @return 精确后的金额
     */
    public static Long pricePreciseUnit(BigDecimal price){
        if (price == null){
            return null;
        }
        return price.multiply(new BigDecimal(100)).longValue();
    }
}
