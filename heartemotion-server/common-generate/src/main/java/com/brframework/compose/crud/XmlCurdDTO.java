package com.brframework.compose.crud;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.brframework.common.utils.DateTimeUtils;
import com.brframework.core.xml.Xml;
import com.brframework.core.xml.dom.DomClass;
import com.brframework.utils.TemplateUtils;
import com.brframework.utils.XmlUtils;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author xu
 * @date 2020/7/30 9:58
 */
public class XmlCurdDTO implements Xml {
    public static String XML_PATH = "/crud/CurdDTO.xml";
    private Builder builder;

    private XmlCurdDTO(Builder builder){
        this.builder = builder;
    }

    public static Builder builder(){
        return new Builder();
    }

    @Override
    public void writeFile(String filePath) {
        String xmlContent = TemplateUtils.readResource(XML_PATH);
        Map<String, String> attributes = TemplateUtils.objectToMap(builder);
        //将属性设置进XML
        String ripeXmlContent = TemplateUtils.setTemplateAttribute(xmlContent, attributes);
        //通过XML生成模板实例
        DomClass domClass = XmlUtils.handleClass(XmlUtils.document(ripeXmlContent).getRootElement());
        String javaClassContent = domClass.toTemplate().strings();
        //写入文件
        FileUtil.writeUtf8String(javaClassContent, filePath);
    }
    public static class Builder {
        private String basePackageName = "";
        private String module = "";
        private String author = "";
        private String createDate = DateTimeUtils.dateTimeToString(LocalDateTime.now());
        private String dtoName = "";
        private String dtoExplain = "";


        public XmlCurdDTO build(){
            if(module == null){
                throw new IllegalArgumentException("module is not null");
            }
            if(basePackageName == null){
                throw new IllegalArgumentException("basePackageName is not null");
            }
            if(author == null){
                throw new IllegalArgumentException("author is not null");
            }
            if(dtoName == null){
                throw new IllegalArgumentException("dtoName is not null");
            }
            if(dtoExplain == null){
                throw new IllegalArgumentException("dtoExplain is not null");
            }
            return new XmlCurdDTO(this);
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
         * 作者
         * @param author  作者
         * @return  构建
         */
        public Builder author(String author){
            this.author = author;
            return this;
        }

        /**
         * 功能名称注释
         * @param dtoExplain  功能名称注释
         * @return  构建
         */
        public Builder dtoExplain(String dtoExplain){
            this.dtoExplain = dtoExplain;
            return this;
        }

        /**
         * 创建时间
         * @param createDate  创建时间
         * @return  构建
         */
        public Builder createDate(String createDate){
            this.createDate = createDate;
            return this;
        }

        /**
         * 功能代码（注意首字母大写）
         * @param dtoName  功能代码（注意首字母大写）
         * @return  构建
         */
        public Builder dtoName(String dtoName){
            this.dtoName =StrUtil.upperFirst(dtoName);
            return this;
        }

    }
}
