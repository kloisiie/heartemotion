package cn.bluetech.gragas.dao.hr;

import cn.bluetech.gragas.entity.hr.HeartRate;
import com.brframework.commondb.core.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description 心率
 * @Author xu
 * @Date 2020/12/16 15:37:36
 */

@Repository
public interface HeartRateDao extends CommonRepository<Long, HeartRate> {


}