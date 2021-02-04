package cn.bluetech.gragas.service.client.impl;

import cn.bluetech.gragas.service.client.ClientService;
import com.brframework.commonsecurity.core.SecuritySubject;
import com.brframework.commonsecurity.core.SecurityUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 客户端服务
 * @author xu
 * @date 2020/12/16 17:50
 */
@Service
@Slf4j
public class ClientServiceImpl implements ClientService {


    @Autowired
    @Qualifier("apiUserDetailsService")
    SecurityUserDetailsService userDetailsService;


    @Override
    public String login(String client) {
        return userDetailsService.genToken(client, SecuritySubject.builder()
                .id(client)
                .username(client)
                .build());
    }

}
