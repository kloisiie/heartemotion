package com.brframework.core.xml.dom;

import com.brframework.common.utils.DateTimeUtils;
import com.brframework.constace.ClassType;
import com.brframework.core.template.JavaClassTemplate;
import com.brframework.utils.XmlUtils;
import com.google.common.collect.Lists;
import lombok.Data;
import org.dom4j.Element;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xu
 * @date 2020/7/29 18:00
 */
@Data
public class DomClass {
    private DomClass(){}

    private String type;
    private String packageName;
    private List<String> importList = Lists.newArrayList();
    private String classExplain;
    private String classAuthor;
    private String createDate;
    private List<String> classAnnotations = Lists.newArrayList();
    private String className;
    private String extendsClass;
    private String implementsClass;
    private List<DomField> fields = Lists.newArrayList();
    private List<DomMethod> methods = Lists.newArrayList();

    /**
     * 解析XML
     * @param element  xml element
     * @return  dom
     */
    public static DomClass parse(Element element){
        DomClass domClass = new DomClass();
        domClass.setType(element.attributeValue("type"));
        domClass.setPackageName(element.attributeValue("packageName"));
        domClass.setPackageName(element.attributeValue("packageName"));
        domClass.setClassExplain(element.attributeValue("explain"));
        domClass.setClassAuthor(element.attributeValue("author"));
        domClass.setCreateDate(element.attributeValue("createDate"));
        domClass.setClassName(element.attributeValue("name"));
        domClass.setExtendsClass(element.attributeValue("extendsClass"));
        domClass.setImplementsClass(element.attributeValue("implementsClass"));

        for (Iterator i = element.elementIterator(); i.hasNext(); ) {
            Element e = (Element) i.next();
            switch (e.getName()){
                case "Method":
                    domClass.getMethods().add(XmlUtils.handleMethod(e));
                    break;
                case "Annotation":
                    domClass.getClassAnnotations().add(XmlUtils.handleAnnotation(e));
                    break;
                case "Field":
                    domClass.getFields().add(XmlUtils.handleField(e));
                    break;
                case "Import":
                    domClass.getImportList().add(XmlUtils.handleImport(e));
                    break;
            }
        }

        return domClass;
    }


    public JavaClassTemplate toTemplate(){
        return JavaClassTemplate.builder()
                .type(ClassType.valueOf(getType().toUpperCase()))
                .implementsClass(getImplementsClass())
                .extendsClass(getExtendsClass())
                .packageName(getPackageName())
                .importList(getImportList())
                .classAuthor(getClassAuthor())
                .classExplain(getClassExplain())
                .className(getClassName())
                .createDate(getCreateDate())
                .classAnnotations(getClassAnnotations())
                .methods(getMethods().stream().map(DomMethod::toTemplate).collect(Collectors.toList()))
                .fields(getFields().stream().map(DomField::toTemplate).collect(Collectors.toList()))
                .build();
    }



}
