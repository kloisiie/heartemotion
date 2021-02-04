package com.brframework.core.template;

import com.brframework.constace.Visible;
import com.brframework.utils.TemplateUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 抽象方法模板
 * @author xu
 * @date 2020/7/29 14:22
 */
public class JavaMethodTemplate implements Template {

    public static String TEMPLATE_PATH = "/JavaMethod.template";
    private Builder builder;

    private JavaMethodTemplate(Builder builder){
        this.builder = builder;
    }

    public static Builder builder(){
        return new Builder();
    }

    @Override
    public String strings() {
        String templateContent = TemplateUtils.readResource(TEMPLATE_PATH);
        String explain = "/**\n" +
                " ${methodExplain} ${paramsExplain} ${returnExplain}\n" +
                " */";

        if(StringUtils.isNotEmpty(builder.methodExplain)){
            templateContent = explain + templateContent;
        }

        Map<String, String> attributes = TemplateUtils.objectToMap(builder);
        return TemplateUtils.setTemplateAttribute(templateContent, attributes);
    }



    public static class Builder{
        private String methodExplain = "";
        private String paramsExplain = "";
        private String visible = Visible.PUBLIC.code();
        private String returnExplain = "";
        private String methodAnnotations = "";
        private String returnType = "void";
        private String methodName;
        private String params = "";
        private String body = ";";

        /**
         * 方法注释
         * @param methodExplain 方法注释
         * @return 构建
         */
        public Builder methodExplain(String methodExplain){
            if(StringUtils.isEmpty(methodExplain)) return this;
            this.methodExplain = "* " + methodExplain;
            return this;
        }

        /**
         * 参数注释
         * @param paramsExplains 参数注释，例如：name:张三
         * @return 构建
         */
        public Builder paramsExplain(Map<String, String> paramsExplains){
            if(paramsExplains.size() == 0) return this;
            StringBuilder sb = new StringBuilder();
            paramsExplains.forEach((key, value) ->
                    sb.append("\n").append(" * ").append("@param ").append(key)
                            .append("    ").append(value));

            this.paramsExplain = sb.toString();
            return this;
        }

        /**
         * 方法注解
         * @param methodAnnotationList  方法注解
         * @return  构建
         */
        public Builder methodAnnotations(List<String> methodAnnotationList){
            if(methodAnnotationList.size() == 0) return this;
            StringBuilder sb = new StringBuilder();
            for (String s : methodAnnotationList) {
                sb.append("\n");
                sb.append(s);
            }
            this.methodAnnotations = sb.toString();
            return this;
        }

        /**
         * 返回注释
         * @param returnExplain  返回注释
         * @return  构建
         */
        public Builder returnExplain(String returnExplain){
            if(StringUtils.isEmpty(returnExplain)) return this;
            this.returnExplain = "\n * @return " + returnExplain;
            return this;
        }

        /**
         * 可见类型
         * @param visible  返回类型
         * @return  构建
         */
        public Builder visible(Visible visible){
            this.visible = visible.code();
            return this;
        }

        /**
         * 返回类型
         * @param returnType  返回类型
         * @return  构建
         */
        public Builder returnType(String returnType){
            if(StringUtils.isEmpty(returnType)) return this;
            this.returnType = returnType;
            return this;
        }

        /**
         * 方法名称
         * @param methodName  方法名称
         * @return  构建
         */
        public Builder methodName(String methodName){
            if(StringUtils.isEmpty(methodName)) return this;
            this.methodName = methodName;
            return this;
        }

        /**
         * 方法参数
         * @param params  方法参数{type}:{name}，例如：String:nickname
         * @return  构建
         */
        public Builder params(List<JavaParamTemplate> params){
            if(params.size() == 0) return this;
            this.params = StringUtils.join(params.stream().map(JavaParamTemplate::strings)
                    .collect(Collectors.toList()), ", ");
            return this;
        }

        /**
         * 方法体
         * @param body 方法体内容
         * @return  构建
         */
        public Builder body(String body){
            if(StringUtils.isEmpty(body)) return this;
            this.body = "{\n    " + body + "\n}";
            return this;
        }


        public JavaMethodTemplate build(){
            if(methodName == null){
                throw new IllegalArgumentException("请输入方法名");
            }
            return new JavaMethodTemplate(this);
        }
    }

}
