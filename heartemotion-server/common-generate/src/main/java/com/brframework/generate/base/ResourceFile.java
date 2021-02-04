package com.brframework.generate.base;

import com.brframework.generate.GenerateUtil;

import java.io.File;
import java.io.FileWriter;

/**
 * @Author: ljr
 * @Date: 2019/8/13 16:51
 * @Description:
 */
public class ResourceFile {

    protected String fileDir;
    protected String fileName;
    protected String resource;

    public ResourceFile(String fileDir, String fileName) {
        super();
        this.fileDir = fileDir;
        this.fileName = fileName;
    }

    protected void init(String resource) {
        this.resource = resource;
    }

    public void generate() throws Exception{
        //startWrite
        File file = GenerateUtil.makeResourceFile(fileDir, fileName);
        FileWriter fileWriter = new FileWriter(file);
        //initString
        StringBuilder stringBuilder = new StringBuilder();
        //addResource
        if(resource != null) {
            stringBuilder.append(resource);
        }
        //writeAll
        fileWriter.write(stringBuilder.toString());
        //endWrite
        fileWriter.flush();
        fileWriter.close();
    }

}
