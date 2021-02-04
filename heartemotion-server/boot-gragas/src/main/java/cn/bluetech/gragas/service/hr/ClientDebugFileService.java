package cn.bluetech.gragas.service.hr;

import java.lang.*;
import java.util.*;
import cn.bluetech.gragas.entity.hr.*;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.pojo.hr.*;


/**
 * 用户调试文件服务实现
 * @author xu
 * @date 2020-12-16 21:47:34
 */ 
public interface ClientDebugFileService{



    /**
     * 通过ID查询用户调试文件 
     * @param clientDebugFileId    用户调试文件的ID 
     * @return 用户调试文件
     */
     ClientDebugFile findClientDebugFileById(Long clientDebugFileId);

    /**
     * 分页查询用户调试文件 
     * @param queryParam    查询参数
     * @param pageParam    分页参数 
     * @return 用户调试文件分页
     */
     Page<ClientDebugFile> findClientDebugFilePage(PageParam pageParam, ClientDebugFilePageQueryParamDTO queryParam);


    /**
     * 添加用户调试文件 
     * @param insertParam    添加参数 
     */
     void insertClientDebugFile(ClientDebugFileInsertParamDTO insertParam);


    /**
     * 修改用户调试文件 
     * @param updateParam    修改参数
     * @param clientDebugFileId    用户调试文件的ID 
     */
     void updateClientDebugFile(Long clientDebugFileId, ClientDebugFileUpdateParamDTO updateParam);


    /**
     * 删除用户调试文件 
     * @param clientDebugFileId    用户调试文件的ID 
     */
     void removeClientDebugFileById(Long clientDebugFileId);




}