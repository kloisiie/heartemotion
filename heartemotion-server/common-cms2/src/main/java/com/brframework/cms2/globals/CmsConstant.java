package com.brframework.cms2.globals;

/**
 * 公用的常量
 * @author xu
 * @date 2020/3/8 2:27
 */
public interface CmsConstant {

    /**
     * 逻辑删除
     */
    enum Delete {
        DELETE("删除"),
        NORMAL("正常");

        private String d;
        public String describe(){return d;}
        Delete(String describe){this.d = describe;}
    }

    /** 是否 */
    enum Is {
        NO("否"),
        YES("是");

        private String d;
        public String describe(){return d;}
        Is(String describe){this.d = describe;}
    }

    /** 页面类型 */
    enum PageType{
        STATIC("静态"),
        DYNAMIC("动态");

        private String d;
        public String describe(){return d;}
        PageType(String describe){this.d = describe;}
    }

    /** 菜单类型 */
    enum MenuType{
        LEFT("左菜单");

        private String d;
        public String describe(){return d;}
        MenuType(String describe){this.d = describe;}
    }
}
