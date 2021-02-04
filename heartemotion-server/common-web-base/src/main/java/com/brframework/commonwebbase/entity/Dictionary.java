package com.brframework.commonwebbase.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by xu
 *
 *  字典
 *
 * @author xu
 * @date 18-4-19 上午9:49
 */
@Data
@Entity
@Table(name = "sys_dictionary",
        indexes = {
                @Index(name = "DICTIONARY_KEY", columnList = "_key", unique = true) })
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", required = true, example = "1")
    Integer id;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", example = "2018-03-13 15:26:19")
    LocalDateTime createDate;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间", example = "2018-03-13 15:26:19")
    LocalDateTime updateDate;

    @ApiModelProperty(value = "键(唯一)", example = "key")
    @Column(name = "_key", length = 32)
    String key;

    @ApiModelProperty(value = "值", example = "value")
    @Column(length = Integer.MAX_VALUE)
    String value;

}
