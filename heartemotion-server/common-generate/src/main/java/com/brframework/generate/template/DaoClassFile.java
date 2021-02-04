package com.brframework.generate.template;

import com.brframework.generate.BaseUtil;
import com.brframework.generate.Constant;
import com.brframework.generate.base.JavaFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: ljr
 * @Date: 2019/8/14 17:44
 * @Description:
 */
public class DaoClassFile extends JavaFile {

    public DaoClassFile(String route, String packageDir, String entity, String function, Map<String, String> notes) {
        fileDir = route + "/" + Constant.PACKAGE_DAO + "/" + function;
        fileName = BaseUtil.getClassWithModule(entity, Constant.MODULE_DAO) + Constant.SUFFIX_CLASS_TYPE;
        packageDir = BaseUtil.toPackage(packageDir);
        filePackageName = packageDir + "." + function;
        type = Constant.INTERFACE_TYPE;
        name = BaseUtil.getClassWithModule(entity, Constant.MODULE_DAO );
        List<String> imports = getImports(packageDir, function, entity);
        List<String> classAnnotations = getClassAnnotations();
        List<String> extendss = getExtendss(entity);
        List<String> implementss = getImplementss();
        List<Field> fields = getFields();
        List<Method> methods = getMethods();
        super.init(imports, notes, classAnnotations, extendss, implementss, fields, methods);
    }

    public List<String> getImports(String mainPackage, String function, String entity){
        List<String> imports = new LinkedList<>();
        String commonRepository = "CommonRepository";
        imports.add(BaseUtil.getImportName(mainPackage, function, entity, Constant.PACKAGE_ENTITY).toString());
        imports.add("com.brframework.commondb.core." + commonRepository);
        imports.add("org.springframework.stereotype.Repository");
        return imports;
    }

    public List<String> getClassAnnotations(){
        List<String> classAnnotations = new LinkedList<>();
        classAnnotations.add("Repository");
        return classAnnotations;
    }

    public List<String> getExtendss(String entity){
        List<String> extendss = new LinkedList<>();
        extendss.add("CommonRepository<Long, " + BaseUtil.getClassWithoutSuffix(entity) + ">");
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
