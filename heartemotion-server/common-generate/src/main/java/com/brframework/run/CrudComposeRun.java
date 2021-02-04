package com.brframework.run;

import cn.bluetech.gragas.entity.client.QOptionLog;
import cn.bluetech.gragas.entity.hr.*;
import cn.bluetech.gragas.entity.user.QStudent;
import com.brframework.compose.crud.CrudCompose;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;

/**
 * @author xu
 * @date 2020/7/30 13:36
 */
public class CrudComposeRun {
    private static final String outDir = "boot-gragas\\src\\main\\java";
    private static final String author = "xu";
    private static final String basePackageName = "cn.bluetech.gragas";

    public static void main(String[] args) throws IOException {
        QPlatformDebugFile q = QPlatformDebugFile.platformDebugFile;
        CrudCompose.builder()
                .outDir(new File(outDir).getCanonicalPath())
                .author(author)
                .basePackageName(basePackageName)
                .funCode("PlatformDebugFile")
                .funName("平台调试文件")
                .module("hr")
                //分页查询参数
                .pageQueryParamFields(Lists.newArrayList(q.fileName))
                //添加参数
                .insertParamFields(Lists.newArrayList(q.fileName,
                        q.details,
                        q.content))
                //修改参数
                .updateParamFields(Lists.newArrayList())
                //查询结果
                .queryResultFields(Lists.newArrayList(q.id, q.createDate,
                        q.fileName,
                        q.details, q.startTime, q.endTime))
                //详情结果
                .detailResultFields(Lists.newArrayList(q.id, q.createDate,
                        q.fileName, q.content,
                        q.details, q.startTime, q.endTime))
                .build()
                .generate();
    }

}
