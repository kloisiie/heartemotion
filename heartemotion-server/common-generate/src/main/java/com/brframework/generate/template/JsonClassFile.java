package com.brframework.generate.template;

import com.brframework.generate.BaseUtil;
import com.brframework.generate.Constant;
import com.brframework.generate.base.JavaFile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: ljr
 * @Date: 2019/8/15 16:09
 * @Description: import,注解,属性
 */
public class JsonClassFile extends JavaFile {

    public JsonClassFile(String route, String packageDir, String entity, String function, String jsonSuffix
            , Map<String, String> notes) {
        fileDir = route + "/" + Constant.PACKAGE_JSON + "/admin/" + function;
        fileName = BaseUtil.getClassWithoutSuffix(entity) + jsonSuffix + Constant.SUFFIX_CLASS_TYPE;
        packageDir = BaseUtil.toPackage(packageDir);
        filePackageName = packageDir + "." + Constant.PACKAGE_ADMIN + "." + function;
        type = Constant.CLASS_TYPE;
        name = BaseUtil.getClassWithoutSuffix(entity) + jsonSuffix;
        List<String> imports = getImports(jsonSuffix);
        List<String> classAnnotations = getClassAnnotations();
        List<String> extendss = getExtendss();
        List<String> implementss = getImplementss();
        List<Field> fields = getFields(jsonSuffix);
        List<Method> methods = getMethods();
        super.init(imports, notes, classAnnotations, extendss, implementss, fields, methods);
    }

    public List<String> getImports(String json){
        List<String> imports = new LinkedList<>();
        if (json.equals(Constant.JSON_NAME_QUERY)){
            imports.add("com.brframework.commondb.annotation.param.ParamQuery");
        }
        imports.add("io.swagger.annotations.ApiModel");
        imports.add("io.swagger.annotations.ApiModelProperty");
        imports.add("lombok.Data");
        return imports;
    }

    public List<String> getClassAnnotations(){
        List<String> classAnnotations = new LinkedList<>();
        classAnnotations.add("Data");
        classAnnotations.add("ApiModel");
        return classAnnotations;
    }

    public List<String> getExtendss(){
        List<String> extendss = new LinkedList<>();
        return extendss;
    }

    public List<String> getImplementss(){
        List<String> implementss = new LinkedList<>();
        return implementss;
    }

    public List<Field> getFields(String json){
        List<Field> fields = new LinkedList<>();
        //设置id的注解
        List<String> idFieldAnnotations = new ArrayList<>();
        if (json.equals(Constant.JSON_NAME_QUERY)){
            idFieldAnnotations.add("ParamQuery");
        }
        idFieldAnnotations.add("ApiModelProperty(value = \"id\", required = true, example = \"1\")");
        String fmodifier = "private";
        fields.add(BaseUtil.generateField(idFieldAnnotations, fmodifier , "Long", "id", null));
        return fields;
    }

    public List<Method> getMethods(){
        List<Method> methods = new LinkedList<>();
        //methods,lombok不需要方法
        return methods;
    }
}
