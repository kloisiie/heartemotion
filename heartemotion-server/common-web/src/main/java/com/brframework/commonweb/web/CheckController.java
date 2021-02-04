package com.brframework.commonweb.web;

import com.brframework.commonweb.utils.RequestResponseUtils;
import com.brframework.commonweb.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SLB健康检查
 * @author xu
 * @date 2019/8/19 11:03
 */
@RestController
public class CheckController {

    @Autowired
    ConfigurableApplicationContext applicationContext;

    @GetMapping("/slb-check/v1/check")
    public void check(){
        RequestResponseUtils.writerJson(ServletUtils.response(), "success");
    }

    @GetMapping("/slb-check/v1/close")
    public void close(){
        applicationContext.close();
    }

}
