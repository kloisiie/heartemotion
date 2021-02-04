package com.brframework.compose.crud;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.brframework.common.utils.DateTimeUtils;
import com.brframework.compose.Compose;
import com.brframework.constace.ClassType;
import com.brframework.constace.Visible;
import com.brframework.core.template.JavaClassTemplate;
import com.brframework.core.template.JavaFieldTemplate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.querydsl.core.types.Path;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * Crud功能组成
 * @author xu
 * @date 2020/7/30 9:49
 */
public class CrudCompose implements Compose {

    public static final String POJO_DIR_NAME = "pojo";
    public static final String ENTITY_DIR_NAME = "entity";
    public static final String DAO_DIR_NAME = "dao";
    public static final String SERVICE_DIR_NAME = "service";
    public static final String SERVICE_IMPL_DIR_NAME = "impl";
    public static final String CONTROLLER_ADMIN_NAME = "web.admin";
    public static final String JSON_ADMIN_NAME = "json.admin";

    private Builder builder;
    private CrudCompose(Builder builder){
        this.builder = builder;
    }

    public static Builder builder(){
        return new Builder();
    }

    @Override
    public void generate() {

        String dtoPackageName = builder.basePackageName + "." + POJO_DIR_NAME + "." + builder.module;

        //生成PageQueryParamDTO
        String pageQueryParamDTOName = builder.funCode + "PageQueryParamDTO";
        genPojo(dtoPackageName, builder.author, builder.funName + "分页参数",
                pageQueryParamDTOName, builder.pageQueryParamFields, builder.outDir, false);

        //生成InsertParamDTO
        String insertParamDTOName = builder.funCode + "InsertParamDTO";
        genPojo(dtoPackageName, builder.author, builder.funName + "添加参数",
                insertParamDTOName, builder.insertParamFields, builder.outDir, false);

        //生成UpdateParamDTO
        String updateParamDTOName = builder.funCode + "UpdateParamDTO";
        genPojo(dtoPackageName, builder.author, builder.funName + "更新参数",
                updateParamDTOName, builder.updateParamFields, builder.outDir, false);

        //生成Service Interface
        String serviceInterfaceName = builder.funCode + "Service";
        genServiceInterface(builder.basePackageName, builder.module, builder.author,
                builder.funCode, builder.funName, serviceInterfaceName, builder.funEntityIdType,
                builder.funEntityType, insertParamDTOName, pageQueryParamDTOName, updateParamDTOName,
                builder.outDir);

        //生成Service Interface Impl
        String serviceInterfaceImplName = builder.funCode + "ServiceImpl";
        genServiceInterfaceImpl(builder.basePackageName, builder.module, builder.author,
                builder.funCode, builder.funName, serviceInterfaceImplName, builder.funEntityIdType,
                builder.funEntityType, insertParamDTOName, pageQueryParamDTOName, updateParamDTOName,
                builder.pageQueryParamFields, builder.insertParamFields, builder.updateParamFields,
                builder.daoType, serviceInterfaceName, builder.outDir);

        String jsonPackageName = builder.basePackageName + "." + JSON_ADMIN_NAME + "." + builder.module;

        //生成PageQueryParam
        String pageQueryParam = builder.funCode + "PageQueryParam";
        genPojo(jsonPackageName, builder.author, builder.funName + "查询参数",
                pageQueryParam, builder.pageQueryParamFields, builder.outDir, false);

        //生成InsertParam
        String insertParam = builder.funCode + "InsertParam";
        genPojo(jsonPackageName, builder.author, builder.funName + "添加参数",
                insertParam, builder.insertParamFields, builder.outDir, true);

        //生成UpdateParam
        String updateParam = builder.funCode + "UpdateParam";
        genPojo(jsonPackageName, builder.author, builder.funName + "修改参数",
                updateParam, builder.updateParamFields, builder.outDir, true);

        //生成QueryResult
        String queryResult = builder.funCode + "QueryResult";
        genPojo(jsonPackageName, builder.author, builder.funName + "查询结果",
                queryResult, builder.queryResultFields, builder.outDir, false);

        //生成DetailResult
        String detailResult = builder.funCode + "DetailResult";
        genPojo(jsonPackageName, builder.author, builder.funName + "详情结果",
                detailResult, builder.detailResultFields, builder.outDir, false);

        //生成Controller
        String controllerName = "Admin" + builder.funCode + "Controller";
        genControllerImpl(builder.basePackageName, builder.module, builder.author,
                builder.funCode, builder.funName, controllerName, builder.funEntityType, builder.funEntityIdType,
                insertParamDTOName, pageQueryParamDTOName, updateParamDTOName, builder.pageQueryParamFields,
                builder.insertParamFields, builder.updateParamFields, builder.queryResultFields, builder.detailResultFields, serviceInterfaceName,
                queryResult, pageQueryParam, insertParam, updateParam, detailResult, builder.outDir);

    }

