package com.brframework.commonwebadmin.globals;

/**
 * @author xu
 * @date 2020/6/12 20:38
 */
public interface AdminConstant {

    /** 资源类型 */
    enum ResourceType {
        MAIN("入口"),
        COMMON("普通"),
        COLUMN("列操作");

        private String d;
        public String describe() {
            return d;
        }
        ResourceType(String describe) {
            this.d = describe;
        }
    }

}
