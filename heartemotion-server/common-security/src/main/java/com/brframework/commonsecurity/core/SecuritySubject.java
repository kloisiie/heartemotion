package com.brframework.commonsecurity.core;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @Author xu
 * @Date 2018/2/2 0002 上午 11:26
 * 信息载体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecuritySubject {

    private String id;
    private String username;
    private String password;
    private Map<String, String> expand = Maps.newHashMap();

    public String getExpandByKey(String key){
        return expand.get(key);
    }

}