    private void genControllerImpl(String basePackageName, String module, String author, String funCode,
                                   String funName, String className, String funEntityType, String funEntityIdType,
                                   String insertParamDTOName, String pageQueryParamDTOName, String updateParamDTOName,
                                   List<Path> queryPaths, List<Path> insertPaths, List<Path> updatePaths,
                                   List<Path> resultPaths, List<Path> detailResultPaths, String serviceType, String queryResultType,
                                   String queryParamType, String insertParamType, String updateParamType, String detailResultType,
                                   String outDir){
        String controllerNameFullClassName = basePackageName + "." + CONTROLLER_ADMIN_NAME + "." + className;

        String queryDtoSetCode = genSetGetCodeByPaths(queryPaths, "dtoParam",
                "queryParam", "    ");
        String insertDtoSetCode = genSetGetCodeByPaths(insertPaths, "dtoParam",
                "insertParam", "    ");
        String updateDtoSetCode = genSetGetCodeByPaths(updatePaths, "dtoParam",
                "updateParam", "    ");
        String resultSetCode = genSetGetCodeByPaths(resultPaths, "result",
                StrUtil.lowerFirst(funCode), "    ");
        String detailResultSetCode = genSetGetCodeByPaths(detailResultPaths, "result",
                StrUtil.lowerFirst(funCode), "    ");

        XmlCrudAdminController.builder()
                .serviceType(serviceType)
                .author(author)
                .basePackageName(basePackageName)
                .funCode(funCode)
                .funEntityIdType(funEntityIdType)
                .funEntityType(funEntityType)
                .module(module)
                .insertParamDTOType(insertParamDTOName)
                .pageQueryParamDTOType(pageQueryParamDTOName)
                .updateParamDTOType(updateParamDTOName)
                .queryDtoSetCode(queryDtoSetCode)
                .insertDtoSetCode(insertDtoSetCode)
                .updateDtoSetCode(updateDtoSetCode)
                .resultSetCode(resultSetCode)
                .detailResultSetCode(detailResultSetCode)
                .queryResultType(queryResultType)
                .queryParamType(queryParamType)
                .insertParamType(insertParamType)
                .updateParamType(updateParamType)
                .detailResultType(detailResultType)
                .funName(funName)
                .build()
                .writeFile(outDir + File.separator +
                        controllerNameFullClassName.replaceAll("\\.",
                                Matcher.quoteReplacement(File.separator)) + ".java");
    }

    private String genSetGetCode(String callSet, String setName, String callGet, String getName){
        return callSet + "." + setName + "(" + callGet + "." + getName + "());";
    }

    private String genSetGetCodeByPaths(List<Path> paths, String callSet, String callGet, String linePrefix){
        StringBuilder insertCode = new StringBuilder();
        for (Path path : paths) {
            String fieldName = path.getMetadata().getName();
            String code = genSetGetCode(callSet, StrUtil.genSetter(fieldName),
                    callGet, StrUtil.genGetter(fieldName));
            insertCode.append(linePrefix).append(code).append("\n");
        }
        return insertCode.toString();
    }

    private void genServiceInterfaceImpl(String basePackageName, String module, String author, String funCode,
                                         String funName, String className, String funEntityIdType,String funEntityType,
                                         String insertParamDTOName, String pageQueryParamDTOName, String updateParamDTOName,
                                         List<Path> queryPaths, List<Path> insertPaths, List<Path> updatePaths, String daoType,
                                         String serviceInterfaceType, String outDir){
        //查询语法生成
        StringBuilder queryCode = new StringBuilder();
        for (Path path : queryPaths) {
            Field field = ReflectionUtils.findField(path.getRoot().getType(), path.getMetadata().getName());
            String ex = "Q" + funCode + "." + StrUtil.lowerFirst(funCode)
                    + "." + field.getName() + ", queryParam.get" + StrUtil.upperFirst(field.getName()) + "()";
            queryCode.append("            .and(ExQuery.eq(").append(ex).append("))\n");
        }

        //插入语法生成
        String insertCode = genSetGetCodeByPaths(insertPaths, StrUtil.lowerFirst(funCode),
                "insertParam", "    ");


        //更新语法生成
        String updateCode = genSetGetCodeByPaths(updatePaths, StrUtil.lowerFirst(funCode),
                "updateParam", "    ");

        String serviceInterfaceImplName = funCode + "ServiceImpl";
        String serviceInterfaceImplNameFullClassName = basePackageName + "." + SERVICE_DIR_NAME +
                "." + builder.module + "." + SERVICE_IMPL_DIR_NAME + "." + serviceInterfaceImplName;
        XmlCrudServiceInterfaceImpl.builder()
                .daoType(daoType)
                .serviceInterfaceType(serviceInterfaceType)
                .author(author)
                .basePackageName(basePackageName)
                .funCode(funCode)
                .funEntityIdType(funEntityIdType)
                .funEntityType(funEntityType)
                .insertParamType(insertParamDTOName)
                .module(module)
                .pageQueryType(pageQueryParamDTOName)
                .updateParamType(updateParamDTOName)
                .queryBody(queryCode.toString())
                .insertBody(insertCode)
                .updateBody(updateCode)
                .funExplain(funName).build()
                .writeFile(outDir + File.separator +
                        serviceInterfaceImplNameFullClassName.replaceAll("\\.",
                                Matcher.quoteReplacement(File.separator)) + ".java");
    }

