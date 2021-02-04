package com.brframework.cms2.generate;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 请求协议
 * @author xu
 * @date 2020/12/8 12:34
 */
public class RequestProtocol {

    private String url;
    private String method;
    private String alert;
    private boolean refresh = false;
    private boolean formParam = false;
    private String success;
    private boolean back = false;
    private Map<String, String> params = Maps.newHashMap();

    public static RequestProtocol builder(){
        return new RequestProtocol();
    }

    public RequestProtocol refresh(boolean refresh){
        this.refresh = refresh;
        return this;
    }

    public RequestProtocol formParam(boolean formParam){
        this.formParam = formParam;
        return this;
    }

    public RequestProtocol success(String success){
        this.success = success;
        return this;
    }

    public RequestProtocol back(boolean back){
        this.back = back;
        return this;
    }

    public RequestProtocol url(String url){
        this.url = url;
        return this;
    }

    public RequestProtocol method(String method){
        this.method = method;
        return this;
    }

    public RequestProtocol alert(String alert){
        this.alert = alert;
        return this;
    }

    public RequestProtocol addParam(String key, String value){
        params.put(key, value);
        return this;
    }

    public RequestProtocol addParam(Map<String, String> p){
        params.putAll(p);
        return this;
    }

    public String build(){
        if(StringUtils.isBlank(url)){
            throw new RuntimeException("请求协议生成失败，缺少url");
        }
        if(StringUtils.isBlank(method)){
            throw new RuntimeException("请求协议生成失败，缺少method");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("request://-X ");
        sb.append(method);
        if(refresh){
            sb.append(" --refresh");
        }

        if(StringUtils.isNotBlank(alert)){
            sb.append(" --alert ").append("\"").append(alert).append("\"");
        }

        if(formParam){
            sb.append(" #{_}");
        }

        if(StringUtils.isNotBlank(success)){
            sb.append(" --success ").append("\"").append(success).append("\"");
        }

        if(back){
            sb.append(" --back");
        }

        params.forEach((key, value) -> sb.append(" -d ").append(key).append("=").append(value));

        sb.append(" ").append(url);

        return sb.toString();
    }

}
