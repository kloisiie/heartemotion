package com.brframework.core.xml.dom;

import com.brframework.constace.Visible;
import com.brframework.core.template.JavaFieldTemplate;
import com.brframework.core.template.JavaMethodTemplate;
import com.brframework.core.template.JavaParamTemplate;
import com.brframework.utils.XmlUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xu
 * @date 2020/7/29 18:01
 */
@Data
public class DomMethod {
    private String methodExplain;
    private Map<String, String> paramsExplains = Maps.newHashMap();
    private String visible = "default";
    private String returnExplain;
    private List<String> methodAnnotations = Lists.newArrayList();
    private String returnType;
    private String methodName;
    private List<DomParam> params = Lists.newArrayList();
    private String body;

    /**
     * 解析XML
     * @param element  xml element
     * @return  dom
     */
    public static DomMethod parse(Element element){
        DomMethod domMethod = new DomMethod();
        domMethod.setMethodExplain(element.attributeValue("explain"));
        if(StringUtils.isNotEmpty(element.attributeValue("visible"))){
            domMethod.setVisible(element.attributeValue("visible"));
        }

        domMethod.setMethodName(element.attributeValue("name"));
        for (Iterator i = element.elementIterator(); i.hasNext(); ) {
            Element e = (Element) i.next();
            switch (e.getName()) {
                case "Return":
                    domMethod.setReturnType(e.attributeValue("type"));
                    domMethod.setReturnExplain(e.attributeValue("explain"));
                    break;
                case "Annotation":
                    domMethod.getMethodAnnotations().add(XmlUtils.handleAnnotation(e));
                    break;
                case "Param":
                    domMethod.getParams().add(XmlUtils.handleParam(e));
                    break;
                case "Body":
                    domMethod.setBody(e.getStringValue());
                    break;
            }
        }

        for (DomParam param : domMethod.getParams()) {
            domMethod.getParamsExplains().put(param.getParamName(), param.getParamExplain());
        }

        return domMethod;
    }


    public JavaMethodTemplate toTemplate(){
        return JavaMethodTemplate.builder()
                .methodName(getMethodName())
                .methodExplain(getMethodExplain())
                .returnExplain(getReturnExplain())
                .returnType(getReturnType())
                .body(getBody())
                .paramsExplain(getParamsExplains())
                .methodAnnotations(getMethodAnnotations())
                .visible(Visible.valueOf(getVisible().toUpperCase()))
                .params(getParams().stream().map(DomParam::toTemplate).collect(Collectors.toList()))
                .build();
    }
}
