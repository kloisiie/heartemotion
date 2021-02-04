package cn.bluetech.gragas.pojo.user;

import java.time.LocalDate;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;


/**
 * 学生更新参数
 * @author xu
 * @date 2020-11-19 10:57:12
 */ 
@ApiModel
@Data
public class StudentUpdateParamDTO{

    
    @ApiModelProperty(value = "名称", required = true, example = "张三")
    private String name;
    
    @ApiModelProperty(value = "年龄", required = true, example = "10")
    private Integer age;
    
    @ApiModelProperty(value = "爱好", required = true, example = "唱,跳,rap")
    private List<String> hobby;
    
    @ApiModelProperty(value = "生日", required = true, example = "2012-12-12")
    @JSONField(format = "yyyy-MM-dd")
    private LocalDate birthday;
    
    @ApiModelProperty(value = "省", required = true, example = "2012-12-12")
    private String province;
    
    @ApiModelProperty(value = "市", required = true, example = "2012-12-12")
    private String city;
    
    @ApiModelProperty(value = "区", required = true, example = "2012-12-12")
    private String district;
    
    @ApiModelProperty(value = "头像", required = true, example = "http://....")
    private String headImage;
    
    @ApiModelProperty(value = "生活照片", required = true, example = "http://....,http://...")
    private List<String> images;
    
    @ApiModelProperty(value = "个性签名", required = true, example = "我喜欢打球，多行文本")
    private String signature;
    
    @ApiModelProperty(value = "附件", required = true, example = "http://....,http://...")
    private List<String> accessory;
    
    @ApiModelProperty(value = "自我评分", required = true, example = "3")
    private Integer rating;
    
    @ApiModelProperty(value = "自我介绍", required = true, example = "html")
    private String introduction;




}