package com.brframework.commondb.annotation.param;


import com.brframework.commondb.core.ExQuery;

/**
 * 查询语法
 * @see ExQuery
 * @author xu
 * @date 2019/1/9 11:15
 */
public enum QueryExpression {

    /**
     * Create a {@code this == right} expression
     */
    eq("eq"),
    /**
     * sql in 操作
     */
    in("in"),
    /**
     * sql not in 操作
     */
    notIn("notIn"),
    /**
     * Create a {@code like %this} expression
     */
    likeLeft("likeLeft"),
    /**
     * Create a {@code like this%} expression
     */
    likeRight("likeRight"),
    /**
     * Create a {@code like %this%} expression
     */
    like("like"),
    /**
     * Create a {@code this <> right} expression
     */
    ne("ne"),
    /**
     * Create a {@code this > right} expression
     */
    gt("gt"),
    /**
     * Create a {@code this >= right} expression
     */
    goe("goe"),
    /**
     * Create a {@code this <= right} expression
     */
    loe("loe"),
    /**
     * Create a {@code this < right} expression
     */
    lt("lt"),
    /**
     * sql某个时间属性between ... and ... 的操作
     */
    betweenDate("between"),
    /**
     * sql between 操作
     */
    betweenDateStart("betweenStart"),
    /**
     * sql between 操作
     */
    betweenDateEnd("betweenEnd");

    String method;
    QueryExpression(String method){
        this.method = method;
    }

    public String getMethod(){
        return method;
    }
}
