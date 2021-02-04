package com.brframework.core.template;

import com.brframework.common.utils.DateTimeUtils;
import com.brframework.constace.ClassType;
import com.brframework.utils.TemplateUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 类模板
 * @author xu
 * @date 2020/7/29 14:23
 */
public class JavaClassTemplate implements Template {
    public static String TEMPLATE_PATH = "/JavaClass.template";

    @Override
    public String strings() {
        String templateContent = TemplateUtils.readResource(TEMPLATE_PATH);
        Map<String, String> attributes = TemplateUtils.objectToMap(builder);

        return TemplateUtils.setTemplateAttribute(templateContent, attributes);
    }

    private Builder builder;

    private JavaClassTemplate(Builder builder){
        this.builder = builder;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String packageName = "";
        private String type = "";
        private String importList = "";
        private String classExplain = "";
        private String classAuthor = "";
        private String createDate = DateTimeUtils.dateTimeToString(LocalDateTime.now());
        private String classAnnotations = "";
        private String className;
        private String extendsClass = "";
        private String implementsClass = "";
        private String fields = "";
        private String methods = "";

        /**
         * 包名
         * @param packageName 包名
         * @return 构建
         */
        public Builder packageName(String packageName){
            if(StringUtils.isEmpty(packageName)) return this;
            this.packageName = "package " + packageName + ";";
            return this;
        }

        /**
         * 类类型
         * @param type 类类型
         * @return 构建
         */
        public Builder type(ClassType type){
            this.type = type.code();
            return this;
        }

        /**
         * 导入依赖列表
         * @param importList 导入依赖列表
         * @return 构建
         */
        public Builder importList(List<String> importList){
            if(importList.size() == 0) return this;
            StringBuilder sb = new StringBuilder();
            for (String s : importList) {
                sb.append("import ");
                sb.append(s);
                sb.append(";\n");
            }
            this.importList = sb.toString();
            return this;
        }

        /**
         * 类注释说明
         * @param classExplain 类注释说明
         * @return 构建
         */
        public Builder classExplain(String classExplain){
            if(StringUtils.isEmpty(classExplain)) return this;
            this.classExplain = "* " + classExplain;
            return this;
        }

        /**
         * 作者
         * @param classAuthor 作者
         * @return 构建
         */
        public Builder classAuthor(String classAuthor){
            if(StringUtils.isEmpty(classAuthor)) return this;
            this.classAuthor = classAuthor;
            return this;
        }

        /**
         * 时间
         * @param createDate 时间
         * @return 构建
         */
        public Builder createDate(String createDate){
            if(StringUtils.isEmpty(createDate)) return this;
            this.createDate = createDate;
            return this;
        }

        /**
         * 类注解
         * @param classAnnotationList  类注解
         * @return  构建
         */
        public Builder classAnnotations(List<String> classAnnotationList){
            if(classAnnotationList.size() == 0) return this;
            StringBuilder sb = new StringBuilder();
            for (String s : classAnnotationList) {
                sb.append("\n");
                sb.append(s);
            }
            this.classAnnotations = sb.toString();
            return this;
        }

        /**
         * 类名
         * @param className 类名
         * @return 构建
         */
        public Builder className(String className){
            if(StringUtils.isEmpty(className)) return this;
            this.className = className;
            return this;
        }

        /**
         * 类继承
         * @param extendsClass 类继承
         * @return 构建
         */
        public Builder extendsClass(String extendsClass){
            if(StringUtils.isEmpty(extendsClass)) return this;
            this.extendsClass = " extends " + extendsClass;
            return this;
        }

        /**
         * 类实现
         * @param implementsClass 类实现
         * @return 构建
         */
        public Builder implementsClass(String implementsClass){
            if(StringUtils.isEmpty(implementsClass)) return this;
            this.implementsClass = " implements " + implementsClass;
            return this;
        }

        /**
         * 类字段
         * @param fieldTemplates 字段模板实例
         * @return 构建
         */
        public Builder fields(List<JavaFieldTemplate> fieldTemplates){
            if(fieldTemplates.size() == 0) return this;
            StringBuilder fieldSb = new StringBuilder();
            for (JavaFieldTemplate fieldTemplate : fieldTemplates) {
                for (String s : fieldTemplate.strings().split("\n")) {
                    fieldSb.append("    ");
                    fieldSb.append(s);
                    fieldSb.append("\n");
                }
            }

            this.fields = fieldSb.toString();
            return this;
        }

        /**
         * 类方法
         * @param methodTemplates 方法模板实例
         * @return 构建
         */
        public Builder methods(List<JavaMethodTemplate> methodTemplates){
            if(methodTemplates.size() == 0) return this;
            StringBuilder methodSb = new StringBuilder();
            for (JavaMethodTemplate methodTemplate : methodTemplates) {
                for (String s : methodTemplate.strings().split("\n")) {
                    methodSb.append("    ");
                    methodSb.append(s);
                    methodSb.append("\n");
                }
                methodSb.append("\n\n");
            }

            this.methods = methodSb.toString();
            return this;
        }

        public JavaClassTemplate build(){
            if(className == null){
                throw new IllegalArgumentException("请输入类名");
            }
            if(type == null){
                throw new IllegalArgumentException("请输入类类型");
            }
            return new JavaClassTemplate(this);
        }
    }
}
