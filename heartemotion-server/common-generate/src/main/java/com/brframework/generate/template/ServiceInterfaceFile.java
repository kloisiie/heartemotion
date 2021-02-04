package com.brframework.generate.template;

import com.brframework.generate.BaseUtil;
import com.brframework.generate.Constant;
import com.brframework.generate.base.JavaFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: ljr
 * @Date: 2019/8/15 10:37
 * @Description:
 */
public class ServiceInterfaceFile extends JavaFile {

    public ServiceInterfaceFile(String route, String packageDir, String entity, String function, Map<String, String> notes) {

        fileDir = route + "/" + Constant.PACKAGE_SERVICE + "/" + function;
        packageDir = BaseUtil.toPackage(packageDir);
        String className = BaseUtil.getClassWithoutSuffix(entity);
        fileName = BaseUtil.getClassWithModule(entity, Constant.MODULE_SERVICE) + Constant.SUFFIX_CLASS_TYPE;
        filePackageName = packageDir + "." + Constant.PACKAGE_SERVICE + "." + function;
        type = Constant.INTERFACE_TYPE;
        name = BaseUtil.getClassWithModule(entity, Constant.MODULE_SERVICE );
        String extendsService = "EntityService";
        List<String> imports = getImports(packageDir, function, entity, extendsService);
        List<String> classAnnotations = getClassAnnotations();
        List<String> extendss = getExtendss(extendsService, className);
        List<String> implementss = getImplementss();
        List<Field> fields = getFields();
        List<Method> methods = getMethods();
        super.init(imports, notes, classAnnotations, extendss, implementss, fields, methods);
    }

    public List<String> getImports(String mainPackage, String function, String entity, String extendsService){
        List<String> imports = new LinkedList<>();
        imports.add(BaseUtil.getImportName(mainPackage, function, entity, Constant.PACKAGE_ENTITY).toString());
        imports.add("com.brframework.commondb.core." + extendsService);
        return imports;
    }

    public List<String> getClassAnnotations(){
        List<String> classAnnotations = new LinkedList<>();
        return classAnnotations;
    }

    public List<String> getExtendss(String extendsService, String className){
        List<String> extendss = new LinkedList<>();
        extendss.add(extendsService + "<" + className +", Long, " + "Object>");
        return extendss;
    }

    public List<String> getImplementss(){
        List<String> implementss = new LinkedList<>();
        return implementss;
    }

    public List<Field> getFields(){
        List<Field> fields = new LinkedList<>();
        return fields;
    }

    public List<Method> getMethods(){
        List<Method> methods = new LinkedList<>();
        //methods,lombok不需要方法
        return methods;
    }
}
