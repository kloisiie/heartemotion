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
 * @date 2020/7/29 17:20
 */
public class XmlCrudServiceInterface implements Xml {
    public static String XML_PATH = "/crud/CrudServiceInterface.xml";
    private Builder builder;

    private XmlCrudServiceInterface(Builder builder){
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
        private String funCode = "";
        private String lowerFirstFunCode = "";
        private String funEntityIdType = "";
        private String funEntityType = "";
        private String funExplain = "";
        private String pageQueryType = "";
        private String insertParamType = "";
        private String updateParamType = "";

        public XmlCrudServiceInterface build(){
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
            if(funEntityIdType == null){
                throw new IllegalArgumentException("funEntityIdType is not null");
            }
            if(funEntityType == null){
                throw new IllegalArgumentException("funEntityType is not null");
            }
            if(funExplain == null){
                throw new IllegalArgumentException("funExplain is not null");
            }
            if(pageQueryType == null){
                throw new IllegalArgumentException("pageQueryType is not null");
            }
            if(insertParamType == null){
                throw new IllegalArgumentException("insertParamType is not null");
            }
            if(updateParamType == null){
                throw new IllegalArgumentException("updateParamType is not null");
            }
            return new XmlCrudServiceInterface(this);
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
         * @param funCode  功能代码（注意首字母大写）
         * @return  构建
         */
        public Builder funCode(String funCode){
            this.funCode =StrUtil.upperFirst(funCode);
            this.lowerFirstFunCode = StrUtil.lowerFirst(this.funCode);
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
         * 实体类型
         * @param funEntityType  实体类型
         * @return  构建
         */
        public Builder funEntityType(String funEntityType){
            this.funEntityType = funEntityType;
            return this;
        }

        /**
         * 实体类型
         * @param funExplain  实体类型
         * @return  构建
         */
        public Builder funExplain(String funExplain){
            this.funExplain = funExplain;
            return this;
        }

        /**
         * 分页查询参数类型
         * @param pageQueryType  分页查询参数类型
         * @return  构建
         */
        public Builder pageQueryType(String pageQueryType){
            this.pageQueryType = pageQueryType;
            return this;
        }

        /**
         * 添加参数类型
         * @param insertParamType  添加参数类型
         * @return  构建
         */
        public Builder insertParamType(String insertParamType){
            this.insertParamType = insertParamType;
            return this;
        }

        /**
         * 更新参数类型
         * @param updateParamType  更新参数类型
         * @return  构建
         */
        public Builder updateParamType(String updateParamType){
            this.updateParamType = updateParamType;
            return this;
        }
    }
}
