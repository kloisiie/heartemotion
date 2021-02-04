package com.brframework.core.xml.dom;

import com.brframework.constace.Visible;
import com.brframework.core.template.JavaFieldTemplate;
import com.brframework.core.template.JavaParamTemplate;
import com.brframework.utils.XmlUtils;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.List;

/**
 * @author xu
 * @date 2020/7/29 18:02
 */
@Data
public class DomField {
    private DomField(){}

    private String fieldExplain;
    private List<String> fieldAnnotations = Lists.newArrayList();
    private String visible = Visible.PUBLIC.code();
    private String fieldType;
    private String fieldName;

    /**
     * 解析XML
     * @param element  xml element
     * @return  dom
     */
    public static DomField parse(Element element){
        DomField domField = new DomField();
        domField.setFieldExplain(element.attributeValue("explain"));
        if(StringUtils.isNotEmpty(element.attributeValue("visible"))){
            domField.setVisible(element.attributeValue("visible"));
        }
        domField.setFieldType(element.attributeValue("type"));
        domField.setFieldName(element.attributeValue("name"));
        for (Iterator i = element.elementIterator(); i.hasNext(); ) {
            Element e = (Element) i.next();
            switch (e.getName()) {
                case "Annotation":
                    domField.getFieldAnnotations().add(XmlUtils.handleAnnotation(e));
                    break;
            }
        }

        return domField;
    }


    public JavaFieldTemplate toTemplate(){
        return JavaFieldTemplate.builder()
                .fieldExplain(getFieldExplain())
                .fieldAnnotations(getFieldAnnotations())
                .fieldType(getFieldType())
                .fieldName(getFieldName())
                .visible(Visible.valueOf(getVisible().toUpperCase()))
                .build();
    }
}
