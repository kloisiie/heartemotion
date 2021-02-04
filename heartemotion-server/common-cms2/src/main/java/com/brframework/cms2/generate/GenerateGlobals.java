package com.brframework.cms2.generate;

/**
 * @author xu
 * @date 2020/12/8 11:48
 */
public interface GenerateGlobals {

    /**
     * 表格数据列的类型
     */
    public enum TableColumnType{
        /** 文本类型 */
        text,
        /** 图片类型 */
        image,
        /** 操作类型 */
        action
    }

    /**
     * 协议类型
     */
    public enum ProtocolType{
        /** 路由协议 */
        route,
        /** 请求协议 */
        request
    }


    /**
     * 表单按钮类型
     */
    public enum FormButtonType{
        /** 返回按钮 */
        back,
        /** 清楚按钮 */
        clear,
        /** 提交按钮 */
        post
    }


    public enum ButtonStyle{
        /** 默认 */
        primary,
        /** 危险 */
        danger,
        /** 成功 */
        success,
        /** 信息 */
        info,
        ghost
    }

    public enum ButtonType{
        /** 文本按钮 */
        text,
        /** 普通按钮 */
        button
    }

    public enum LayoutType{
        /** 栅格布局 */
        grid,
        /** 列布局 */
        col,
        /** 头布局 */
        header,
        /** 功能栏布局 */
        action,
        /** 搜索栏布局 */
        search,
        /** 表格布局 */
        table,
        /** 分页列表布局 */
        list,
        /** form布局 */
        form
    }

    public enum ColumnType{

        /** 文本输入框 */
        input("input"),
        /** 颜色选择器 */
        color_picker("color-picker"),
        /** 开关 */
        switch_("switch"),
        /** 日期选择器-年选择 */
        date_picker_year("date-picker"),
        /** 日期选择器-月份选择 */
        date_picker_month("date-picker"),
        /** 日期选择器-日期选择 */
        date_picker_date("date-picker"),
        /** 日期选择器-日期范围选择 */
        date_picker_daterange("date-picker"),
        /** 日期选择器-月份选择 */
        date_picker_monthrange("date-picker"),
        /** 数字 */
        number("number"),
        /** 时间选择器 */
        time_picker("time-picker"),
        /** 级联选择器 */
        cascader("cascader"),
        /** 日期时间选择器 */
        date_time_picker("date-time-picker"),
        /** 评分 */
        rate("rate"),
        /** 多选框 */
        checkbox("checkbox"),
        /** 下拉框 */
        select("select"),
        /** 单选框 */
        radio("radio"),
        /** 文件上传 */
        file_upload("file-upload"),
        /** 图片上传 */
        image_upload("image-upload"),
        /** 音视频上传 */
        media_upload("media-upload"),
        /** 文本域 */
        textarea("textarea"),
        /** 富文本 */
        rich_text("rich-text");

        private String typeName;

        ColumnType(String typeName){
            this.typeName = typeName;
        }

        public String getTypeName(){
            return this.typeName;
        }

        @Override
        public String toString() {
            return typeName;
        }
    }

}
