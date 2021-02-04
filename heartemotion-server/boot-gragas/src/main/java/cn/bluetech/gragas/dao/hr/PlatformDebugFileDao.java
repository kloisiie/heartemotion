package cn.bluetech.gragas.dao.hr;

import cn.bluetech.gragas.entity.hr.PlatformDebugFile;
import com.brframework.commondb.core.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description 平台调试文件
 * @Author xu
 * @Date 2020/12/16 21:41:16
 */

@Repository
public interface PlatformDebugFileDao extends CommonRepository<Long, PlatformDebugFile> {


}