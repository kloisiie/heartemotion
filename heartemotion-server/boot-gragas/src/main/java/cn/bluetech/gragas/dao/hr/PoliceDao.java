package cn.bluetech.gragas.dao.hr;

import cn.bluetech.gragas.entity.hr.Police;
import com.brframework.commondb.core.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description 报警记录
 * @Author xu
 * @Date 2020/12/16 15:37:36
 */

@Repository
public interface PoliceDao extends CommonRepository<Long, Police> {


}