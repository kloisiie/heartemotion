package cn.bluetech.gragas.web.admin;

import java.lang.*;
import java.util.*;

import com.brframework.commonweb.json.PageParam;
import cn.bluetech.gragas.entity.user.*;
import cn.bluetech.gragas.pojo.user.*;
import cn.bluetech.gragas.service.user.*;
import cn.bluetech.gragas.json.admin.user.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.json.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.validation.Valid;
import static com.brframework.commondb.core.ControllerPageHandle.*;


/**
 * 学生模块
 * @author xu
 * @date 2020-11-19 10:57:12
 */ 
@RestController
@Api(tags = "学生模块")
@RequestMapping("/admin/access")
@Slf4j
public class AdminStudentController{

    /** 学生Service */
    @Autowired
    public StudentService studentService;


    
    @ApiOperation(value = "学生列表", notes = "学生列表")
    @GetMapping("/v1/user/student/list")
    @PreAuthorize("hasRole('user-student-query')")
    public JSONResult<Page<StudentQueryResult>> studentPageV1(PageParam pageParam,
                                                              StudentPageQueryParam queryParam){
        
        StudentPageQueryParamDTO dtoParam = new StudentPageQueryParamDTO();
        dtoParam.setName(queryParam.getName());
        if(StringUtils.isNotBlank(queryParam.getAgeBetween())){
            String[] split = queryParam.getAgeBetween().split(",");
            dtoParam.setAgeBetweenStart(Integer.parseInt(split[0]));
            dtoParam.setAgeBetweenEnd(Integer.parseInt(split[1]));
        }

        dtoParam.setHobby(queryParam.getHobby());
        if(queryParam.getBirthdayBetween() != null && queryParam.getBirthdayBetween().size() != 0){
            dtoParam.setBirthdayBetweenStart(queryParam.getBirthdayBetween().get(0));
            dtoParam.setBirthdayBetweenEnd(queryParam.getBirthdayBetween().get(1));
        }

        if(StringUtils.isNotBlank(queryParam.getProvinceCityDistrict())){
            String[] split = queryParam.getProvinceCityDistrict().split(",");
            String province = split[0].equals("-1") ? null : split[0];
            String city = split[1].equals("-1") ? null : split[1];
            String district = split[2].equals("-1") ? null : split[2];

            dtoParam.setProvince(province);
            dtoParam.setCity(city);
            dtoParam.setDistrict(district);
        }


        dtoParam.setRating(queryParam.getRating());
    
        return JSONResult.ok(jpaPageHandleToPage(
                studentService.findStudentPage(pageParam, dtoParam), pageParam,
                this::studentToResult
        ));
            
    }


    
    @ApiOperation(value = "学生详情", notes = "学生详情")
    @GetMapping("/v1/user/student/details/{id}")
    @PreAuthorize("hasRole('user-student-query')")
    public JSONResult<StudentDetailResult> studentDetailsV1(@PathVariable Long id){
        
        return JSONResult.ok(studentToDetailResult(studentService.findStudentById(id)));
            
    }


    
    @ApiOperation(value = "添加学生", notes = "添加学生")
    @PostMapping("/v1/user/student/add")
    @PreAuthorize("hasRole('user-student-add')")
    public JSONResult studentAddV1(@Valid StudentInsertParam insertParam){
        StudentInsertParamDTO dtoParam = new StudentInsertParamDTO();
        dtoParam.setName(insertParam.getName());
        dtoParam.setAge(insertParam.getAge());
        dtoParam.setHobby(insertParam.getHobby());
        dtoParam.setBirthday(insertParam.getBirthday());
        dtoParam.setProvince(insertParam.getProvinceCityDistrict().get(0));
        dtoParam.setCity(insertParam.getProvinceCityDistrict().get(1));
        dtoParam.setDistrict(insertParam.getProvinceCityDistrict().get(2));
        dtoParam.setHeadImage(insertParam.getHeadImage().get(0));
        dtoParam.setImages(insertParam.getImages());
        dtoParam.setSignature(insertParam.getSignature());
        dtoParam.setAccessory(insertParam.getAccessory());
        dtoParam.setRating(insertParam.getRating());
        dtoParam.setIntroduction(insertParam.getIntroduction());
    
        studentService.insertStudent(dtoParam);
        return JSONResult.ok();
            
    }


    
    @ApiOperation(value = "修改学生", notes = "修改学生")
    @PostMapping("/v1/user/student/update/{id}")
    @PreAuthorize("hasRole('user-student-update')")
    public JSONResult studentUpdateV1(@PathVariable Long id, @Valid StudentUpdateParam updateParam){
        StudentUpdateParamDTO dtoParam = new StudentUpdateParamDTO();
        dtoParam.setName(updateParam.getName());
        dtoParam.setAge(updateParam.getAge());
        dtoParam.setHobby(updateParam.getHobby());
        dtoParam.setBirthday(updateParam.getBirthday());
        dtoParam.setProvince(updateParam.getProvinceCityDistrict().get(0));
        dtoParam.setCity(updateParam.getProvinceCityDistrict().get(1));
        dtoParam.setDistrict(updateParam.getProvinceCityDistrict().get(2));
        dtoParam.setHeadImage(updateParam.getHeadImage().get(0));
        dtoParam.setImages(updateParam.getImages());
        dtoParam.setSignature(updateParam.getSignature());
        dtoParam.setAccessory(updateParam.getAccessory());
        dtoParam.setRating(updateParam.getRating());
        dtoParam.setIntroduction(updateParam.getIntroduction());
    
        studentService.updateStudent(id, dtoParam);
        return JSONResult.ok();
    }

