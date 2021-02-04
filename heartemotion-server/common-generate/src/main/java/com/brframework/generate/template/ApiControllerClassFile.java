package com.brframework.generate.template;

import com.brframework.generate.BaseUtil;
import com.brframework.generate.Constant;
import com.brframework.generate.base.JavaFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.brframework.generate.BaseUtil.buildMethod;
import static com.brframework.generate.Constant.PACKAGE_API;

/**
 * @Author: ljr
 * @Date: 2019/8/15 14:16
 * @Description:
 */
public class ApiControllerClassFile extends JavaFile {

    public ApiControllerClassFile(String route, String packageDir, String entity, String function, Map<String, String> notes) {
        fileDir = route + "/" + Constant.PACKAGE_CONTROLLER + "/" + PACKAGE_API;
        packageDir = BaseUtil.toPackage(packageDir);
        String className = "Api" + BaseUtil.getClassWithoutSuffix(function) + Constant.MODULE_CONTROLLER;
        fileName = className + Constant.SUFFIX_CLASS_TYPE;
        filePackageName = Constant.PACKAGE_CONTROLLER + "." + PACKAGE_API;
        type = Constant.CLASS_TYPE;
        name = className;
        List<String> imports = getImports(packageDir, function);
        List<String> classAnnotations = getClassAnnotations(function);
        List<String> extendss = getExtendss();
        List<String> implementss = getImplementss();
        List<Field> fields = getFields(entity);
        List<Method> methods = getMethods(function);
        super.init(imports, notes, classAnnotations, extendss, implementss, fields, methods);
    }

    public List<String> getImports(String mainPackage, String function){
        List<String> imports = new LinkedList<>();
        imports.add(mainPackage + "." + Constant.PACKAGE_ENTITY + "." + function + ".*" );
        imports.add(mainPackage + "." + Constant.PACKAGE_SERVICE + "." + function + ".*" );
        imports.add("com.brframework.commonweb.json.JSONResult");
        imports.add("io.swagger.annotations.Api");
        imports.add("io.swagger.annotations.ApiOperation");
        imports.add("org.springframework.beans.factory.annotation.Autowired");
        imports.add("org.springframework.web.bind.annotation.*");
        return imports;
    }

    public List<String> getClassAnnotations(String function){
        List<String> classAnnotations = new LinkedList<>();
        classAnnotations.add("RestController");
        classAnnotations.add("Api(tags = \""+ function +"\")");
        classAnnotations.add(new StringBuilder().append("RequestMapping(\"/").append("api").append("\")").toString());
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

    public List<Field> getFields(String entity){
        List<Field> fields = new LinkedList<>();
        List<String> fannotations = new LinkedList<>();
        String className = entity.substring(0, 1).toUpperCase() + entity.substring(1);
        fannotations.add("Autowired");
        String fmodifier = "private";
        String ftype = new StringBuilder().append(className).append(Constant.MODULE_SERVICE).toString();
        String fname = entity + Constant.MODULE_SERVICE;
        String value = null;
        Field field = new Field(fannotations, fmodifier , ftype, fname, value);
        fields.add(field);
        return fields;
    }

    public List<Method> getMethods( String function){
        List<Method> methods = new LinkedList<>();
        List<String> mannotations ;
        String mmodifier = "public";
        String resultType = "JSONResult";
        String name;
        String args;
        List<String> exceptions = new LinkedList<>();
        String methodBody;

        //get
        mannotations = new LinkedList<>();
        mannotations.add("ApiOperation(value = \"获取"+ function +"V1\", notes = \"获取"+ function +"V1\", produces = \"text/plain\")");
        mannotations.add("GetMapping(\"/v1/" + function + "/list" + "\")");
        name = "list";
        args = "";
        methodBody = buildMethod(new String[] {
                "return JSONResult.ok();"
        });
        Method method = new Method(mannotations, mmodifier, resultType, name, args, exceptions, methodBody);
        methods.add(method);
        return methods;
    }
}