    private void genServiceInterface(String basePackageName, String module, String author, String funCode,
                                     String funName, String className, String funEntityIdType,String funEntityType,
                                     String insertParamDTOName, String pageQueryParamDTOName, String updateParamDTOName,
                                     String outDir){

        String serviceInterfaceNameFullClassName = basePackageName + "." + SERVICE_DIR_NAME +
                "." + module + "." + className;
        XmlCrudServiceInterface.builder()
                .author(author)
                .basePackageName(basePackageName)
                .funCode(funCode)
                .funEntityIdType(funEntityIdType)
                .funEntityType(funEntityType)
                .insertParamType(insertParamDTOName)
                .module(module)
                .pageQueryType(pageQueryParamDTOName)
                .updateParamType(updateParamDTOName)
                .funExplain(funName).build()
                .writeFile(outDir + File.separator +
                        serviceInterfaceNameFullClassName.replaceAll("\\.",
                                Matcher.quoteReplacement(File.separator)) + ".java");

    }

    private void genPojo(String packageName, String author, String explain,
                         String className, List<Path> paths, String outDir, boolean valid){
        String pageQueryParamDTONameFullClassName = packageName + "." + className;
        HashSet<String> importList = Sets.newHashSet(
                "io.swagger.annotations.ApiModelProperty",
                "io.swagger.annotations.ApiModel",
                "lombok.Data"
        );
        if(valid){
            importList.add("javax.validation.constraints.NotNull");
        }
        importList.addAll(loadFieldImport(paths));
        List<JavaFieldTemplate> fieldTemplates = pathToFieldTemplateByPaths(paths, valid);

        String classContent = JavaClassTemplate.builder()
                .type(ClassType.CLASS)
                .packageName(packageName)
                .importList(Lists.newArrayList(importList))
                .classAuthor(author)
                .classExplain(explain)
                .className(className)
                .classAnnotations(Lists.newArrayList(
                        "@ApiModel",
                        "@Data"
                ))
                .fields(fieldTemplates)
                .build().strings();
        FileUtil.writeUtf8String(classContent, outDir + File.separator +
                pageQueryParamDTONameFullClassName.replaceAll("\\.",
                        Matcher.quoteReplacement(File.separator)) + ".java");
    }

    private HashSet<String> loadFieldImport(List<Path> paths){
        HashSet<String> importList = Sets.newHashSet();
        for (Path path : paths) {
            Field field = ReflectionUtils.findField(path.getRoot().getType(), path.getMetadata().getName());
            for (Annotation annotation : field.getAnnotations()) {
                importList.add(annotation.annotationType().getName());
            }
            importList.add(field.getType().getName().replaceAll("\\$", "."));
        }
        return importList;
    }

    private List<JavaFieldTemplate> pathToFieldTemplateByPaths(List<Path> paths, boolean valid){
        List<JavaFieldTemplate> fieldTemplates = Lists.newArrayList();
        for (Path path : paths) {
            Field field = ReflectionUtils.findField(path.getRoot().getType(), path.getMetadata().getName());
            fieldTemplates.add(pathToFieldTemplate(field, valid));
        }
        return fieldTemplates;
    }

    private JavaFieldTemplate pathToFieldTemplate(Field field, boolean valid){
        ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
        JSONField jsonField = field.getAnnotation(JSONField.class);
        List<String> annotations = Lists.newArrayList();

        if(property != null){
            String propertyAnnotation =
                    "@ApiModelProperty(value = \"" + property.value() + "\", " +
                            "required = "+ property.required()  +", example = \"" + property.example() + "\")";
            annotations.add(propertyAnnotation);

            if(valid){
                String validAnnotation = "@NotNull(message = \"" + property.value() + "不能为空\")";
                annotations.add(validAnnotation);
            }
        }

        if(jsonField != null){
            String jsonFieldAnnotation = "@JSONField(format = \"" + jsonField.format() + "\")";
            annotations.add(jsonFieldAnnotation);
        }

        return JavaFieldTemplate.builder()
                .visible(Visible.PRIVATE)
                .fieldAnnotations(annotations)
                .fieldName(field.getName())
                .fieldType(field.getType().getSimpleName())
                .build();
    }

