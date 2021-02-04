package cn.bluetech.gragas.task;

import com.alibaba.fastjson.JSON;
import com.brframework.commonwebbase.service.DictionaryService;
import com.brframework.log.pojo.LogMessageRemoveConfigDTO;
import com.brframework.log.service.LogMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author laolian
 * @Date 2017/12/11 0011 下午 5:13
 * 日志数据处理
 */
@Slf4j
@Component
public class LogMessageTask {

    @Autowired
    LogMessageService logMessageService;
    @Autowired
    DictionaryService dictionaryService;

    /**
     * 每天五点检测按照条件删除数据
     */
    @Scheduled(cron = "0 0 5 * * *")
    @Transactional(rollbackFor = Exception.class)
    public void removeConditionally() {

        LogMessageRemoveConfigDTO configDTO = JSON.parseObject(dictionaryService.get(LogMessageRemoveConfigDTO.LOG_MESSAGE_REMOVE_CONFIG_KEY), LogMessageRemoveConfigDTO.class);

        logMessageService.removeByConfig(configDTO);

    }
}
