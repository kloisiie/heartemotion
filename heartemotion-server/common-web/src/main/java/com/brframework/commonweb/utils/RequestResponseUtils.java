package com.brframework.commonweb.utils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * @Author xu
 * @Date 2018/2/2 0002 下午 4:18
 */
public class RequestResponseUtils {

    /**
     * 将数据写入json
     * @param response
     * @param json
     */
    public static void writerJson(ServletResponse response, String json){

        writer(response, json, "application/json");

    }

    /**
     * 将数据写入
     * @param response
     * @param content
     */
    public static void writer(ServletResponse response, String content, String contentType){

        try {
            response.setContentType(contentType);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(content);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取requestBody里的数据
     * @param request
     * @return
     */
    public static String readRequestBody(ServletRequest request){

        try {
            BufferedReader in = request.getReader();

            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,String[]> e:map.entrySet()){
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if(value != null && value.length == 1){
                sb.append(value[0]).append("\t");
            }else{
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }


    public static String readRequestParamString(ServletRequest request){
        return getParamString(request.getParameterMap());
    }

    public static String readRequestHeader(HttpServletRequest request , String headerName){
        return request.getHeader(headerName);
    }

}
