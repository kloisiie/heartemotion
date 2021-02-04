package cn.bluetech.gragas.dao.hr;

import cn.bluetech.gragas.entity.hr.Device;
import com.brframework.commondb.core.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description 设备
 * @Author xu
 * @Date 2020/12/16 15:37:36
 */

@Repository
public interface DeviceDao extends CommonRepository<Long, Device> {


}