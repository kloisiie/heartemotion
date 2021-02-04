package com.brframework.apidoc.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author xu
 * @date 2020/4/20 13:56
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="doc_mock_template", indexes = {
        @Index(name = "UN_IDX_API", columnList = "apiUrl,httpMethod", unique = true)
})
@Builder
public class MockTemplate {

    /** MOCK模板状态 -> 开启 */
    public static final int STATUS_OPEN = 1;
    /** MOCK模板状态 -> 关闭 */
    public static final int STATUS_CLOSED = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Long id;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", required = true, example = "2020-03-27 15:44:33")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "MOCK模板状态(1.开启 2.关闭)", required = true, example = "1")
    private Integer status;

    @ApiModelProperty(value = "接口URL", required = true, example = "/api/doc.html")
    private String apiUrl;
    @ApiModelProperty(value = "请求方法", required = true, example = "post")
    private String httpMethod;
    @ApiModelProperty(value = "MOCK模板", required = true, example = "template")
    @Column(length = Integer.MAX_VALUE)
    private String template;

}