    @ApiOperation(value = "删除学生", notes = "删除学生")
    @DeleteMapping("/v1/user/student/{id}")
    @PreAuthorize("hasRole('user-student-del')")
    public JSONResult studentDeleteV1(@PathVariable("id") Long id){
        studentService.removeStudentById(id);
        return JSONResult.ok();
    }


    @ApiOperation(value = "批量删除学生", notes = "批量删除学生")
    @DeleteMapping("/v1/user/student/batch-del")
    @PreAuthorize("hasRole('user-student-del')")
    public JSONResult studentBatchDeleteV1(@RequestParam("ids") List<Long> ids){

        for (Long id : ids) {
            studentService.removeStudentById(id);
        }
        return JSONResult.ok();

    }


    /**
     * 学生Result转换 
     * @param student    学生 
     */
    private StudentQueryResult studentToResult(Student student){
        
        StudentQueryResult result = new StudentQueryResult();
        result.setId(student.getId());
        result.setCreateDate(student.getCreateDate());
        result.setName(student.getName());
        result.setAge(student.getAge());
        result.setHobby(student.getHobby());
        result.setBirthday(student.getBirthday());
        result.setProvinceCityDistrict(student.getProvince() + "-" + student.getCity() + "-" +
                student.getDistrict());
        result.setHeadImage(Lists.newArrayList(student.getHeadImage()));
        result.setImages(student.getImages());
        result.setSignature(student.getSignature());
        result.setAccessory(student.getAccessory());
        result.setRating(student.getRating());
    
        return result;
            
    }


    /**
     * 学生DetailResult转换 
     * @param student    学生 
     */
    private StudentDetailResult studentToDetailResult(Student student){
        
        StudentDetailResult result = new StudentDetailResult();
        result.setId(student.getId());
        result.setCreateDate(student.getCreateDate());
        result.setName(student.getName());
        result.setAge(student.getAge());
        result.setHobby(student.getHobby());
        result.setBirthday(student.getBirthday());
        result.setProvinceCityDistrict(Lists.newArrayList(student.getProvince() , student.getCity(),
                student.getDistrict()));
        result.setHeadImage(Lists.newArrayList(student.getHeadImage()));
        result.setImages(student.getImages());
        result.setSignature(student.getSignature());
        result.setAccessory(student.getAccessory());
        result.setRating(student.getRating());
        result.setIntroduction(student.getIntroduction());
    
        return result;
            
    }




}