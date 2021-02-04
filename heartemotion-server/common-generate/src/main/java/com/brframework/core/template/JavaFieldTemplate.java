package com.brframework.core.template;

import com.brframework.constace.Visible;
import com.brframework.utils.TemplateUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 字段模板
 * @author xu
 * @date 2020/7/29 14:23
 */
public class JavaFieldTemplate implements Template {
    public static String TEMPLATE_PATH = "/JavaField.template";
    private Builder builder;

    private JavaFieldTemplate(Builder builder){
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

    public static class Builder{
        private String fieldExplain = "";
        private String fieldAnnotations = "";
        private String visible = Visible.PUBLIC.code();
        private String fieldType;
        private String fieldName;


        /**
         * 字段注释
         * @param fieldExplain  字段注释
         * @return  构建
         */
        public Builder fieldExplain(String fieldExplain){
            if(StringUtils.isEmpty(fieldExplain)) return this;
            this.fieldExplain = "/** " + fieldExplain + " */";
            return this;
        }

        /**
         * 字段注解
         * @param fieldAnnotationList  字段注解
         * @return  构建
         */
        public Builder fieldAnnotations(List<String> fieldAnnotationList){
            if(fieldAnnotationList.size() == 0) return this;
            StringBuilder sb = new StringBuilder();
            for (String s : fieldAnnotationList) {
                sb.append("\n");
                sb.append(s);
            }
            this.fieldAnnotations = sb.toString();
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
         * 字段类型
         * @param fieldType  字段类型
         * @return  构建
         */
        public Builder fieldType(String fieldType){
            if(StringUtils.isEmpty(fieldType)) return this;
            this.fieldType = fieldType;
            return this;
        }

        /**
         * 字段名称
         * @param fieldName  字段名称
         * @return  构建
         */
        public Builder fieldName(String fieldName){
            if(StringUtils.isEmpty(fieldName)) return this;
            this.fieldName = fieldName;
            return this;
        }

        public JavaFieldTemplate build(){
            if(fieldType == null){
                throw new IllegalArgumentException("请输入字段类型");
            }
            if(fieldName == null){
                throw new IllegalArgumentException("请输入字段名");
            }
            return new JavaFieldTemplate(this);
        }
    }
}
