package cn.bluetech.gragas.json.admin.user;

import java.lang.Long;
import java.time.LocalDate;

import com.brframework.commonweb.json.BaseJson;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;


/**
 * 学生查询结果
 * @author xu
 * @date 2020-11-19 10:57:12
 */ 
@ApiModel
@Data
public class StudentQueryResult extends BaseJson {

    
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    
    @ApiModelProperty(value = "名称", required = true, example = "张三")
    private String name;
    
    @ApiModelProperty(value = "年龄", required = true, example = "10")
    private Integer age;
    
    @ApiModelProperty(value = "爱好", required = true, example = "唱,跳,rap")
    private List<String> hobby;
    
    @ApiModelProperty(value = "生日", required = true, example = "2012-12-12")
    @JSONField(format = "yyyy-MM-dd")
    private LocalDate birthday;

    @ApiModelProperty(value = "省市区", required = true, example = "2012-12-12")
    private String provinceCityDistrict;
    
    @ApiModelProperty(value = "头像", required = true, example = "http://....")
    private List<String> headImage;
    
    @ApiModelProperty(value = "生活照片", required = true, example = "http://....,http://...")
    private List<String> images;
    
    @ApiModelProperty(value = "个性签名", required = true, example = "我喜欢打球，多行文本")
    private String signature;
    
    @ApiModelProperty(value = "附件", required = true, example = "http://....,http://...")
    private List<String> accessory;
    
    @ApiModelProperty(value = "自我评分", required = true, example = "3")
    private Integer rating;




}