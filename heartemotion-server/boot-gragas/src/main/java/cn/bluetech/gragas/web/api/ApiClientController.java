package cn.bluetech.gragas.web.api;

import cn.bluetech.gragas.aop.annotation.APILog;
import cn.bluetech.gragas.service.client.ClientService;
import com.alibaba.fastjson.JSON;
import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.utils.ServletUtils;
import com.brframework.commonwebadmin.json.admin.adminuser.LoginResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端控制器
 * @author xu
 * @date 2020/12/16 17:29
 */
@RestController
@Api(tags = "客户端模块")
@RequestMapping("/api")
@Slf4j
public class ApiClientController {

    @Autowired
    private ClientService clientService;

    @ApiOperation(value = "客户端登录", notes = "客户端登录")
    @PostMapping("/v1/client/login")
    public JSONResult<LoginResult> studentDetailsV1(){
        String requestFlag = ServletUtils.request().getHeader("request-flag");
        if(StringUtils.isBlank(requestFlag)){
            throw new HandleException("request-flag不能为空");
        }
        String clientId = JSON.parseObject(requestFlag).getString("clientId");
        if(StringUtils.isBlank(clientId)){
            throw new HandleException("clientId不能为空");
        }

        return JSONResult.ok(
                LoginResult.builder()
                        .token(clientService.login(clientId))
                        .build()
        );
    }

}
