package com.brframework.generate.template;

import com.brframework.generate.BaseUtil;
import com.brframework.generate.Constant;
import com.brframework.generate.FileDomain;
import com.brframework.generate.base.JavaFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.brframework.generate.BaseUtil.buildMethod;
import static com.brframework.generate.Constant.CLASS_TYPE;
import static com.brframework.generate.Constant.PACKAGE_ADMIN;

/**
 * @Author: ljr
 * @Date: 2019/8/15 16:01
 * @Description: 与api控制器的差别是注入import,注解,方法
 */
public class AdminControllerFile  extends JavaFile {

    public AdminControllerFile(String route, String packageDir, String function, Map<String, String> notes, List<FileDomain> fileDomains,String functionName) {
        fileDir = route + "/" + Constant.PACKAGE_CONTROLLER + "/" + PACKAGE_ADMIN;
        fileName = "Admin" + BaseUtil.getClassWithoutSuffix(function) + Constant.MODULE_CONTROLLER;
        packageDir = BaseUtil.toPackage(packageDir);
        filePackageName = packageDir + "." + function;
        type = Constant.CLASS_TYPE;
        name = "Admin" + BaseUtil.getClassWithoutSuffix(function) + Constant.MODULE_CONTROLLER;
        List<String> imports = getImports(packageDir, function);
        List<String> classAnnotations = getClassAnnotations(function, functionName);
        List<String> extendss = getExtendss();
        List<String> implementss = getImplementss();
        List<Field> fields = getFields(fileDomains);
        List<Method> methods = getMethods(fileDomains, function);
        super.init(imports, notes, classAnnotations, extendss, implementss, fields, methods);
    }


    public List<String> getImports(String mainPackage, String function){
        List<String> imports = new LinkedList<>();
        imports.add(mainPackage + "." + Constant.PACKAGE_ENTITY + "." + function + ".*" );
        imports.add(mainPackage + "." + Constant.PACKAGE_SERVICE + "." + function + ".*" );
        imports.add(mainPackage + "." + Constant.PACKAGE_JSON + "."+ PACKAGE_ADMIN + "." + function + ".*" );
        imports.add("com.brframework.commoncms.annatotion.AdminMenu");
        imports.add("com.brframework.commoncms.annatotion.layout.EditAlertLayout");
        imports.add("com.brframework.commoncms.annatotion.layout.EditLayout");
        imports.add("com.brframework.commoncms.annatotion.layout.PageLayout");
        imports.add("com.brframework.commoncms.annatotion.option.CmsOption");
        imports.add("com.brframework.commonweb.json.JSONResult");
        imports.add("com.brframework.commonweb.json.Page");
        imports.add("com.brframework.commonweb.json.PageParam");
        imports.add("com.brframework.commonwebadmin.aop.annotation.AOLog");
        imports.add("io.swagger.annotations.Api");
        imports.add("io.swagger.annotations.ApiOperation");
        imports.add("org.springframework.beans.factory.annotation.Autowired");
        imports.add("org.springframework.security.access.prepost.PreAuthorize");
        imports.add("org.springframework.web.bind.annotation.*");
        imports.add("javax.validation.Valid");
        imports.add("java.util.Optional");
        imports.add("static com.brframework.commondb.core.ControllerPageHandle.convertTo");
        imports.add("static com.brframework.commondb.core.ControllerPageHandle.jpaPageConvertToPage");
        return imports;
    }


