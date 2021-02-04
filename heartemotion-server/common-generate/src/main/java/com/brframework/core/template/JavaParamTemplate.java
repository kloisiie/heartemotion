package com.brframework.core.template;

import com.brframework.utils.TemplateUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 参数模板
 * @author xu
 * @date 2020/7/29 14:54
 */
public class JavaParamTemplate implements Template {
    public static String TEMPLATE_PATH = "/JavaParam.template";
    private Builder builder;

    private JavaParamTemplate(Builder builder){
        this.builder = builder;
    }

    public static Builder builder(){
        return new Builder();
    }

    @Override
    public String strings() {
        String templateContent = TemplateUtils.readResource(TEMPLATE_PATH);
        Map<String, String> attributes = TemplateUtils.objectToMap(builder);
        return TemplateUtils.setTemplateAttribute(templateContent, attributes);
    }

    public static class Builder {
        private String paramAnnotations = "";
        private String paramType;
        private String paramName;

        /**
         * 参数注解
         * @param paramAnnotationList  参数注解
         * @return  构建
         */
        public Builder paramAnnotations(List<String> paramAnnotationList){
            if(paramAnnotationList.size() == 0) return this;
            StringBuilder sb = new StringBuilder();
            for (String s : paramAnnotationList) {
                sb.append(s).append(" ");
            }
            this.paramAnnotations = sb.toString();
            return this;
        }

        /**
         * 参数类型
         * @param paramType  参数类型
         * @return  构建
         */
        public Builder paramType(String paramType){
            if(StringUtils.isEmpty(paramType)) return this;
            this.paramType = paramType;
            return this;
        }

        /**
         * 参数名称
         * @param paramName  参数名称
         * @return  构建
         */
        public Builder paramName(String paramName){
            if(StringUtils.isEmpty(paramName)) return this;
            this.paramName = paramName;
            return this;
        }

        public JavaParamTemplate build(){
            if(paramType == null){
                throw new IllegalArgumentException("请输入参数类型");
            }
            if(paramName == null){
                throw new IllegalArgumentException("请输入参数名");
            }
            return new JavaParamTemplate(this);
        }
    }
}
