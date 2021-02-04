package cn.bluetech.gragas.json.admin.user;

import java.time.LocalDate;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;


/**
 * 学生查询参数
 * @author xu
 * @date 2020-11-19 10:57:12
 */ 
@ApiModel
@Data
public class StudentPageQueryParam extends BaseJson {

    
    @ApiModelProperty(value = "名称", required = true, example = "张三")
    private String name;
    
    @ApiModelProperty(value = "年龄区间", required = true, example = "10")
    private String ageBetween;
    
    @ApiModelProperty(value = "爱好", required = true, example = "唱,跳,rap")
    private List<String> hobby;
    
    @ApiModelProperty(value = "生日区间", required = true, example = "2012-12-12")
    @JSONField(format = "yyyy-MM-dd")
    private List<LocalDate> birthdayBetween;
    
    @ApiModelProperty(value = "省市区", required = true, example = "广东省,广州市,番禺区")
    private String provinceCityDistrict;

    @ApiModelProperty(value = "自我评分", required = true, example = "3")
    private Integer rating;




}