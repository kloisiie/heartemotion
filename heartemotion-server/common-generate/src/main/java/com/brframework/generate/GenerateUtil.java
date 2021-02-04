package com.brframework.generate;

import com.brframework.generate.base.JavaFile;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: ljr
 * @Date: 2019/8/13 16:54
 * @Description: 用于生成文件所需的每个部分
 */
public class GenerateUtil {

    /**
     * 逐级创建目录
     *
     * @param fileDir
     * @return
     */
    public static File makeResourceDir(String fileDir) {
        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 逐级创建目录,并在目录创建文件
     *
     * @param fileDir
     * @param fileName
     * @return
     * @throws Exception
     */
    public static File makeResourceFile(String fileDir, String fileName) throws Exception {
        File dir = makeResourceDir(fileDir);
        File file = new File(dir, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * @param fileDir  创建文件目录
     * @param fileName 文件名
     * @param suffix   后缀,如果不是以此后缀结尾,则加上此后缀 eg .java
     * @return
     * @throws Exception
     */
    public static File makeFileWithSuffix(String fileDir, String fileName, String suffix) throws Exception {
        File dir = makeResourceDir(fileDir);
        if (!fileName.endsWith(suffix)) {
            fileName += suffix;
        }
        File file = new File(dir, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 构建包名字符串
     *
     * @param filePackageName 包名 com/ns/gailun
     * @return
     */
    public static String buildPackage(String filePackageName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("package ").append(filePackageName).append(";\n\n");
        return stringBuilder.toString();
    }

    /**
     * 构建导入信息字符串
     *
     * @param imports
     * @return
     */
    public static String buildImports(Collection<String> imports) {
        StringBuilder stringBuilder = new StringBuilder();
        if (imports != null) {
            Iterator<String> iterator = imports.iterator();
            while (iterator.hasNext()) {
                stringBuilder.append("import ");
                stringBuilder.append(iterator.next());
                stringBuilder.append(";\n");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * 构建注释信息字符串
     *
     * @param notes eg Author -> ljr ;Date -> now
     * @return
     */
    public static String buildNotes(Map<String, String> notes) {
        StringBuilder stringBuilder = new StringBuilder();
        if (notes != null) {
            stringBuilder.append("/**\n");
            for (String key : notes.keySet()) {
                stringBuilder.append(" * @" + key + " " + notes.get(key) + "\n");
            }
            stringBuilder.append(" */\n\n");
        }
        return stringBuilder.toString();
    }

    /**
     * 构建注解类或方法信息字符串
     *
     * @param classAnnotations
     * @param needTab          true -> 需要制表符tab,属性或者方法有注解时用到;false -> 不需要,类上用到
     * @return
     */
    public static String buildAnnotations(Collection<String> classAnnotations, boolean needTab) {
        StringBuilder stringBuilder = new StringBuilder();
        if (classAnnotations != null) {
            Iterator<String> iterator = classAnnotations.iterator();
            while (iterator.hasNext()) {
                if (needTab) {
                    stringBuilder.append("\t");
                }
                stringBuilder.append("@");
                stringBuilder.append(iterator.next());
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 构建类名称信息字符串,暂不考虑静态信息
     *
     * @param classType 类类型 class / interface
     * @param className 类名称
     * @return
     */
    public static String buildClass(String classType, String className) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("public ");
        stringBuilder.append(classType);
        stringBuilder.append(" ");
        stringBuilder.append(className);
        stringBuilder.append(" ");
        return stringBuilder.toString();
    }

    /**
     * 建类继承信息字符串
     *
     * @param extendss
     * @return
     */
    public static String buildExtends(Collection<String> extendss) {
        StringBuilder stringBuilder = new StringBuilder();
        extendHandle(extendss, stringBuilder, Constant.KEYWORD_EXTENDS);
        return stringBuilder.toString();
    }

    /**
     * 建类实现信息字符串
     *
     * @param implementss
     * @return
     */
    public static String buildImplements(Collection<String> implementss) {
        StringBuilder stringBuilder = new StringBuilder();
        extendHandle(implementss, stringBuilder, Constant.KEYWORD_IMPLEMENTS);
        stringBuilder.append("{\n");
        return stringBuilder.toString();
    }

    /**
     * 建类继承/实现/异常信息字符串
     *
     * @param parents
     * @param stringBuilder
     * @param keyWord
     */
    public static void extendHandle(Collection<String> parents, StringBuilder stringBuilder, String keyWord) {
        Iterator<String> iterator = parents.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            if (count == 0) {
                stringBuilder.append(keyWord + " ");
            } else {
                stringBuilder.append(",");
            }
            stringBuilder.append(iterator.next());
            count++;
        }
        if (count > 0) {
            stringBuilder.append(" ");
        }
    }

    /**
     * 建类类成员属性信息字符串
     *
     * @param fields
     * @return
     */
    public static String buildFields(Collection<JavaFile.Field> fields) {
        StringBuilder stringBuilder = new StringBuilder();
        if (fields != null) {
            Iterator<JavaFile.Field> iterator = fields.iterator();
            while (iterator.hasNext()) {
                stringBuilder.append("\n");
                JavaFile.Field field = iterator.next();
                stringBuilder.append(buildAnnotations(field.annotations, true));
                modifierHandle(field.modifier, stringBuilder);
                if (field.type != null) {
                    stringBuilder.append(field.type).append(" ");
                }
                if (field.name != null) {
                    stringBuilder.append(field.name);
                }
                if (field.value != null) {
                    stringBuilder.append(" = ").append(field.value);
                }
                stringBuilder.append(";");
            }
        }
        stringBuilder.append("\n\n");
        return stringBuilder.toString();
    }

    /**
     * 构建类的方法信息
     * @param methods
     * @param classType
     * @return
     */
    public static String buildMethods(Collection<JavaFile.Method> methods, String classType) {
        StringBuilder stringBuilder = new StringBuilder();
        if (methods != null) {
            Iterator<JavaFile.Method> iterator = methods.iterator();
            while (iterator.hasNext()) {
                JavaFile.Method method = iterator.next();
                stringBuilder.append(buildAnnotations(method.annotations, true));
                modifierHandle(method.modifier, stringBuilder);
                if (method.resultType != null) {
                    stringBuilder.append(method.resultType).append(" ");
                }
                if (method.name != null) {
                    stringBuilder.append(method.name);
                }
                stringBuilder.append("(");
                if (method.args != null) {
                    stringBuilder.append(method.args);
                }
                stringBuilder.append(") ");
                extendHandle(method.exceptions, stringBuilder, Constant.KEYWORD_THROWS);
                //有方法体则以花括号结束,没有的例如接口就以冒号结束
                if (Constant.CLASS_TYPE.equals(classType)) {
                    stringBuilder.append("{\n");
                    if (method.methodBody != null) {
                        stringBuilder.append(method.methodBody);
                    }
                    stringBuilder.append("\t}\n");
                } else {
                    stringBuilder.append(";\n");
                }
                stringBuilder.append("\n");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 处理限定词
     * @param modifier
     * @param stringBuilder
     */
    public static void modifierHandle(String modifier, StringBuilder stringBuilder) {
        if (modifier != null) {
            stringBuilder.append("\t");
            stringBuilder.append(modifier);
            stringBuilder.append(" ");
        }
    }

    /**
     * 构建类结束信息
     */
    public static String buildClassEnd() {
        return "}";
    }

    /**
     * 将输出流输出
     * @param writer
     * @param stringBuilder
     * @throws Exception
     */
    public static void write(FileWriter writer, StringBuilder stringBuilder) throws Exception {
        writer.write(stringBuilder.toString());
        //endWrite
        writer.flush();
        writer.close();
    }

    public static void generate(JavaFile fileInfo) throws Exception{
        String fileDir = fileInfo.fileDir;
        String fileName = fileInfo.fileName;
        String filePackageName = fileInfo.filePackageName;
        Collection<String> imports = fileInfo.imports;
        Map<String, String> notes = fileInfo.notes;
        Collection<String> classAnnotations = fileInfo.classAnnotations;
        String type = fileInfo.type;
        String name = fileInfo.name;
        Collection<String> extendss = fileInfo.extendss;
        Collection<String> implementss = fileInfo.implementss;
        Collection<JavaFile.Field> fields = fileInfo.fields;
        Collection<JavaFile.Method> methods = fileInfo.methods;
        File file = GenerateUtil.makeFileWithSuffix(fileDir, fileName, Constant.SUFFIX_CLASS_TYPE);
        FileWriter writer = new FileWriter(file);
        //initString
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(GenerateUtil.buildPackage(filePackageName));
        stringBuilder.append(GenerateUtil.buildImports(imports));
        stringBuilder.append(GenerateUtil.buildNotes(notes));
        stringBuilder.append(GenerateUtil.buildAnnotations(classAnnotations, false));
        stringBuilder.append(GenerateUtil.buildClass(type, name));
        stringBuilder.append(GenerateUtil.buildExtends(extendss));
        stringBuilder.append(GenerateUtil.buildImplements(implementss));
        stringBuilder.append(GenerateUtil.buildFields(fields));
        stringBuilder.append(GenerateUtil.buildMethods(methods, type));
        stringBuilder.append(GenerateUtil.buildClassEnd());
        GenerateUtil.write(writer, stringBuilder);
    }

}
