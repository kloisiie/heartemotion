package com.brframework.utils;

import com.brframework.core.xml.dom.DomClass;
import com.brframework.core.xml.dom.DomField;
import com.brframework.core.xml.dom.DomMethod;
import com.brframework.core.xml.dom.DomParam;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;

/**
 * @author xu
 * @date 2020/7/29 17:58
 */
public class XmlUtils {

    @SneakyThrows
    public static Document document(String content){
        SAXReader saxReader = new SAXReader();
        return saxReader.read(new ByteArrayInputStream(content.getBytes()));
    }

    public static DomClass handleClass(Element classElement){
        return DomClass.parse(classElement);
    }

    public static DomMethod handleMethod(Element methodElement){
        return DomMethod.parse(methodElement);
    }

    public static DomField handleField(Element fieldElement){
        return DomField.parse(fieldElement);
    }

    public static String handleAnnotation(Element annotationElement){
        return annotationElement.getStringValue();
    }

    public static String handleImport(Element importElement){
        return importElement.getStringValue();
    }

    public static DomParam handleParam(Element paramElement){
        return DomParam.parse(paramElement);
    }
}
