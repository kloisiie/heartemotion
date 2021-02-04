package com.brframework.generate;

import com.brframework.generate.base.JavaFile;

import java.util.List;

/**
 * @Author: ljr
 * @Date: 2019/8/14 16:05
 * @Description: 用于处理某些基础方法
 */
public class BaseUtil {

    public static String getClassWithModule(String entity, String module) {

        return getClassWithoutSuffix(entity) + module;
    }

    public static String getClassWithoutSuffix(String entity) {
        StringBuilder modelClass = new StringBuilder();
        modelClass.append((entity.charAt(0) + "").toUpperCase());
        modelClass.append(entity.substring(1));
        return modelClass.toString();
    }

    public static String buildMethod(String[] lines) {
        StringBuilder method = new StringBuilder();
        if (lines != null) {
            for (String line : lines) {
                method.append("\t\t").append(line).append("\n");
            }
        }
        return method.toString();
    }

    /**
     * 获取注入的实体对象的地址
     * @param filePackageName
     * @param function 模块名 eg group
     * @param entity
     * @param module 类型模块 eg entity
     * @return
     */
    public static StringBuilder getImportName(String filePackageName, String function, String entity, String module) {
        StringBuilder importName = new StringBuilder();
        importName.append(filePackageName);
        importName.append("." + module + ".");
        if (!function.isEmpty()) {
            importName.append(function + ".");
        }
        importName.append(getClassWithoutSuffix(entity));
        return importName;
    }

    /**
     *
     * @return pageLayout的字符串
     */
    public static String pageLayout(String entity){
        StringBuilder builder = new StringBuilder();
        builder.append("PageLayout(\n");
        builder.append("\t\toptions = {\n");
        builder.append("\t\t\t @CmsOption(name = \"添加\", uriMappingMethod = \"" + entity + "CreateV1\")\n");
        builder.append("\t\t},\n");
        builder.append("\t\tcolumnOptions = {\n");
        builder.append("\t\t\t @CmsOption(name = \"修改\", uriMappingMethod = \"" + entity + "EditV1\"),\n");
        builder.append("\t\t\t @CmsOption(name = \"删除\", uriMappingMethod = \"" + entity + "DelV1\", alert = \"确定删除该记录吗?\")\n");
        builder.append("\t\t}\n");
        builder.append("\t)");
        return builder.toString();
    }

    /**
     *
     * @return editAlertLayout的字符串
     */
    public static String editAlertLayout(String className){
        StringBuilder builder = new StringBuilder();
        builder.append("EditAlertLayout(\n");
        builder.append("\t\tpaddingMappingMethod = \"get"+ className + "\"\n");
        builder.append("\t)");
        return builder.toString();
    }

    /**
     * 生成属性对象
     * @param fannotations
     * @param fmodifier
     * @param ftype
     * @param fname
     * @param value
     * @return
     */
    public static JavaFile.Field generateField(List<String> fannotations, String fmodifier, String ftype, String fname, String value) {
        return new JavaFile.Field(fannotations, fmodifier, ftype, fname, value);
    }

    /**
     * 将包名由斜杆转为点
     * @param packageDir
     * @return
     */
    public static String toPackage(String packageDir) {
        return packageDir.replaceAll("/", ".");
    }
}
