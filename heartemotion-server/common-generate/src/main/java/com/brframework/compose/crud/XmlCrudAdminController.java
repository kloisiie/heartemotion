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
import java.util.regex.Pattern;

/**
 * @author xu
 * @date 2020/7/30 20:49
 */
public class XmlCrudAdminController implements Xml {
    public static String XML_PATH = "/crud/CrudAdminController.xml";
    private Builder builder;

    private XmlCrudAdminController(Builder builder){
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
        private String basePackageName;
        private String module;
        private String author;
        private String createDate = DateTimeUtils.dateTimeToString(LocalDateTime.now());
        private String funCode;
        private String serviceType;
        private String funCodeService;
        private String lowerFirstFunCode;
        private String partFunCode;
        private String funEntityType;
        private String funEntityIdType;
        private String funName;
        private String pageQueryParamDTOType;
        private String insertParamDTOType;
        private String updateParamDTOType;
        private String queryDtoSetCode;
        private String insertDtoSetCode;
        private String updateDtoSetCode;
        private String resultSetCode;
        private String queryResultType;
        private String queryParamType;
        private String insertParamType;
        private String updateParamType;
        private String detailResultType;
        private String detailResultSetCode;

        public XmlCrudAdminController build(){
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
            if(serviceType == null){
                throw new IllegalArgumentException("serviceType is not null");
            }
            if(funName == null){
                throw new IllegalArgumentException("funName is not null");
            }
            if(funEntityIdType == null){
                throw new IllegalArgumentException("funEntityIdType is not null");
            }
            if(pageQueryParamDTOType == null){
                throw new IllegalArgumentException("pageQueryParamDTOType is not null");
            }
            if(insertParamDTOType == null){
                throw new IllegalArgumentException("insertParamDTOType is not null");
            }
            if(updateParamDTOType == null){
                throw new IllegalArgumentException("updateParamDTOType is not null");
            }
            if(queryDtoSetCode == null){
                throw new IllegalArgumentException("queryDtoSetCode is not null");
            }
            if(insertDtoSetCode == null){
                throw new IllegalArgumentException("insertDtoSetCode is not null");
            }
            if(updateDtoSetCode == null){
                throw new IllegalArgumentException("updateDtoSetCode is not null");
            }
            if(queryResultType == null){
                throw new IllegalArgumentException("queryResultType is not null");
            }
            if(queryParamType == null){
                throw new IllegalArgumentException("queryParamType is not null");
            }
            if(insertParamType == null){
                throw new IllegalArgumentException("insertParamType is not null");
            }
            if(updateParamType == null){
                throw new IllegalArgumentException("updateParamType is not null");
            }
            if(detailResultType == null){
                throw new IllegalArgumentException("detailResultType is not null");
            }


            return new XmlCrudAdminController(this);
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
            this.funCode = StrUtil.upperFirst(funCode);
            this.lowerFirstFunCode = StrUtil.lowerFirst(this.funCode);
            this.funCodeService = this.lowerFirstFunCode + "Service";
            this.partFunCode = this.lowerFirstFunCode.replaceAll("[A-Z]", "-$0").toLowerCase();
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
         * ID类型
         * @param funEntityIdType  ID类型
         * @return  构建
         */
        public Builder funEntityIdType(String funEntityIdType){
            this.funEntityIdType = funEntityIdType;
            return this;
        }

        /**
         * Service类型
         * @param serviceType  Service类型
         * @return  构建
         */
        public Builder serviceType(String serviceType){
            this.serviceType = serviceType;
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
         * 查询DTOSet代码
         * @param queryDtoSetCode  查询DTOSet代码
         * @return  构建
         */
        public Builder queryDtoSetCode(String queryDtoSetCode){
            this.queryDtoSetCode = queryDtoSetCode;
            return this;
        }

        /**
         * resultSetCode
         * @param resultSetCode  resultSetCode
         * @return  构建
         */
        public Builder resultSetCode(String resultSetCode){
            this.resultSetCode = resultSetCode;
            return this;
        }

        /**
         * insertDtoSetCode
         * @param insertDtoSetCode  insertDtoSetCode
         * @return  构建
         */
        public Builder insertDtoSetCode(String insertDtoSetCode){
            this.insertDtoSetCode = insertDtoSetCode;
            return this;
        }

        /**
         * updateDtoSetCode
         * @param updateDtoSetCode  updateDtoSetCode
         * @return  构建
         */
        public Builder updateDtoSetCode(String updateDtoSetCode){
            this.updateDtoSetCode = updateDtoSetCode;
            return this;
        }

        /**
         * 分页查询DTO参数类型
         * @param pageQueryParamDTOType  分页查询DTO参数类型
         * @return  构建
         */
        public Builder pageQueryParamDTOType(String pageQueryParamDTOType){
            this.pageQueryParamDTOType = pageQueryParamDTOType;
            return this;
        }

        /**
         * 添加DTO参数类型
         * @param insertParamDTOType  添加DTO参数类型
         * @return  构建
         */
        public Builder insertParamDTOType(String insertParamDTOType){
            this.insertParamDTOType = insertParamDTOType;
            return this;
        }

        /**
         * 更新DTO参数类型
         * @param updateParamDTOType  更新DTO参数类型
         * @return  构建
         */
        public Builder updateParamDTOType(String updateParamDTOType){
            this.updateParamDTOType = updateParamDTOType;
            return this;
        }

        /**
         * queryResultType
         * @param queryResultType  queryResultType
         * @return  构建
         */
        public Builder queryResultType(String queryResultType){
            this.queryResultType = queryResultType;
            return this;
        }

        /**
         * queryParamType
         * @param queryParamType  queryParamType
         * @return  构建
         */
        public Builder queryParamType(String queryParamType){
            this.queryParamType = queryParamType;
            return this;
        }

        /**
         * insertParamType
         * @param insertParamType  insertParamType
         * @return  构建
         */
        public Builder insertParamType(String insertParamType){
            this.insertParamType = insertParamType;
            return this;
        }

        /**
         * updateParamType
         * @param updateParamType  updateParamType
         * @return  构建
         */
        public Builder updateParamType(String updateParamType){
            this.updateParamType = updateParamType;
            return this;
        }

        /**
         * detailResultType
         * @param detailResultType  detailResultType
         * @return  构建
         */
        public Builder detailResultType(String detailResultType){
            this.detailResultType = detailResultType;
            return this;
        }

        /**
         * detailResultSetCode
         * @param detailResultSetCode  detailResultSetCode
         * @return  构建
         */
        public Builder detailResultSetCode(String detailResultSetCode){
            this.detailResultSetCode = detailResultSetCode;
            return this;
        }
    }

}
