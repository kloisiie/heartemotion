package cn.bluetech.gragas.service.hr;

import java.lang.*;
import java.util.*;
import cn.bluetech.gragas.entity.hr.*;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.pojo.hr.*;


/**
 * 平台调试文件服务实现
 * @author xu
 * @date 2020-12-16 21:51:26
 */ 
public interface PlatformDebugFileService{



    /**
     * 通过ID查询平台调试文件 
     * @param platformDebugFileId    平台调试文件的ID 
     * @return 平台调试文件
     */
     PlatformDebugFile findPlatformDebugFileById(Long platformDebugFileId);


    /**
     * 分页查询平台调试文件 
     * @param queryParam    查询参数
     * @param pageParam    分页参数 
     * @return 平台调试文件分页
     */
     Page<PlatformDebugFile> findPlatformDebugFilePage(PageParam pageParam, PlatformDebugFilePageQueryParamDTO queryParam);


    /**
     * 添加平台调试文件 
     * @param insertParam    添加参数 
     */
     void insertPlatformDebugFile(PlatformDebugFileInsertParamDTO insertParam);


    /**
     * 修改平台调试文件 
     * @param updateParam    修改参数
     * @param platformDebugFileId    平台调试文件的ID 
     */
     void updatePlatformDebugFile(Long platformDebugFileId, PlatformDebugFileUpdateParamDTO updateParam);


    /**
     * 删除平台调试文件 
     * @param platformDebugFileId    平台调试文件的ID 
     */
     void removePlatformDebugFileById(Long platformDebugFileId);




}