package cn.bluetech.gragas.json.admin.user;

import java.time.LocalDate;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import javax.validation.constraints.NotNull;
import java.lang.Integer;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;


/**
 * 学生修改参数
 * @author xu
 * @date 2020-11-19 10:57:12
 */ 
@ApiModel
@Data
public class StudentUpdateParam extends BaseJson {

    
    @ApiModelProperty(value = "名称", required = true, example = "张三")
    @Length(min = 1, max = 50, message = "名称必须在1 - 50个字符之间")
    private String name;
    
    @ApiModelProperty(value = "年龄", required = true, example = "10")
    @NotNull(message = "年龄不能为空")
    @Range(min = 1, max = 100)
    private Integer age;
    
    @ApiModelProperty(value = "爱好", required = true, example = "唱,跳,rap")
    @NotNull(message = "爱好不能为空")
    private List<String> hobby;
    
    @ApiModelProperty(value = "生日", required = true, example = "2012-12-12")
    @NotNull(message = "生日不能为空")
    @JSONField(format = "yyyy-MM-dd")
    private LocalDate birthday;

    @ApiModelProperty(value = "省市区", required = true, example = "2012-12-12")
    @NotNull(message = "省不能为空")
    private List<String> provinceCityDistrict;
    
    @ApiModelProperty(value = "头像", required = true, example = "http://....")
    @NotNull(message = "头像不能为空")
    private List<String> headImage;
    
    @ApiModelProperty(value = "生活照片", required = true, example = "http://....,http://...")
    @NotNull(message = "生活照片不能为空")
    private List<String> images;
    
    @ApiModelProperty(value = "个性签名", required = true, example = "我喜欢打球，多行文本")
    @NotNull(message = "个性签名不能为空")
    private String signature;
    
    @ApiModelProperty(value = "附件", required = true, example = "http://....,http://...")
    @NotNull(message = "附件不能为空")
    private List<String> accessory;
    
    @ApiModelProperty(value = "自我评分", required = true, example = "3")
    @NotNull(message = "自我评分不能为空")
    private Integer rating;
    
    @ApiModelProperty(value = "自我介绍", required = true, example = "html")
    @NotNull(message = "自我介绍不能为空")
    private String introduction;




}