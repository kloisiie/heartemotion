package cn.bluetech.gragas.utils;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.core.types.dsl.StringTemplate;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * @author xu
 * @date 2020/4/21 21:50
 */
public class QueryDslJsonUtils {

    /* 逗号分隔符 */
    public static final String CONSTANT_MARKUP_SEPARATOR_COMMA = ",";
    /* 可看成员标签符号 */
    private static final String LABEL_CONCAT_SYMBOL_LABEL = "id_";
    /* 可看成员标签值 */
    private static final int LABEL_CONCAT_SYMBOL_LABEL_VALUE = 1;

    public static JSONObject wrapLabel(String labels) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotEmpty(labels)) {
            for (String label : labels.split(",")) {
                jsonObject.put(LABEL_CONCAT_SYMBOL_LABEL + label, LABEL_CONCAT_SYMBOL_LABEL_VALUE);
            }
        }
        return jsonObject;
    }

    public static String unWrapLabel(JSONObject labelJson) {
        if (labelJson != null) {
            Set<String> labelSet = Sets.newTreeSet();
            for (String wrapLabel : labelJson.keySet()) {
                try {
                    String[] split = wrapLabel.split("_");
                    labelSet.add(split[1]);
                } catch (Exception e) {
                    continue;
                }
            }
            return StringUtils.join(labelSet, ",");

        }
        return "";
    }

    public static String genWrap(String label) {
        return LABEL_CONCAT_SYMBOL_LABEL + label;
    }

    /**
     * 获取JSON中的字段，并生成字符串操作模板
     * @param jsonPath     query dsl json字段
     * @param fieldName    获取的字段名称
     * @return  字符串操作模板
     */
    public static StringTemplate extractStringTemplate(Path jsonPath, String fieldName){
        return Expressions.stringTemplate("json_extract({0}, '$." + fieldName +"')", jsonPath);
    }

    /**
     * 获取JSON中的字段，并生成数值操作模板
     * @param fieldType  字段类型
     * @param jsonPath   query dsl json字段
     * @param fieldName  获取的字段名称
     * @return  数值操作模板
     */
    public static <T extends Number & Comparable<?>> NumberTemplate<T> extractNumberTemplate(Class<? extends T> fieldType
            , Path jsonPath, String fieldName){
        if(fieldType.getName().equals(Long.class.getName())){
            return Expressions.numberTemplate(fieldType,
                    "json_extract_long({0}, '$." + fieldName +"')", jsonPath);
        } else if(fieldType.getName().equals(Integer.class.getName())) {
            return Expressions.numberTemplate(fieldType,
                    "json_extract_integer({0}, '$." + fieldName +"')", jsonPath);
        } else if(fieldType.getName().equals(Double.class.getName())) {
            return Expressions.numberTemplate(fieldType,
                    "json_extract_double({0}, '$." + fieldName +"')", jsonPath);
        } else {
            throw new IllegalArgumentException("did not match expected type :" + fieldType.getName());
        }

    }

    /**
     * 判断json数组中，是否包含某个值，如果进行多值查询的情况下是并且的关系
     * 同时满足多个值的的时候条件才成立
     * @param jsonPath     query dsl json字段
     * @param values       查询的值，可以多个，如果传递了多个，则同时匹配多个条件才会成立
     * @return  boolean操作模板
     */
    public static BooleanTemplate containsBooleanTemplateByArray(Path jsonPath, String... values){
        StringBuilder newValues = new StringBuilder();
        for (int i = 0 ; i < values.length; i++) {
            if(NumberUtil.isNumber(values[i])){
                newValues.append(values[i]);
            } else {
                newValues.append("\"").append(values[i]).append("\"");
            }
            if(i != values.length - 1){
                newValues.append(",");
            }
        }

        return Expressions.booleanTemplate("json_contains({0}, '[" + newValues.toString() +"]')", jsonPath);
    }

    /**
     * 判断json对象中，是否包含某些字段，只要包含其中一个字段条件则成立
     * @param jsonPath        query dsl json字段
     * @param fieldNames      查询的字段
     * @return  boolean操作模板
     */
    public static BooleanTemplate containsOneBooleanTemplate(Path jsonPath, String... fieldNames){
        return Expressions.booleanTemplate("json_contains_path({0}, 'one', " +
                objectFieldNameHandle(fieldNames) + ")", jsonPath);
    }

    /**
     * 判断json对象中，是否包含某些字段，必须要含有所有字段条件才会成立
     * @param jsonPath         query dsl json字段
     * @param fieldNames       询的字段
     * @return   boolean操作模板
     */
    public static BooleanTemplate containsAllBooleanTemplate(Path jsonPath, String... fieldNames){
        return Expressions.booleanTemplate("json_contains_path({0}, 'all', " +
                objectFieldNameHandle(fieldNames) + ")", jsonPath);
    }

    private static String objectFieldNameHandle(String... fieldNames){
        StringBuilder newValues = new StringBuilder();
        for (int i = 0 ; i < fieldNames.length; i++) {
            newValues.append("'$.\"").append(fieldNames[i]).append("\"'");
            if(i != fieldNames.length - 1){
                newValues.append(",");
            }
        }
        return newValues.toString();
    }

}
