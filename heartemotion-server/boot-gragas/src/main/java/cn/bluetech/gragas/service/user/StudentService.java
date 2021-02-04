package cn.bluetech.gragas.service.user;

import java.lang.*;

import cn.bluetech.gragas.entity.user.*;
import org.springframework.data.domain.Page;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.pojo.user.*;


/**
 * 学生服务实现
 * @author xu
 * @date 2020-11-19 10:57:12
 */ 
public interface StudentService{



    /**
     * 通过ID查询学生 
     * @param studentId    学生的ID 
     * @return 学生
     */
     Student findStudentById(Long studentId);


    /**
     * 分页查询学生 
     * @param queryParam    查询参数
     * @param pageParam    分页参数 
     * @return 学生分页
     */
     Page<Student> findStudentPage(PageParam pageParam, StudentPageQueryParamDTO queryParam);


    /**
     * 添加学生 
     * @param insertParam    添加参数 
     */
     void insertStudent(StudentInsertParamDTO insertParam);


    /**
     * 修改学生 
     * @param studentId    学生的ID
     * @param updateParam    修改参数 
     */
     void updateStudent(Long studentId, StudentUpdateParamDTO updateParam);


    /**
     * 删除学生 
     * @param studentId    学生的ID 
     */
     void removeStudentById(Long studentId);




}