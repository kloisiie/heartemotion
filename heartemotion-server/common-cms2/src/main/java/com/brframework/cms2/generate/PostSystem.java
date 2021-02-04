package com.brframework.cms2.generate;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.brframework.common.utils.DateTimeUtils;
import com.google.common.collect.Maps;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 提交系统
 * @author xu
 * @date 2020/12/8 12:39
 */
public class PostSystem {

    /**
     * 请求token
     * @param server     服务器地址
     * @param username   用户名
     * @param password   密码
     * @return  token
     */
    public static String getToken(String server, String username, String password){

        Map<String, Object> loginParam = Maps.newHashMap();
        loginParam.put("username", username);
        loginParam.put("password", password);
        JSONObject loginJson = JSON.parseObject(HttpUtil.post(
                server + "/admin/v1/login", loginParam));
        if(loginJson.getInteger("code") != 200){
            throw new RuntimeException("系统登录失败，请检查用户名和密码");
        }

        return loginJson.getJSONObject("data").getString("token");
    }

    /**
     * 提交版本
     * @param server    服务器地址
     * @param token      用户token
     * @param routeName   路由名称
     * @param content   内容
     */
    public static void postVersion(String server, String token, String routeName, String content){
        Map<String, String> headers = Maps.newHashMap();
        headers.put("Authorization", token);

        HttpRequest pageInfoRequest = HttpUtil.createGet(
                server + "/admin/access/v1/cms-page/details/route-name/" + routeName);
        pageInfoRequest.headerMap(headers, true);

        JSONObject pageInfoJson = JSON.parseObject(pageInfoRequest.execute().body());
        if(pageInfoJson.getInteger("code") != 200){
            throw new RuntimeException("数据提交失败，请注意检查：" + routeName + "路由是否存在");
        }

        Long pageId = pageInfoJson.getJSONObject("data").getLong("id");
        Map<String, Object> formData = Maps.newHashMap();
        formData.put("name", "代码更新-" + DateTimeUtils.dateTimeToString(LocalDateTime.now()));
        formData.put("pageId", pageId);
        formData.put("content", content);
        HttpRequest pageVersionRequest = HttpUtil.createPost(
                server + "/admin/access/v1/cms-page-version/add");
        pageVersionRequest.form(formData);
        pageVersionRequest.headerMap(headers, true);
        JSONObject pageVersionJson = JSON.parseObject(pageVersionRequest.execute().body());
        if(pageVersionJson.getInteger("code") != 200){
            throw new RuntimeException("版本提交失败，错误信息：" + pageVersionJson.get("msg"));
        } else {
            System.out.println("===========版本更新成功===================");
        }
    }

    public static JSONObject getRouteInfo(String server, String routeName){
        HttpRequest pageInfoRequest = HttpUtil.createGet(
                server + "/admin/access/v1/cms-page/details/route-name/" + routeName);

        JSONObject pageInfoJson = JSON.parseObject(pageInfoRequest.execute().body());
        if(pageInfoJson.getInteger("code") != 200){
            throw new RuntimeException("请注意检查：" + routeName + "路由是否存在");
        }
        return pageInfoJson.getJSONObject("data");
    }

}
