package com.brframework.generate;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.brframework.generate.template.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Handler;
import java.util.stream.Collectors;

/**
 * @author xu
 * @date 2019/11/18 16:46
 */
public class SuperGenerateClass {

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
        String packageDir = "cn/bluetech/gragas";
        String route = baseDir + "/" + projectName + "/" + javaDir + "/" + packageDir;
        String posFilePath = "C:\\Users\\Administrator\\Downloads\\数据库设计图.pos";
        String author = "xu";

        JSONObject jsonObject = JSON.parseObject(FileUtil.readString(posFilePath, "UTF-8"));
        JSONObject elements = jsonObject.getJSONObject("diagram").getJSONObject("elements").getJSONObject("elements");
        elements.keySet().stream().map(elements::getJSONObject).forEach(e -> {
            JSONArray textBlock = e.getJSONArray("textBlock");
            String tableInfo = textBlock.getJSONObject(0).getString("text");

            if(tableInfo.contains("#")){
                System.out.println("表头信息【" + tableInfo + "】被忽略");
                return;
            }

            if(!tableInfo.contains("(") && !tableInfo.contains(")")){
                throw new RuntimeException("表头信息【" + tableInfo + "】不符合规范");
            }



            String tableExplain = tableInfo.substring(0, tableInfo.indexOf("(")).trim();
            String tableName = tableInfo.substring(tableInfo.indexOf("(") + 1, tableInfo.indexOf(")"));
            String tablePrefix = tableName.substring(0, tableName.indexOf("_"));
            String entityName = StrUtil.lowerFirst( StringUtils.join(Arrays.stream(tableName
                    .replace(tablePrefix, "").split("_"))
                    .map(StrUtil::upperFirst).toArray()));

            String tableDetails = textBlock.getJSONObject(1).getString("text")
                    .replace("</div><div>", "\n")
                    .replace("<div>", "").replace("</div>", "")
                    .replace("<br>", "\n");
            System.out.println(tableExplain + "——" + tableName);
            System.out.println(textBlock.getJSONObject(1).getString("text"));
            System.out.println(tableDetails);


            Map<String, String> noteMap = new HashMap<>();
            //文件注释
            noteMap.put("Author", author);
            noteMap.put("Description", tableExplain);
            noteMap.put("Date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

            /**
             * 实体map -> key:指定实体名称; value:功能模块
             * key是类名,小写即可,不要大写,不然serviceImpl注入的dao会变成大写
             */

            Map<String, List<FileDomain>> modelMap = new HashMap<>();
            List<FileDomain> infos = new ArrayList<>();

            FileDomain space = new FileDomain();
            //实体类名不需要首字母大写,也不需要在这里指定模块名
            space.setDoMain(entityName);
            space.setDoMainName(tableExplain);
            space.setContent(tableDetails);
            infos.add(space);
            //模块化,key是模块名和简称的组合,value是要生成的表属性的集合
            //user是生成的模块命,文件按模块来存放;u是数据库表名的简称;value是文件功能说明
            modelMap.put( tablePrefix + "," + tablePrefix + "," + tableExplain, infos);

            try {
                execute(modelMap, noteMap, route, packageDir);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
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

                //dao
                DaoClassFile daoClassFile =
                        new DaoClassFile(route, packageDir, model, function, noteMap);
                GenerateUtil.generate(daoClassFile);

            }

        }
    }


}
