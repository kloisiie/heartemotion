package com.brframework.commonwebadmin.service.admin;

import com.brframework.commondb.core.AbstractEntityService;
import com.brframework.commondb.core.CommonRepository;
import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonwebadmin.dao.admin.AdminOptionLogDao;
import com.brframework.commonwebadmin.entity.admin.AdminOptionLog;
import com.brframework.commonwebadmin.json.admin.adminuser.AdminOptionLogListParam;
import com.brframework.commonwebadmin.json.admin.adminuser.AdminOptionLogListQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员操作记录
 * @author xu
 * @date 2019/10/9 14:02
 */
@Service
public class AdminOptionLogService extends AbstractEntityService<AdminOptionLog, Long, AdminOptionLogListQueryParam> {

    @Autowired
    AdminOptionLogDao adminOptionLogDao;

    @Override
    public CommonRepository<Long, AdminOptionLog> getRepository() {
        return adminOptionLogDao;
    }

    /**
     * 添加记录
     * @param log
     */
    public void addLog(AdminOptionLog log){
        super.save(log);
    }

    @Override
    public void removeById(Long integer) {
        //不允许删除记录
        throw new HandleException("不允许删除记录");
    }

    @Override
    public AdminOptionLog save(AdminOptionLog adminOptionLog) {
        //不允许修改记录
        throw new HandleException("不允许修改记录");
    }
}
