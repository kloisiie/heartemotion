package com.brframework.core.xml.dom;

import com.brframework.core.template.JavaParamTemplate;
import com.brframework.utils.XmlUtils;
import com.google.common.collect.Lists;
import lombok.Data;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.List;

/**
 * @author xu
 * @date 2020/7/29 18:02
 */
@Data
public class DomParam {
    private List<String> paramAnnotations = Lists.newArrayList();
    private String paramType;
    private String paramName;
    private String paramExplain;

    /**
     * 解析XML
     * @param element  xml element
     * @return  dom
     */
    public static DomParam parse(Element element){
        DomParam domParam = new DomParam();
        domParam.setParamType(element.attributeValue("type"));
        domParam.setParamName(element.attributeValue("name"));
        domParam.setParamExplain(element.attributeValue("explain"));
        for (Iterator i = element.elementIterator(); i.hasNext(); ) {
            Element e = (Element) i.next();
            switch (e.getName()) {
                case "Annotation":
                    domParam.getParamAnnotations().add(XmlUtils.handleAnnotation(e));
                    break;
            }
        }

        return domParam;
    }

    public JavaParamTemplate toTemplate(){
        return JavaParamTemplate.builder()
                .paramAnnotations(getParamAnnotations())
                .paramType(getParamType())
                .paramName(getParamName())
                .build();
    }
}
