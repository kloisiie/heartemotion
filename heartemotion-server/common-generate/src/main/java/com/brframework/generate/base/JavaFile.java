package com.brframework.generate.base;

import com.brframework.generate.Constant;
import com.brframework.generate.GenerateUtil;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Map;

/**
 * @Author: ljr
 * @Date: 2019/8/13 17:49
 * @Description:
 */
public class JavaFile {

    /**
     * fileDir -> 要生成的文件目录, eg yigou/src/main/java/com/ns/gailun
     * fileName -> 生成的文件名,自动加后缀 eg Interflow.java
     * filePackageName -> 生成的文件的package包名
     * type ->
     * name -> 文件类型后面class名 eg public class Interflow{}
     */
    public String fileDir;
    public String fileName;
    public String filePackageName;
    public String type;
    public String name;

    /**
     * 这块是类组成的抽象
     * imports -> 导入的类
     * notes -> 注释,可设置Author,Date等属性
     * classAnnotations -> 类的注释,只需要注解名称,不需要@符
     * extendss -> 继承的类
     * implementss -> 实现的接口
     * fields -> 成员属性列表
     * methods -> 成员方法列表
     */
    public Collection<String> imports;
    public Map<String, String> notes;
    public Collection<String> classAnnotations;
    public Collection<String> extendss;
    public Collection<String> implementss;
    public Collection<Field> fields;
    public Collection<Method> methods;

    public String resource;

    public static class Field {
        /**
         * 类成员属性
         * annotations -> 类上的注解,只需要注解名称,不需要@符
         * modifier -> 限定词 private,public....
         * type -> 属性的类型
         * name -> 属性名称
         * value -> 属性初始值
         */
        public Collection<String> annotations;
        public String modifier;
        public String type;
        public String name;
        public String value;

        public Field(Collection<String> annotations, String modifier, String type, String name, String value) {
            this.annotations = annotations;
            this.modifier = modifier;
            this.type = type;
            this.name = name;
            this.value = value;
        }
    }

    public static class Method {
        /**
         * 方法成员属性
         * annotations -> 类上的注解,只需要注解名称,不需要@符
         * modifier -> 限定词 private,public....
         * resultType -> 方法返回值类型
         * name -> 方法名称
         * args -> 方法参数
         * exceptions -> 异常
         * methodBody -> 方法体
         */
        public Collection<String> annotations;
        public String modifier;
        public String resultType;
        public String name;
        public String args;
        public Collection<String> exceptions;
        public String methodBody;

        public Method(Collection<String> annotations, String modifier, String resultType, String name, String args, Collection<String> exceptions, String methodBody) {
            this.annotations = annotations;
            this.modifier = modifier;
            this.resultType = resultType;
            this.name = name;
            this.args = args;
            this.exceptions = exceptions;
            this.methodBody = methodBody;
        }
    }

    public JavaFile() {
    }

    /**
     * @param fileDir
     * @param fileName 与name不同,这个文件名带有.java
     * @param filePackageName
     * @param function        所属模块
     * @param type
     * @param name
     */
    public JavaFile(String fileDir, String fileName, String filePackageName, String function, String type, String name) {
        super();
        if (!Constant.CLASS_TYPE.equals(type) && !Constant.INTERFACE_TYPE.equals(type)) {
            throw new IllegalArgumentException("无法识别的类型");
        }
        this.fileDir = fileDir;
        this.fileName = fileName;
        this.filePackageName = filePackageName + "." + function;
        this.type = type;
        this.name = name;
    }

    public void init(String resource) {
        this.resource = resource;
    }

    public void init(Collection<String> imports,
                     Map<String, String> notes,
                     Collection<String> classAnnotations,
                     Collection<String> extendss,
                     Collection<String> implementss,
                     Collection<Field> fields,
                     Collection<Method> methods) {
        this.imports = imports;
        this.notes = notes;
        this.classAnnotations = classAnnotations;
        this.extendss = extendss;
        this.implementss = implementss;
        this.fields = fields;
        this.methods = methods;
    }


}