    public static class Builder {
        private String outDir;
        private String basePackageName;
        private String module;
        private String funName;
        private String funCode;
        private String author;

        private String daoType;
        private String funEntityIdType;
        private String funEntityType ;

        private List<Path> pageQueryParamFields = Lists.newArrayList();
        private List<Path> insertParamFields = Lists.newArrayList();
        private List<Path> updateParamFields = Lists.newArrayList();
        private List<Path> queryResultFields = Lists.newArrayList();
        private List<Path> detailResultFields = Lists.newArrayList();

        public CrudCompose build(){
            if(outDir == null){
                throw new IllegalArgumentException("outDir is not null");
            }
            if(module == null){
                throw new IllegalArgumentException("module is not null");
            }
            if(basePackageName == null){
                throw new IllegalArgumentException("basePackageName is not null");
            }
            if(author == null){
                throw new IllegalArgumentException("author is not null");
            }
            if(funCode == null){
                throw new IllegalArgumentException("funCode is not null");
            }
            if(funName == null){
                throw new IllegalArgumentException("funName is not null");
            }
            if(funEntityIdType == null){
                funEntityIdType = Long.class.getSimpleName();
            }
            if(funEntityType == null){
                funEntityType = funCode;
            }

            if(daoType == null){
                daoType = funCode + "Dao";
            }
            return new CrudCompose(this);
        }

        /**
         * 输出目录
         * @param outDir  目录
         * @return  目录
         */
        public Builder outDir(String outDir){
            this.outDir = outDir;
            return this;
        }

        /**
         * 模块
         * @param module  模块
         * @return  构建
         */
        public Builder module(String module){
            this.module = module;
            return this;
        }

        /**
         * 基础包名
         * @param basePackageName  基础包名
         * @return  构建
         */
        public Builder basePackageName(String basePackageName){
            this.basePackageName = basePackageName;
            return this;
        }

        /**
         * Dao类型
         * @param daoType  Dao类型
         * @return  构建
         */
        public Builder daoType(String daoType){
            this.daoType = daoType;
            return this;
        }
        /**
         * 作者
         * @param author  作者
         * @return  构建
         */
        public Builder author(String author){
            this.author = author;
            return this;
        }

        /**
         * 功能代码（注意首字母大写）
         * @param funCode  功能代码（注意首字母大写）
         * @return  构建
         */
        public Builder funCode(String funCode){
            this.funCode = StrUtil.upperFirst(funCode);
            return this;
        }

        /**
         * 功能名称
         * @param funName  功能名称
         * @return  构建
         */
        public Builder funName(String funName){
            this.funName = funName;
            return this;
        }

        /**
         * ID类型
         * @param funEntityIdType  ID类型
         * @return  构建
         */
        public Builder funEntityIdType(String funEntityIdType){
            this.funEntityIdType = funEntityIdType;
            return this;
        }

        /**
         * 分页参数字段
         * @param pageQueryParamFields  分页参数字段
         * @return  构建
         */
        public Builder pageQueryParamFields(List<Path> pageQueryParamFields){
            this.pageQueryParamFields = pageQueryParamFields;
            return this;
        }

        /**
         * 保存参数字段
         * @param insertParamFields  保存参数字段
         * @return  构建
         */
        public Builder insertParamFields(List<Path> insertParamFields){
            this.insertParamFields = insertParamFields;
            return this;
        }

        /**
         * 更新参数字段
         * @param updateParamFields  更新参数字段
         * @return  构建
         */
        public Builder updateParamFields(List<Path> updateParamFields){
            this.updateParamFields = updateParamFields;
            return this;
        }

        /**
         * 查询结果字段
         * @param queryResultFields  查询结果字段
         * @return  构建
         */
        public Builder queryResultFields(List<Path> queryResultFields){
            this.queryResultFields = queryResultFields;
            return this;
        }

        /**
         * 详情结果字段
         * @param detailResultFields  详情结果字段
         * @return  构建
         */
        public Builder detailResultFields(List<Path> detailResultFields){
            this.detailResultFields = detailResultFields;
            return this;
        }

        /**
         * 实体类型
         * @param funEntityType  实体类型
         * @return  构建
         */
        public Builder funEntityType(String funEntityType){
            this.funEntityType = funEntityType;
            return this;
        }
    }
}
