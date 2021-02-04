package cn.bluetech.gragas.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 学生
 * @author xu
 * @date 2020/11/17 17:34
 */
@Entity
@Data
@Table(name="user_student")
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", required = true, example = "2018-03-12 21:32:33")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "名称", required = true, example = "张三")
    private String name;

    @ApiModelProperty(value = "年龄", required = true, example = "10")
    private Integer age;

    @ApiModelProperty(value = "个性签名", required = true, example = "我喜欢打球，多行文本")
    @Column(length = Integer.MAX_VALUE)
    private String signature;

    @ApiModelProperty(value = "生日", required = true, example = "2012-12-12")
    @JSONField(format = "yyyy-MM-dd")
    private LocalDate birthday;

    @ApiModelProperty(value = "省", required = true, example = "2012-12-12")
    private String province;

    @ApiModelProperty(value = "市", required = true, example = "2012-12-12")
    private String city;

    @ApiModelProperty(value = "区", required = true, example = "2012-12-12")
    private String district;

    @ApiModelProperty(value = "爱好", required = true, example = "唱,跳,rap")
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> hobby;

    @ApiModelProperty(value = "头像", required = true, example = "http://....")
    private String headImage;

    @ApiModelProperty(value = "生活照片", required = true, example = "http://....,http://...")
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> images;

    @ApiModelProperty(value = "自我评分", required = true, example = "3")
    private Integer rating;

    @ApiModelProperty(value = "自我介绍", required = true, example = "html")
    @Column(length = Integer.MAX_VALUE)
    private String introduction;

    @ApiModelProperty(value = "附件", required = true, example = "http://....,http://...")
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> accessory;
}
