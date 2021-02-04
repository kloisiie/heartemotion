package cn.bluetech.gragas.pojo.user;

import java.time.LocalDate;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;


/**
 * 学生分页参数
 * @author xu
 * @date 2020-11-19 10:57:12
 */ 
@ApiModel
@Data
public class StudentPageQueryParamDTO{

    
    @ApiModelProperty(value = "名称", required = true, example = "张三")
    private String name;

    @ApiModelProperty(value = "年龄区间开始", required = true, example = "10")
    private Integer ageBetweenStart;

    @ApiModelProperty(value = "年龄区间结束", required = true, example = "10")
    private Integer ageBetweenEnd;
    
    @ApiModelProperty(value = "爱好", required = true, example = "唱,跳,rap")
    private List<String> hobby;

    @ApiModelProperty(value = "生日区间开始", required = true, example = "2012-12-12")
    private LocalDate birthdayBetweenStart;

    @ApiModelProperty(value = "生日区间结束", required = true, example = "2012-12-12")
    private LocalDate birthdayBetweenEnd;
    
    @ApiModelProperty(value = "省", required = true, example = "2012-12-12")
    private String province;
    
    @ApiModelProperty(value = "市", required = true, example = "2012-12-12")
    private String city;
    
    @ApiModelProperty(value = "区", required = true, example = "2012-12-12")
    private String district;
    
    @ApiModelProperty(value = "自我评分", required = true, example = "3")
    private Integer rating;




}