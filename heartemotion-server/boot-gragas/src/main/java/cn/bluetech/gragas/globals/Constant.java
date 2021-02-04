package cn.bluetech.gragas.globals;

/**
 * 公用的常量
 * @author xu
 * @date 2020/3/8 2:27
 */
public interface Constant {

    /**所有用户标记**/
    String CONSTANT_TO_ALL_MEMBER = "-1";

    enum Delete {
        /**
         * 逻辑删除
         */
        DELETE("删除"),
        NORMAL("正常");

        private String d;
        public String describe(){return d;}
        Delete(String describe){this.d = describe;}
    }


    enum Is {
        /**
         * 是否
         */
        NO("否"),
        YES("是");

        private String d;
        public String describe(){return d;}
        Is(String describe){this.d = describe;}
    }

}
