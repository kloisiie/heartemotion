package com.brframework.commonweb.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonweb.utils.ServletUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


/**
 * Created by xu on 2019-8-19 12:44:42
 * REST API 数据抽象
 */
@Data
@ApiModel
public class JSONResult<T> {

    //成功
    public static final int DEFAULT_OK_CODE = 200;
    public static final String DEFAULT_OK_MSG = "success";

    //失败
    public static final int DEFAULT_ERROR_CODE = 500;
    public static final String DEFAULT_ERROR_MSG = "服务器异常，请稍后再试";

    @ApiModelProperty(value = "code", required = true, example = "200")
    private int code = 200;
    @ApiModelProperty(value = "msg", required = true, example = "success")
    private String msg;
    @ApiModelProperty(value = "desc", required = true, example = "ok")
    private String desc = "ok";
    @ApiModelProperty(value = "path", required = true, example = "path/path")
    private String path;
    @ApiModelProperty(value = "time", required = true, example = "2020-05-11 15:01:34")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time = LocalDateTime.now();
    @ApiModelProperty(value = "data", required = true)
    private T data;

    public static JSONResult ok(){
        JSONResult result = new JSONResult();
        result.code = DEFAULT_OK_CODE;
        result.msg = DEFAULT_OK_MSG;
        result.path = ServletUtils.request().getRequestURI();

        return result;
    }

    public static <T> JSONResult<T> ok(T data){
        JSONResult<T> result = new JSONResult<>();
        result.code = DEFAULT_OK_CODE;
        result.msg = DEFAULT_OK_MSG;
        result.path = ServletUtils.request().getRequestURI();
        result.data = data;
        return result;
    }


    public static JSONResult error(){
        JSONResult result = new JSONResult();
        result.code = DEFAULT_ERROR_CODE;
        result.msg = DEFAULT_ERROR_MSG;
        result.path = ServletUtils.request().getRequestURI();

        return result;
    }

    public static JSONResult error(HandleException e){
        JSONResult result = new JSONResult();
        result.code = e.getCode();
        result.msg = e.getMsg();
        result.desc = e.getDesc();
        result.path = ServletUtils.request().getRequestURI();

        return result;
    }

    public static JSONResult error(Throwable throwable){
        JSONResult result = new JSONResult();
        result.code = DEFAULT_ERROR_CODE;
        result.msg = throwable.getMessage();
        result.desc = "error";
        result.path = ServletUtils.request().getRequestURI();

        return result;
    }

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }



}
