package com.brframework.cms2.generate;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 路由协议
 * @author xu
 * @date 2020/12/8 12:34
 */
public class RouteProtocol {
    private String name;
    private boolean dynamic = true;
    private String title;
    private Map<String, String> params = Maps.newHashMap();

    private RouteProtocol(){}

    public static RouteProtocol builder(){
        return new RouteProtocol();
    }

    public RouteProtocol dynamic(boolean dynamic){
        this.dynamic = dynamic;
        return this;
    }

    public RouteProtocol title(String title){
        this.title = title;
        return this;
    }

    public RouteProtocol name(String name){
        this.name = name;
        return this;
    }

    public RouteProtocol addParam(String key, String value){
        params.put(key, value);
        return this;
    }

    public RouteProtocol addParam(Map<String, String> p){
        params.putAll(p);
        return this;
    }

    public String build(){
        if(StringUtils.isBlank(name)){
            throw new RuntimeException("路由协议生成失败，缺少name");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("route://");
        sb.append(name);
        if(dynamic){
            sb.append(" --dynamic");
        } else {
            sb.append(" --static");
        }

        if(StringUtils.isNotBlank(title)){
            sb.append(" --title ").append("\"").append(title).append("\"");
        }

        params.forEach((key, value) -> {
            sb.append(" -d ").append(key).append("=").append(value);
        });

        return sb.toString();
    }
}