    public List<String> getClassAnnotations(String function, String functionName){
        List<String> classAnnotations = new LinkedList<>();
        classAnnotations.add("RestController");
        classAnnotations.add("Api(tags = \""+ functionName +"\")");
        classAnnotations.add("AdminMenu(menuName = \"" + function +"\")");
        classAnnotations.add(new StringBuilder().append("RequestMapping(\"/").append(PACKAGE_ADMIN + "/access").append("\")").toString());
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

    public List<Field> getFields(List<FileDomain> fileDomains){
        List<Field> fields = new LinkedList<>();
        for (FileDomain fileDomain : fileDomains) {
            String entity = fileDomain.getDoMain();
            String className = entity.substring(0, 1).toUpperCase() + entity.substring(1);
            List<String> fannotations = new LinkedList<>();
            fannotations.add("Autowired");
            String fmodifier = "private";
            String ftype = new StringBuilder().append(className).append(Constant.MODULE_SERVICE).toString();
            String fname = entity + Constant.MODULE_SERVICE;
            String value = null;
            Field field = new Field(fannotations, fmodifier , ftype, fname, value);
            fields.add(field);
        }

        return fields;
    }


    public List<Method> getMethods(List<FileDomain> fileDomains, String function){
        List<Method> methods = new LinkedList<>();
        for (FileDomain fileDomain : fileDomains) {
            String entity = fileDomain.getDoMain();
            String doMainName = fileDomain.getDoMainName();
            String className = entity.substring(0, 1).toUpperCase() + entity.substring(1);
            List<String> mannotations ;
            String mmodifier = "public";
            String resultType = "JSONResult";
            String name;
            String args;
            List<String> exceptions = new LinkedList<>();
            String methodBody;
            //service
            String fname = entity + Constant.MODULE_SERVICE;
            //resultName
            String resultName = className + Constant.JSON_NAME_RESULT;
            //resultName,修改对象
            String paramName = className + Constant.JSON_NAME_CREATE;
            //url前缀,驼峰大小写转下划线小写
            String urlPrefix = function + "-" + entity.replaceAll("[A-Z]", "-$0").toLowerCase() + "-";
            //实体类大小转为url形式的反斜杆
            String entityUrl = entity.replaceAll("[A-Z]", "/$0").toLowerCase();
            //list和get方法是一样的权限
            String queryPermission = urlPrefix + "query";
            //page
            mannotations = new LinkedList<>();
            mannotations.add("ApiOperation(value = \"所有"+ doMainName +"列表\", notes = \"所有"+ doMainName +"列表\", produces = \"text/plain\")");
            mannotations.add("GetMapping(\"/v1/" + entityUrl + "/page" + "\")");
            mannotations.add("AdminMenu(menuName = \"" + doMainName + "管理\")");
            mannotations.add("PreAuthorize(\"hasRole('" + queryPermission + "')\")");
            mannotations.add(BaseUtil.pageLayout(entity));
            name = entity + "ListV1";
            args = "PageParam pageParam, " + className + Constant.JSON_NAME_QUERY + " param";
            methodBody = buildMethod(new String[] {
                    "Page<" + resultName + "> page = jpaPageConvertToPage(" + fname + ".page(pageParam, param), pageParam, " + resultName + ".class);",
                    "return JSONResult.ok(page);"
            });
            Method pageMethod = new Method(mannotations, mmodifier, resultType + "<Page<" + resultName + ">>"
                    , name, args, exceptions, methodBody);
            methods.add(pageMethod);

            //update
            mannotations = new LinkedList<>();
            mannotations.add("ApiOperation(value = \"修改"+ doMainName +"信息\", notes = \"修改"+ doMainName +"信息\", produces = \"text/plain\")");
            mannotations.add("PostMapping(\"/v1/" + entityUrl + "/{id}" + "\")");
            mannotations.add("PreAuthorize(\"hasRole('" + urlPrefix + "edit-v1')\")");
            mannotations.add("AOLog");
            mannotations.add(BaseUtil.editAlertLayout(className));
            name = entity + "EditV1";
            args = "@PathVariable Long id, @RequestBody " + paramName +" param";
            methodBody = buildMethod(new String[] {
                    className + " " + entity + " = convertTo(param, " + className + ".class);",
                    entity + ".setId(id);",
                    fname + ".save(" + entity + ");",
                    "return JSONResult.ok();"
            });
            Method updateMethod = new Method(mannotations, mmodifier, resultType, name, args, exceptions, methodBody);
            methods.add(updateMethod);

            //add
            mannotations = new LinkedList<>();
            mannotations.add("ApiOperation(value = \"添加"+ doMainName +"信息\", notes = \"添加"+ doMainName +"信息\", produces = \"text/plain\")");
            mannotations.add("PutMapping(\"/v1/" + entityUrl + "/add" + "\")");
            mannotations.add("PreAuthorize(\"hasRole('" + urlPrefix + "create-v1')\")");
            mannotations.add("AOLog");
            mannotations.add("EditLayout");
            name = entity + "CreateV1";
            args = "@RequestBody " + paramName +" param";
            methodBody = buildMethod(new String[] {
                    className + " " + entity + " = convertTo(param, " + className + ".class);",
                    fname + ".save(" + entity + ");",
                    "return JSONResult.ok();"
            });
            Method addMethod = new Method(mannotations, mmodifier, resultType, name, args, exceptions, methodBody);
            methods.add(addMethod);

            //delete
            mannotations = new LinkedList<>();
            mannotations.add("ApiOperation(value = \"删除"+ doMainName +"信息\", notes = \"删除"+ doMainName +"信息\", produces = \"text/plain\")");
            mannotations.add("DeleteMapping(\"/v1/" + entityUrl + "/{id}" + "\")");
            mannotations.add("PreAuthorize(\"hasRole('" + urlPrefix + "del-v1')\")");
            mannotations.add("AOLog");
            name = entity + "DelV1";
            args = "@PathVariable Long id";
            methodBody = buildMethod(new String[] {
                    fname + ".removeById(id);",
                    "return JSONResult.ok();"
            });
            Method deleteMethod = new Method(mannotations, mmodifier, resultType, name, args, exceptions, methodBody);
            methods.add(deleteMethod);

            //get
            String optinal = "optinal";
            mannotations = new LinkedList<>();
            mannotations.add("ApiOperation(value = \"获取"+ doMainName +"信息\", notes = \"获取"+ doMainName +"信息\", produces = \"text/plain\")");
            mannotations.add("PreAuthorize(\"hasRole('" + queryPermission + "')\")");
            mannotations.add("GetMapping(\"/v1/" + entityUrl + "/{id}" + "\")");
            name = "get" + className;
            args = "@PathVariable Long id";
            methodBody = buildMethod(new String[] {
                    "Optional<" + className + "> optional = " + fname + ".findById(id);",
                    "return JSONResult.ok(optional.get());"
            });
            Method getMethod = new Method(mannotations, mmodifier, resultType + "<" + className + ">", name, args, exceptions, methodBody);
            methods.add(getMethod);
        }
        return methods;
    }
}
