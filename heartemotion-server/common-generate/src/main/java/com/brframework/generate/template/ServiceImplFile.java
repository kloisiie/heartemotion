package com.brframework.generate.template;

import com.brframework.generate.BaseUtil;
import com.brframework.generate.Constant;
import com.brframework.generate.base.JavaFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.brframework.generate.Constant.PACKAGE_ENTITY;

/**
 * @Author: ljr
 * @Date: 2019/8/15 10:57
 * @Description:
 */
public class ServiceImplFile extends JavaFile {

    public ServiceImplFile(String route, String packageDir, String entity, String function, Map<String, String> notes) {
        fileDir = route + "/" + Constant.PACKAGE_SERVICE + "/" + function + "/" + Constant.PACKAGE_SERVICE_IMPL;
        packageDir = BaseUtil.toPackage(packageDir);
        String className = BaseUtil.getClassWithoutSuffix(entity);
        fileName = BaseUtil.getClassWithModule(entity, Constant.MODULE_SERVICE + Constant.MODULE_SERVICE_IMPL) + Constant.SUFFIX_CLASS_TYPE;
        filePackageName = packageDir + "." + Constant.PACKAGE_SERVICE + "." + Constant.PACKAGE_SERVICE_IMPL + "." + function;
        type = Constant.CLASS_TYPE;
        name = BaseUtil.getClassWithModule(entity, Constant.MODULE_SERVICE + Constant.MODULE_SERVICE_IMPL );
        List<String> imports = getImports(packageDir, function, entity, className);
        List<String> classAnnotations = getClassAnnotations();
        List<String> extendss = getExtendss(className);
        List<String> implementss = getImplementss(className);
        List<Field> fields = getFields(entity, className);
        List<Method> methods = getMethods(entity, className);
        super.init(imports, notes, classAnnotations, extendss, implementss, fields, methods);
    }

    public List<String> getImports(String mainPackage, String function, String entity, String className){
        List<String> imports = new LinkedList<>();
        imports.add(BaseUtil.getImportName(mainPackage, function, className, Constant.PACKAGE_ENTITY).toString());
        imports.add(mainPackage + "." +  Constant.PACKAGE_DAO + "." + function+ "." + className + Constant.MODULE_DAO );
        imports.add(mainPackage + "." +  Constant.PACKAGE_SERVICE + "." + function+ "." + className + Constant.MODULE_SERVICE );
        imports.add("com.brframework.commondb.core.AbstractEntityService");
        imports.add("com.brframework.commondb.core.CommonRepository");
        imports.add("lombok.extern.slf4j.Slf4j");
        imports.add("org.springframework.beans.factory.annotation.Autowired");
        imports.add("org.springframework.stereotype.Service");
        return imports;
    }

    public List<String> getClassAnnotations(){
        List<String> classAnnotations = new LinkedList<>();
        classAnnotations.add("Slf4j");
        classAnnotations.add("Service");
        return classAnnotations;
    }

    public List<String> getExtendss(String className){
        List<String> extendss = new LinkedList<>();
        extendss.add("AbstractEntityService<" + className + ", Long, Object>");
        return extendss;
    }

    public List<String> getImplementss(String className){
        List<String> implementss = new LinkedList<>();
        implementss.add(new StringBuilder().append(className).append(Constant.MODULE_SERVICE).toString());
        return implementss;
    }

    public List<Field> getFields(String entity, String className){
        List<Field> fields = new LinkedList<>();
        List<String> fannotations = new LinkedList<>();
        fannotations.add("Autowired");
        String fmodifier = "private";
        String ftype = new StringBuilder().append(className).append(Constant.MODULE_DAO).toString();
        String fname = entity + Constant.MODULE_DAO;
        String value = null;
        Field field = new Field(fannotations, fmodifier , ftype, fname, value);
        fields.add(field);
        return fields;
    }

    public List<Method> getMethods(String entity, String className){
        List<Method> methods = new LinkedList<>();
        List<String> mannotations = new LinkedList<>();
        mannotations.add("Override");
        String mmodifier = "public";
        String resultType;
        String name;
        String args;
        List<String> exceptions = new LinkedList<>();
        String methodBody = null;
        //getRepository

        resultType = "CommonRepository<Long, " + className + ">";
        name = "getRepository";
        args = "";
        //这里不需要其他方法体
        methodBody = BaseUtil.buildMethod(new String[] {
                new StringBuilder()
                        .append("return ")
                        .append(entity + Constant.MODULE_DAO)
                        .append(";")
                        .toString()
        });
        Method method = new Method(mannotations, mmodifier, resultType, name, args, exceptions, methodBody);
        methods.add(method);
        return methods;
    }
}
