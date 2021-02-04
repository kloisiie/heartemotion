package cn.bluetech.gragas.dao.client;

import cn.bluetech.gragas.entity.client.OptionLog;
import com.brframework.commondb.core.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description 操作日志
 * @Author xu
 * @Date 2020/12/16 15:37:36
 */

@Repository
public interface OptionLogDao extends CommonRepository<Long, OptionLog> {


}