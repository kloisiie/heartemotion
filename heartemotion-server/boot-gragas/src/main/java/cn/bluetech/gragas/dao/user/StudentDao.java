package cn.bluetech.gragas.dao.user;

import cn.bluetech.gragas.entity.user.Student;
import com.brframework.commondb.core.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description 主菜单
 * @Author xu
 * @Date 2020/11/07 16:05:05
 */

@Repository
public interface StudentDao extends CommonRepository<Long, Student> {


}