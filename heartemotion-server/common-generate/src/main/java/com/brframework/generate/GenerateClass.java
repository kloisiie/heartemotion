package com.brframework.generate;

import com.brframework.generate.template.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.brframework.generate.Constant.PACKAGE_ADMIN;
import static com.brframework.generate.Constant.PACKAGE_API;

/**
 * @author xu
 * @date 2019/11/18 16:46
 */
public class GenerateClass {

    public static void main(String[] args) throws Exception{
/**
 * baseDir -> 基础路径
 * projectName -> 代码所属的项目名,例如 yigou_module
 * resourcesDir -> 资源目录,由于暂时需要生成xml,yml之类的文件,所以不需要
 * javaDir -> java类目录
 * packageDir -> 包名,在java类目录之下
 */
        String baseDir = "common-generate/src/build";
        String projectName = "";
        String javaDir = "src/main/java";
        String packageDir = "com/softwarebr/com.softwarebr.blitzcrank";
        String route = baseDir + "/" + projectName + "/" + javaDir + "/" + packageDir;

        Map<String, String> noteMap = new HashMap<>();
        //文件注释
        noteMap.put("Author", "xu");
        noteMap.put("Description", "平台服务费");
        noteMap.put("Date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

        /**
         * 实体map -> key:指定实体名称; value:功能模块
         * key是类名,小写即可,不要大写,不然serviceImpl注入的dao会变成大写
         */

        Map<String, List<FileDomain>> modelMap = new HashMap<>();
        List<FileDomain> infos = new ArrayList<>();

        FileDomain space = new FileDomain();
        //实体类名不需要首字母大写,也不需要在这里指定模块名
        space.setDoMain("platformServiceFee");
        space.setDoMainName("平台服务费");
        space.setContent("//id\n" +
                "id:Long\n" +
                "//创建时间\n" +
                "createDate:DateTime\n" +
                "//用户\n" +
                "memberId:Long\n" +
                "//服务费\n" +
                "fee:Long\n" +
                "//类型(1.合约购买服务费)\n" +
                "type:Integer\n" +
                "//备注\n" +
                "remark:String");
        infos.add(space);
        //模块化,key是模块名和简称的组合,value是要生成的表属性的集合
        //user是生成的模块命,文件按模块来存放;u是数据库表名的简称;value是文件功能说明
        modelMap.put("user,u,平台服务费", infos);

        execute(modelMap, noteMap, route, packageDir);
    }

    private static void execute(Map<String, List<FileDomain>> modelMap, Map<String, String> noteMap, String route, String packageDir) throws Exception{
        for (String model : modelMap.keySet()) {
            String[] moduleInfo = model.split(",");
            //模块
            String function = moduleInfo[0];
            //缩写
            String abbreviation = moduleInfo[1];
            //模块名称
            String functionName = moduleInfo[2];
            List<FileDomain> fileDomains = modelMap.get(model);
            for (FileDomain fileDomain : fileDomains) {
                model = fileDomain.getDoMain();
                String content = fileDomain.getContent();
                //entity
                EntityClassFile entityClassFile =
                        new EntityClassFile(route, packageDir
                                , model, function,noteMap, abbreviation, content);
                GenerateUtil.generate(entityClassFile);

                //jsonParam
                JsonClassFile jsonParamFile =
                        new JsonClassFile(route, packageDir, model, function, Constant.JSON_NAME_CREATE, noteMap);
                GenerateUtil.generate(jsonParamFile);

                //jsonQuery
                JsonClassFile jsonQueryFile =
                        new JsonClassFile(route, packageDir, model, function, Constant.JSON_NAME_QUERY, noteMap);
                GenerateUtil.generate(jsonQueryFile);

                //jsonResult
                JsonClassFile jsonResultFile =
                        new JsonClassFile(route,packageDir, model, function, Constant.JSON_NAME_RESULT, noteMap);
                GenerateUtil.generate(jsonResultFile);

                //dao
                DaoClassFile daoClassFile =
                        new DaoClassFile(route, packageDir, model, function, noteMap);
                GenerateUtil.generate(daoClassFile);

                //service interface
                ServiceInterfaceFile serviceInterfaceFile =
                        new ServiceInterfaceFile(route, packageDir, model, function, noteMap);
                GenerateUtil.generate(serviceInterfaceFile);

                //serviceimpl
                ServiceImplFile serviceImplFile =
                        new ServiceImplFile(route, packageDir, model, function, noteMap);
                GenerateUtil.generate(serviceImplFile);

                //apicontroller
                ApiControllerClassFile apiControllerClassFile =
                        new ApiControllerClassFile(route, packageDir, model, function, noteMap);
                GenerateUtil.generate(apiControllerClassFile);
            }
            //admincontroller
            AdminControllerFile adminControllerFile =
                    new AdminControllerFile(route , packageDir, function, noteMap, fileDomains, functionName);
            GenerateUtil.generate(adminControllerFile);

        }
    }

}
