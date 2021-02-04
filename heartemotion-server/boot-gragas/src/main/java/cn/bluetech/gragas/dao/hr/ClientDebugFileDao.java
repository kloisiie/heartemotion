package cn.bluetech.gragas.dao.hr;

import cn.bluetech.gragas.entity.hr.ClientDebugFile;
import com.brframework.commondb.core.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description 用户调试文件
 * @Author xu
 * @Date 2020/12/16 21:41:16
 */

@Repository
public interface ClientDebugFileDao extends CommonRepository<Long, ClientDebugFile> {


}