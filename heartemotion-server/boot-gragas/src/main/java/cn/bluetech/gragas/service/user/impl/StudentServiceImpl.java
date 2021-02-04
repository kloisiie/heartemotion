package cn.bluetech.gragas.service.user.impl;

import java.lang.*;
import java.time.LocalDateTime;

import cn.bluetech.gragas.utils.QueryDslJsonUtils;
import org.springframework.data.domain.*;
import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.dao.user.*;
import cn.bluetech.gragas.entity.user.*;
import cn.bluetech.gragas.pojo.user.*;
import cn.bluetech.gragas.service.user.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.brframework.commondb.core.ExQuery;
import org.springframework.transaction.annotation.Transactional;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import com.querydsl.core.QueryResults;


/**
 * 学生服务实现
 * @author xu
 * @date 2020-11-19 10:57:12
 */ 
@Service
@Slf4j
public class StudentServiceImpl implements StudentService{

    /** 学生Dao */
    @Autowired
    private StudentDao studentDao;
    /** QueryDSL查询 */
    private JPAQueryFactory queryFactory;


    /**
     * 设置QueryFactory 
     * @param entityManager    entity manager 
     */
    @Resource
    public void setQueryFactory(@Autowired EntityManager entityManager){
        
        queryFactory = new JPAQueryFactory(entityManager);
            
    }


    /**
     * 通过ID查询学生 
     * @param studentId    学生的ID 
     * @return 学生
     */
    @Override
    public Student findStudentById(Long studentId){
        
        return studentDao.findById(studentId).orElse(null);
            
    }


    /**
     * 分页查询学生 
     * @param queryParam    查询参数
     * @param pageParam    分页参数 
     * @return 学生分页
     */
    @Override
    public Page<Student> findStudentPage(PageParam pageParam, StudentPageQueryParamDTO queryParam){



        QueryResults<Student> results = queryFactory.select(QStudent.student)
            .from(QStudent.student)
            .where(ExQuery.booleanExpressionBuilder()
                .and(ExQuery.eq(QStudent.student.name, queryParam.getName()))
                .and(ExQuery.between(QStudent.student.age, queryParam.getAgeBetweenStart(),
                        queryParam.getAgeBetweenEnd()))
                .and(queryParam.getHobby() == null || queryParam.getHobby().size() == 0 ? null :
                        QueryDslJsonUtils.containsOneBooleanTemplate(
                        QStudent.student.hobby, queryParam.getHobby().toArray(new String[0])))
                .and(ExQuery.betweenDate(QStudent.student.birthday, queryParam.getBirthdayBetweenStart(),
                        queryParam.getBirthdayBetweenEnd()))
                .and(ExQuery.eq(QStudent.student.province, queryParam.getProvince()))
                .and(ExQuery.eq(QStudent.student.city, queryParam.getCity()))
                .and(ExQuery.eq(QStudent.student.district, queryParam.getDistrict()))
                .and(ExQuery.eq(QStudent.student.rating, queryParam.getRating()))
                .build())
            .offset(pageParam.getPageIndex() * pageParam.getPageSize())
            .limit(pageParam.getPageSize())
            .orderBy(QStudent.student.createDate.desc())
            .fetchResults();
    
        return new PageImpl<>(results.getResults(), Pageable.unpaged(), results.getTotal());
            
    }


    /**
     * 添加学生 
     * @param insertParam    添加参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertStudent(StudentInsertParamDTO insertParam){
        
        Student student = new Student();
        student.setCreateDate(LocalDateTime.now());
        student.setName(insertParam.getName());
        student.setAge(insertParam.getAge());
        student.setHobby(insertParam.getHobby());
        student.setBirthday(insertParam.getBirthday());
        student.setProvince(insertParam.getProvince());
        student.setCity(insertParam.getCity());
        student.setDistrict(insertParam.getDistrict());
        student.setHeadImage(insertParam.getHeadImage());
        student.setImages(insertParam.getImages());
        student.setSignature(insertParam.getSignature());
        student.setAccessory(insertParam.getAccessory());
        student.setRating(insertParam.getRating());
        student.setIntroduction(insertParam.getIntroduction());
    
        studentDao.save(student);
            
    }


    /**
     * 修改学生 
     * @param studentId    学生的ID
     * @param updateParam    修改参数 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStudent(Long studentId, StudentUpdateParamDTO updateParam){
        
        Student student = findStudentById(studentId);
        student.setName(updateParam.getName());
        student.setAge(updateParam.getAge());
        student.setHobby(updateParam.getHobby());
        student.setBirthday(updateParam.getBirthday());
        student.setProvince(updateParam.getProvince());
        student.setCity(updateParam.getCity());
        student.setDistrict(updateParam.getDistrict());
        student.setHeadImage(updateParam.getHeadImage());
        student.setImages(updateParam.getImages());
        student.setSignature(updateParam.getSignature());
        student.setAccessory(updateParam.getAccessory());
        student.setRating(updateParam.getRating());
        student.setIntroduction(updateParam.getIntroduction());
    
        studentDao.save(student);
            
    }


    /**
     * 删除学生 
     * @param studentId    学生的ID 
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeStudentById(Long studentId){
        
        Student student = findStudentById(studentId);
        studentDao.delete(student);
            
    }




}