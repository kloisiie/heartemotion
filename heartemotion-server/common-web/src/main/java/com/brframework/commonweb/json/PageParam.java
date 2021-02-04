package com.brframework.commonweb.json;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;

/**
 * @Author xu
 * @Date 2018/1/16 0016 下午 12:03
 */
@ApiModel
public class PageParam {

    @ApiModelProperty(value = "页索引(1开始)", required = true, example = "1")
    @Range(min = 0, message = "pageIndex必须从1开始")
    private int pageIndex = 1;
    @ApiModelProperty(value = "返回数量", required = true, example = "12")
    @Range(min = 1, max = 200, message = "pageSize最大不能超过200")
    private int pageSize = 12;


    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        //服务端，页索引是从0开始的，所以要减一
        this.pageIndex = pageIndex - 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
