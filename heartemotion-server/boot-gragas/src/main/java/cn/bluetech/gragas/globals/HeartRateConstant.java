package cn.bluetech.gragas.globals;

/**
 * 心率
 * @author xu
 * @date 2020/12/16 15:52
 */
public interface HeartRateConstant {

    /** 标注状态(无情绪、平稳、烦躁、高兴) */
    enum LabelStatus {
        NO_MOOD("无情绪"),
        CALMNESS("平稳"),
        AGITATED("烦躁"),
        HAPPY("高兴");

        private String d;
        public String describe(){return d;}
        LabelStatus(String describe){this.d = describe;}
    }


    /** 标注类型(手动标注、算法标注) */
    enum LabelType {
        MANUAL("手动标注"),
        ARITHMETIC("算法标注");

        private String d;
        public String describe(){return d;}
        LabelType(String describe){this.d = describe;}
    }


    /** 执行状态(进行中、成功、失败) */
    enum DebugStatus {
        RUNNING("进行中"),
        SUCCESS("成功"),
        DEFEATED("失败");

        private String d;
        public String describe(){return d;}
        DebugStatus(String describe){this.d = describe;}
    }

    /** 算法类型(基准算法、普通算法) */
    enum ArithmeticType {
        STANDARD("基准算法"),
        NORMAL("普通算法");

        private String d;
        public String describe(){return d;}
        ArithmeticType(String describe){this.d = describe;}
    }


}
