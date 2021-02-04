package com.brframework.generate.template;

import com.brframework.generate.BaseUtil;
import com.brframework.generate.Constant;
import com.brframework.generate.base.JavaFile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.brframework.generate.BaseUtil.generateField;

/**
 * @Author: ljr
 * @Date: 2019/8/14 15:59
 * @Description:
 */
public class EntityClassFile extends JavaFile {

    public EntityClassFile(String route, String packageDir, String entity, String function, Map<String, String> notes, String abbreviation, String content) {

        fileDir = route + "/" + Constant.PACKAGE_ENTITY + "/" + function;
        packageDir = BaseUtil.toPackage(packageDir);
        String className = BaseUtil.getClassWithoutSuffix(entity);
        fileName = className + Constant.SUFFIX_CLASS_TYPE;
        filePackageName = packageDir + "." + function;
        type = Constant.CLASS_TYPE;
        name = className;
        List<String> imports = getImports();
        List<String> classAnnotations = getClassAnnotations(entity, abbreviation);
        List<String> extendss = getExtendss();
        List<String> implementss = getImplementss();
        List<Field> fields = getFields(content);
        List<Method> methods = getMethods();
        super.init(imports, notes, classAnnotations, extendss, implementss, fields, methods);
    }

    public List<String> getImports(){
        List<String> imports = new LinkedList<>();
        imports.add("com.alibaba.fastjson.annotation.JSONField");
        imports.add("io.swagger.annotations.ApiModelProperty");
        imports.add("lombok.AllArgsConstructor");
        imports.add("lombok.Builder");
        imports.add("lombok.Data");
        imports.add("lombok.NoArgsConstructor");
        imports.add("javax.persistence.*");
        imports.add("java.time.LocalDateTime");
        return imports;
    }

    public List<String> getClassAnnotations(String entity, String abbreviation){
        List<String> classAnnotations = new LinkedList<>();
        classAnnotations.add("Entity");
        classAnnotations.add("Data");
        classAnnotations.add("NoArgsConstructor");
        classAnnotations.add("AllArgsConstructor");
        classAnnotations.add("Table(name=\"" + abbreviation + "_" + entity.replaceAll("[A-Z]", "_$0").toLowerCase() + "\")");
        classAnnotations.add("Builder");

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

    public List<Field> getFields(String content){
        List<Field> fields = new LinkedList<>();
        //设置id的注解
        List<String> idFieldAnnotations = new ArrayList<>();
        idFieldAnnotations.add("Id");
        idFieldAnnotations.add("GeneratedValue(strategy = GenerationType.IDENTITY)");
        idFieldAnnotations.add("ApiModelProperty(value = \"id\", required = true, example = \"1\")");
        //设置createDate的注解
        List<String> createDateFieldAnnotations = new ArrayList<>();
        createDateFieldAnnotations.add("JSONField(format = \"yyyy-MM-dd HH:mm:ss\")");
        createDateFieldAnnotations.add("ApiModelProperty(value = \"创建时间\", required = true, example = \"2018-03-12 21:32:33\")");

        String fmodifier = "private";
        fields.add(BaseUtil.generateField(idFieldAnnotations, fmodifier , "Long", "id", null));
        fields.add(BaseUtil.generateField(createDateFieldAnnotations, fmodifier , "LocalDateTime", "createDate", null));
        fields.addAll(contentHandle(content));
        return fields;
    }

    private List<Field> contentHandle(String content){
        List<Field> fields = new LinkedList<>();
        String[] split = content.split("\n");
        List<String> descriptions = new ArrayList<>();
        List<String> fieldTypes = new ArrayList<>();
        for (String info : split) {
            if (info.contains("/")) {
                descriptions.add(info.substring(info.lastIndexOf("/") + 1));
            }else if (info.contains(":")){
                fieldTypes.add(info);
            }
            if (info.contains(":")) {

            }
        }
        for (int i = 0; i < fieldTypes.size(); i++) {
            String fieldType = fieldTypes.get(i);
            String[] fieldInfo = fieldType.split(":");
            List<String> fieldAnnotations = new ArrayList<>();
            String type = fieldInfo[1].trim();
            String fieldName = fieldInfo[0].trim();
            if (fieldName.equals("id") || fieldName.equals("createDate")) {
                continue;
            }
            fieldAnnotations.add("ApiModelProperty(value = \"" + descriptions.get(i) + "\", required = true, example = \"\")");
            fields.add(BaseUtil.generateField(fieldAnnotations, "private" , type, fieldName, null));
        }

        return fields;
    }

    public List<Method> getMethods(){
        List<Method> methods = new LinkedList<>();
        //methods,lombok不需要方法
        return methods;
    }


}
