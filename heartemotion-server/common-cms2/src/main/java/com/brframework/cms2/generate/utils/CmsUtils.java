package com.brframework.cms2.generate.utils;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.brframework.cms2.generate.*;
import com.brframework.cms2.generate.column.InputColumnEntry;
import com.brframework.cms2.generate.column.NumberColumnEntry;
import com.brframework.cms2.generate.column.TableColumnEntry;
import com.brframework.cms2.utils.ControllerUtil;
import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonweb.json.BaseJson;
import com.brframework.commonweb.json.Page;
import io.swagger.annotations.ApiModelProperty;
import io.vavr.Tuple2;
import io.vavr.Tuple4;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xu
 * @date 2020/12/9 15:25
 */
public class CmsUtils {

    public static Method findMethodByName(Class cla, String name){
        for (Method method : cla.getMethods()) {
            if(method.getName().equals(name)){
                return method;
            }
        }
        throw new RuntimeException("类找不到方法：" + name);
    }


    public static void printMethodParamColumn(Method method){
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            if(BaseJson.class.isAssignableFrom(parameter.getType())){

                List<Field> declaredFields = ControllerUtil.handlerPropertyField(
                        ControllerUtil.getAllField(parameter.getType())
                );

                for (Field declaredField : declaredFields) {
                    ApiModelProperty apiModelProperty =
                            AnnotationUtils.findAnnotation(declaredField, ApiModelProperty.class);
                    assert apiModelProperty != null;

                    String notnull = "";
                    if(declaredField.getAnnotation(NotNull.class) != null){
                        notnull = ".required(true)";
                        InputColumnEntry.builder().required(true).build();
                    }

                    String stringMinMax = "";
                    if(declaredField.getAnnotation(Length.class) != null){
                        Length length = declaredField.getAnnotation(Length.class);
                        stringMinMax = ".minLength(" + length.min() + ").maxLength(" + length.max() + ")";
                    }

                    String numberMinMax = "";
                    if(declaredField.getAnnotation(Range.class) != null){
                        Range range = declaredField.getAnnotation(Range.class);
                        numberMinMax = ".min(" + range.min() + ").max(" + range.max() + ")";
                    }


                    if(Number.class.isAssignableFrom(declaredField.getType())){
                        String ext = notnull + numberMinMax;
                        System.out.println("NumberColumnEntry.builder().label(\"" + apiModelProperty.value() + "\").prop(\"" + declaredField.getName() + "\")" + ext + ".build(),");
                    } else if(declaredField.getName().toLowerCase().contains("image") || declaredField.getName().toLowerCase().contains("img")){
                        String ext = notnull;
                        System.out.println("ImageUploadColumnEntry.builder().label(\"" + apiModelProperty.value() + "\").prop(\"" + declaredField.getName() + "\")" + ext + ".build(),");
                    } else {
                        String ext = notnull + stringMinMax;
                        System.out.println("InputColumnEntry.builder().label(\"" + apiModelProperty.value() + "\").prop(\"" + declaredField.getName() + "\")" + ext + ".build(),");
                    }

                }
            }
        }
    }

    public static void printMethodResultColumn(Method method){
        Class<?> returnType = method.getReturnType();
        ResolvableType methodType = ResolvableType.forMethodReturnType(method);

        //获取Data
        ResolvableType pageType = methodType.getGeneric(0);
        if (!BaseJson.class.isAssignableFrom(pageType.getRawClass())) {
            throw new HandleException("返回值类型必须继承BaseJson");
        }

        List<Field> returnTypeFields = ControllerUtil.handlerPropertyField(
                ControllerUtil.getAllField(pageType.getRawClass())
        );

        for (Field field : returnTypeFields) {
            ApiModelProperty apiModelProperty =
                    AnnotationUtils.findAnnotation(field, ApiModelProperty.class);
            assert apiModelProperty != null;
            if(Number.class.isAssignableFrom(field.getType())){
                System.out.println("NumberColumnEntry.builder().label(\"" + apiModelProperty.value() + "\").prop(\"" + field.getName() + "\")" + ".build(),");
            } else if(field.getName().toLowerCase().contains("image") || field.getName().toLowerCase().contains("img")){
                System.out.println("ImageUploadColumnEntry.builder().label(\"" + apiModelProperty.value() + "\").prop(\"" + field.getName() + "\")" + ".build(),");
            } else {
                System.out.println("InputColumnEntry.builder().label(\"" + apiModelProperty.value() + "\").prop(\"" + field.getName() + "\")" + ".build(),");
            }

        }
    }




    public static void printMethodPageResultTableColumn(Method method){
        Class<?> returnType = method.getReturnType();
        ResolvableType methodType = ResolvableType.forMethodReturnType(method);
        ResolvableType dataType = methodType.getGeneric(0);
        if (!dataType.getRawClass().getName().equals(Page.class.getName())) {
            throw new HandleException("返回值类型" + returnType.getName() + "非协议类型");
        }

        //获取Data
        ResolvableType pageType = dataType.getGeneric(0);
        if (!BaseJson.class.isAssignableFrom(pageType.getRawClass())) {
            throw new HandleException("返回值类型必须继承BaseJson");
        }

        List<Field> returnTypeFields = ControllerUtil.handlerPropertyField(
                ControllerUtil.getAllField(pageType.getRawClass())
        );

        for (Field field : returnTypeFields) {
            ApiModelProperty apiModelProperty =
                    AnnotationUtils.findAnnotation(field, ApiModelProperty.class);
            assert apiModelProperty != null;
            if(Number.class.isAssignableFrom(field.getType())){
                System.out.println("TableColumnEntry.builder().label(\"" + apiModelProperty.value() + "\").prop(\"" + field.getName() + "\").columnType(GenerateGlobals.TableColumnType.text).width(\"50\").build(),");
            } else if(field.getName().toLowerCase().contains("image") || field.getName().toLowerCase().contains("img")){
                System.out.println("TableColumnEntry.builder().label(\"" + apiModelProperty.value() + "\").prop(\"" + field.getName() + "\").columnType(GenerateGlobals.TableColumnType.image).width(\"\").build(),");
            } else {
                System.out.println("TableColumnEntry.builder().label(\"" + apiModelProperty.value() + "\").prop(\"" + field.getName() + "\").columnType(GenerateGlobals.TableColumnType.text).width(\"\").build(),");
            }

        }
    }

    public static ButtonEntry tupleToActionButtonEntry(String server,
                                Tuple4<Method, GenerateGlobals.ProtocolType, String, List<Tuple2<String, String>>> tuple){
        String httpMethod = ControllerUtil.getHttpMethod(tuple._1);
        String icon;
        GenerateGlobals.ButtonStyle style;
        switch (httpMethod.toUpperCase()){
            case "GET":
                icon = "el-icon-plus";
                style = GenerateGlobals.ButtonStyle.info;
                break;
            case "PUT":
                icon = "el-icon-edit";
                style = GenerateGlobals.ButtonStyle.success;
                break;
            case "DELETE":
                icon = "el-icon-delete";
                style = GenerateGlobals.ButtonStyle.danger;
                break;
            case "POST":
            default:
                icon = "el-icon-plus";
                style = GenerateGlobals.ButtonStyle.primary;
                break;
        }
        return tupleToActionButtonEntry(server, tuple, icon, style, "");
    }

    public static ButtonEntry tupleToActionButtonEntry(String server,
                                                       Tuple4<Method, GenerateGlobals.ProtocolType, String, List<Tuple2<String, String>>> tuple,
                                                       String icon, GenerateGlobals.ButtonStyle style, String condition){
        String httpMethod = ControllerUtil.getHttpMethod(tuple._1);
        Map<String, String> params = new HashMap<>();
        if(tuple._4 != null){
            for (Tuple2<String, String> param : tuple._4) {
                params.put(param._1(), param._2());
            }
        }

        if(tuple._2.equals(GenerateGlobals.ProtocolType.request)){
            return ButtonEntry.builder()
                    .url(
                            RequestProtocol.builder()
                                    .method(httpMethod.toUpperCase())
                                    .url(methodToCmsUrl(tuple._1))
                                    .alert("您确定" + tuple._3 + "吗？")
                                    .refresh(true)
                                    .addParam(params)
                                    .build()
                    )
                    .label(tuple._3)
                    .icon(icon)
                    .btnStyle(style)
                    .isCircle(false)
                    .selection(true)
                    .condition(condition)
                    .plain(true)
                    .btnType(GenerateGlobals.ButtonType.button)
                    .build();
        } else {
            return ButtonEntry.builder()
                    .url(PostSystem.getRouteInfo(server, tuple._1.getName()).getString("route"))
                    .label(tuple._3)
                    .icon(icon)
                    .btnStyle(style)
                    .isCircle(false)
                    .condition(condition)
                    .selection(false)
                    .plain(true)
                    .btnType(GenerateGlobals.ButtonType.button)
                    .build();
        }
    }


    public static ButtonEntry tupleToOpartionButtonEntry(String server, Tuple4<Method, GenerateGlobals.ProtocolType, String, List<Tuple2<String, String>>> tuple){
        String httpMethod = ControllerUtil.getHttpMethod(tuple._1);
        String icon;
        GenerateGlobals.ButtonStyle style;
        switch (httpMethod.toUpperCase()){
            case "GET":
                icon = "el-icon-plus";
                style = GenerateGlobals.ButtonStyle.info;
                break;
            case "PUT":
                icon = "el-icon-edit";
                style = GenerateGlobals.ButtonStyle.success;
                break;
            case "DELETE":
                icon = "el-icon-delete";
                style = GenerateGlobals.ButtonStyle.danger;
                break;
            case "POST":
            default:
                icon = "el-icon-plus";
                style = GenerateGlobals.ButtonStyle.primary;
                break;
        }
        Map<String, String> params = new HashMap<>();
        if(tuple._4 != null){
            for (Tuple2<String, String> param : tuple._4) {
                params.put(param._1(), param._2());
            }
        }

        if(tuple._2.equals(GenerateGlobals.ProtocolType.request)){
            return ButtonEntry.builder()
                    .url(
                            RequestProtocol.builder()
                                    .method(httpMethod.toUpperCase())
                                    .url(methodToCmsUrl(tuple._1))
                                    .alert("您确定" + tuple._3 + "吗？")
                                    .refresh(true)
                                    .addParam(params)
                                    .build()
                    )
                    .label(tuple._3)
                    .icon(icon)
                    .btnStyle(style)
                    .build();
        } else {
            return ButtonEntry.builder()
                    .url(PostSystem.getRouteInfo(server, tuple._1.getName()).getString("route"))
                    .label(tuple._3)
                    .icon(icon)
                    .btnStyle(style)
                    .build();
        }
    }

    public static String methodToCmsUrl(Method method){
        return ControllerUtil.getControllerUrl(method).replaceAll("\\{", "#{");
    }

    public static String methodToRequest(Method method, Map<String, String> params){
        return RequestProtocol.builder()
                .method(ControllerUtil.getHttpMethod(method))
                .url(CmsUtils.methodToCmsUrl(method))
                .alert("您确定进行该操作吗？")
                .refresh(true)
                .addParam(params)
                .build();
    }

    public static String methodToRequest(Method method){
        return methodToRequest(method, Collections.emptyMap());
    }

    public static String dictToRequest(String dict){
        return RequestProtocol.builder()
                .method("GET")
                .url("/admin/access/v1/cms/dict/" + dict)
                .build();
    }




}
