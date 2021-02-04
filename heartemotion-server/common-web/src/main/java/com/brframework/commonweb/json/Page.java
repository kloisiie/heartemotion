package com.brframework.commonweb.json;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu on 2017/1/5.
 * 返回分页数据
 */
@Data
@ApiModel
public class Page<T> {

    /**
     * 每页显示条数
     */
    @ApiModelProperty(value = "每页最大显示条数", required = true, example = "12")
    private int pageSize;
    /**
     * 当前页 1开始
     */
    @ApiModelProperty(value = "页索引(1开始)", required = true, example = "1")
    private int pageIndex;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数", required = true, example = "10")
    private int pageTotal;
    /**
     * 总数据条数
     */
    @ApiModelProperty(value = "总条数", required = true, example = "103")
    private long total;
    /**
     * 分页数据上
     */
    @ApiModelProperty(value = "分页数据", required = true)
    private List<T> list = new ArrayList<>();

    public Page(int pageIndex, int pageSize, long total) {
        //页码转换到前端，由1开始
        this.pageIndex = pageIndex + 1;
        this.pageSize = pageSize;
        this.total = total;
        this.pageTotal = (int) ( total % pageSize == 0 ? total / pageSize : total / pageSize + 1 );
    }

    public Page(){}

    /**
     * 获取分页开始的索引
     *
     * @return
     */
    @JSONField(serialize = false)
    public int getStartIndex() {
        return (pageIndex - 1) * pageSize;
    }

    /**
     * 获取分页结束的索引
     *
     * @return
     */
    @JSONField(serialize = false)
    public int getEndIndex() {
        return pageIndex * pageSize;
    }

    public static Page copyPage(Page copyer) {
        return new Page(copyer.getPageIndex(), copyer.getPageSize(), copyer.getTotal());
    }
}
