package com.brframework.commonweb.utils;

import com.brframework.commonweb.exception.HandleException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ServletUtils
 * @author xu
 * @date 2019/8/19 10:40
 */
public class ServletUtils {

    /**
     * 获取ServletRequestAttributes
     * @return ServletRequestAttributes
     */
    public static ServletRequestAttributes getServletRequestAttributes(){
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取当前 request
     * @return
     */
    public static HttpServletRequest request(){
        if(getServletRequestAttributes() == null || getServletRequestAttributes() == null){
            throw new HandleException("当前非WEB环境");
        }

        return getServletRequestAttributes().getRequest();
    }

    /**
     * 获取当前 response
     * @return
     */
    public static HttpServletResponse response(){
        return getServletRequestAttributes().getResponse();
    }

    public static String getRequestServerURL(HttpServletRequest request){
        StringBuilder url = new StringBuilder();
        String scheme = request.getScheme();
        int port = request.getServerPort();
        if (port < 0) {
            // Work around java.net.URL bug
            port = 80;
        }

        url.append(scheme);
        url.append("://");
        url.append(request.getServerName());
        if ((scheme.equals("http") && (port != 80))
                || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }
        return url.toString();
    }
}
