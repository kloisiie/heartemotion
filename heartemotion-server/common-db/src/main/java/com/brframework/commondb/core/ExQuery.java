package com.brframework.commondb.core;

import com.brframework.common.utils.DateTimeUtils;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * 表达式查询
 *
 * 示例
 * ExQuery.booleanExpressionBuilder()
 * //时间区间
 * .and(ExQuery.betweenDate(query.checkDate, start, end))
 * //用户
 * .and(ExQuery.eq(query.userId, userId))
 * //手机号码
 * .and(ExQuery.eq(query.phone, phone))
 * //商品
 * .and(ExQuery.eq(query.goodsId, goodsId))
 * .build(), pageable);
 * @author xu
 * @date 2018/3/9 0009 下午 4:18
 * 查询对象
 */
public class ExQuery {


    public static BooleanExpressionBuilder booleanExpressionBuilder(){
        return new BooleanExpressionBuilder();
    }

    public static class BooleanExpressionBuilder {

        public static BooleanExpressionBuilder builder(){
            return new BooleanExpressionBuilder();
        }

        BooleanExpression ex;

        public BooleanExpressionBuilder and(BooleanExpression booleanExpression){
            if(booleanExpression == null){
                return this;
            }

            if(ex == null){
                ex = booleanExpression;
            } else {
                ex = ex.and(booleanExpression);
            }

            return this;
        }

        public BooleanExpressionBuilder or(BooleanExpression booleanExpression){
            if(booleanExpression == null){
                return this;
            }

            if(ex == null){
                ex = booleanExpression;
            } else {
                ex = ex.or(booleanExpression);
            }

            return this;
        }


        public BooleanExpression build(){
            return ex;
        }
    }

    /**
     * Create a {@code this == right} expression
     * @param path
     * @param value
     * @param <T>
     * @return
     */
    public static <T> BooleanExpression eq(SimpleExpression<T> path, T value){
        if(value == null) {
            return null;
        }
        return path.eq(value);
    }

    /**
     * sql in 操作
     * @param path
     * @param value
     * @param <T>
     * @return
     */
    public static <T> BooleanExpression in(SimpleExpression<T> path, T... value){
        if(value == null) {
            return null;
        }
        return path.in(value);
    }

    /**
     * sql not in 操作
     * @param path
     * @param value
     * @param <T>
     * @return
     */
    public static <T> BooleanExpression notIn(SimpleExpression<T> path, T... value){
        if(value == null) {
            return null;
        }
        return path.notIn(value);
    }

    /**
     * Create a {@code like %this%} expression
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression like(StringExpression path, String value){
        if(value == null) {
            return null;
        }
        value = "%" + value + "%";
        return path.like(value);
    }

    /**
     * Create a {@code like %this} expression
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression likeLeft(StringExpression path, String value){
        if(value == null) {
            return null;
        }
        value = "%" + value;
        return path.like(value);
    }

    /**
     * Create a {@code like this%} expression
     * @param path
     * @param value
     * @return
     */
    public static BooleanExpression likeRight(StringExpression path, String value){
        if(value == null) {
            return null;
        }
        value = value + "%";
        return path.like(value);
    }

    /**
     * Create a {@code this <> right} expression
     * @param path
     * @param value
     * @param <T>
     * @return
     */
    public static <T> BooleanExpression ne(SimpleExpression<T> path, T value){
        if(value == null) {
            return null;
        }
        return path.ne(value);
    }


    /**
     * Create a {@code this > right} expression
     * @param path
     * @param value
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<?>> BooleanExpression gt(NumberExpression<T> path, T value){
        if(value == null) {
            return null;
        }
        return path.gt(value);
    }

    /**
     * Create a {@code this >= right} expression
     * @param path
     * @param value
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<?>> BooleanExpression goe(NumberExpression<T> path, T value){
        if(value == null) {
            return null;
        }
        return path.goe(value);
    }

    /**
     * Create a {@code this <= right} expression
     * @param path
     * @param value
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<?>> BooleanExpression loe(NumberExpression<T> path, T value){
        if(value == null) {
            return null;
        }
        return path.loe(value);
    }

    /**
     * Create a {@code this < right} expression
     * @param path
     * @param value
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<?>> BooleanExpression lt(NumberExpression<T> path, T value){
        if(value == null) {
            return null;
        }
        return path.lt(value);
    }

    /**
     * sql between 操作
     * @param path
     * @param start
     * @param end
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<?>> BooleanExpression between(NumberExpression<T> path, T start, T end){
        if(start == null && end == null) {
            return null;
        }

        BooleanExpression expression;
        if(end == null){
            expression = path.goe(start);
        } else if(start == null) {
            expression = path.loe(end);
        } else {
            expression = path.between(start, end);
        }

        return expression;
    }




    /**
     * 时间区间
     * end 为空 start 不为空的情况下使用大于等于
     * start 为空 end 不为空的情况下使用小于等于
     * @param dateTimePath
     * @param start
     * @param end
     * @return
     */
    public static <T extends Comparable> BooleanExpression betweenDate(DateTimePath<T> dateTimePath, T start, T end){
        if(start == null && end == null){
            return null;
        }

        BooleanExpression dateExpression;
        if(end == null){
            dateExpression = dateTimePath.goe(start);
        } else if(start == null) {
            dateExpression = dateTimePath.loe(end);
        } else {
            dateExpression = dateTimePath.between(start, end);
        }
        return dateExpression;
    }


    /**
     * 时间区间
     * end 为空 start 不为空的情况下使用大于等于
     * start 为空 end 不为空的情况下使用小于等于
     * @param datePath
     * @param start
     * @param end
     * @return
     */
    public static <T extends Comparable> BooleanExpression betweenDate(DatePath<T> datePath, T start, T end){
        if(start == null && end == null){
            return null;
        }

        BooleanExpression dateExpression;
        if(end == null){
            dateExpression = datePath.goe(start);
        } else if(start == null) {
            dateExpression = datePath.loe(end);
        } else {
            dateExpression = datePath.between(start, end);
        }
        return dateExpression;
    }

    /**
     *
     * @param dateTimePath
     * @param date
     * @return
     */
    public static BooleanExpression betweenDateTimeWithString(DateTimePath<java.time.LocalDateTime> dateTimePath, String date){

        if(StringUtils.isBlank(date)){
            return null;
        }
        String[] dateArr = date.split(",");
        LocalDateTime start = DateTimeUtils.stringToDateTime(dateArr[0]);
        LocalDateTime end = DateTimeUtils.stringToDateTime(dateArr[1]);
        BooleanExpression dateExpression;
        dateExpression = dateTimePath.between(start, end);
        return dateExpression;
    }
}
