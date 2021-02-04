package com.brframework.cms2.utils;

import com.brframework.commonsecurity.core.SecurityContextHolder;
import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonweb.json.PageParam;
import com.brframework.commonweb.utils.ServletUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

/**
 * @author xu
 * @date 2018/10/15 15:26
 */
public class ControllerUtil {
    private final static LocalVariableTableParameterNameDiscoverer LVTPND = new LocalVariableTableParameterNameDiscoverer();

    /**
     * 获取一个类所有的字段
     * @param c
     * @return
     */
    public static List<Field> getAllField(Class<?> c){
        List<Field> fields = Lists.newArrayList(c.getDeclaredFields());
        if(Object.class.getName().equals(c.getSuperclass().getName())){
            return fields;
        }

        fields.addAll(getAllField(c.getSuperclass()));

        return fields;
    }

    /**
     * 获取方法的参数，解决参数名为arg0, arg1的问题
     * @param method
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Parameter[] getHandleParameter(Method method) throws NoSuchFieldException, IllegalAccessException {
        String[] parameterNames = LVTPND.getParameterNames(method);
        Parameter[] parameters = method.getParameters();

        Field nameField = Parameter.class.getDeclaredField("name");
        nameField.setAccessible(true);
        for (int i = 0; i < parameterNames.length; i++) {
            nameField.set(parameters[i], parameterNames[i]);
        }

        return parameters;
    }

    /**
     * 获取请求里的URI参数
     * @param method
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Map<String, String> getUriParameter(Method method) throws NoSuchFieldException, IllegalAccessException {
        Map<String, Object> uriParams = (Map<String, Object>) ServletUtils.request().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Parameter[] parameters = ControllerUtil.getHandleParameter(method);

        Map<String, String> uriParameter = Maps.newHashMap();
        for (Parameter parameter : parameters) {
            PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
            if(pathVariable != null){

                String name = pathVariable.value();
                if(Strings.isNullOrEmpty(name)){
                    name = parameter.getName();
                }

                uriParameter.put(name, uriParams.get(name).toString());
            }

        }

        return uriParameter;
    }


    /**
     * 将包含有ApiModelProperty注解的字段筛选出来
     *
     * @param oldFields
     * @return
     */
    public static List<Field> handlerPropertyField(List<Field> oldFields) {
        List<Field> newFields = Lists.newArrayList();

        for (Field oldField : oldFields) {
            ApiModelProperty apiModelProperty =
                    AnnotationUtils.findAnnotation(oldField, ApiModelProperty.class);

            if (apiModelProperty != null) {
                newFields.add(oldField);
            }
        }

        return newFields;

    }

    /**
     * 获取方法参数里有ApiModel注解的类
     * 自动忽略PageParam
     *
     * @param method
     * @return
     */
    public static List<Class> getParamApiModelClass(Method method) {
        Class[] parameterTypes = method.getParameterTypes();
        List<Class> apiTypes = Lists.newArrayList();
        for (Class<?> parameterType : parameterTypes) {
            if (parameterType.getDeclaredAnnotation(ApiModel.class) != null) {
                if (!parameterType.getName().equals(PageParam.class.getName())) {
                    apiTypes.add(parameterType);
                }

            }
        }

        return apiTypes;
    }

    /**
     * 获取Controller完整的url
     *
     * @param method
     * @return
     */
    public static String getControllerUrl(Method method) {
        //获取前缀
        String prefix = getPrefixUrl(method);
        //获取请求地址
        String url = getMappingUrl(method);

        if (!url.startsWith("/") && !prefix.endsWith("/")) {
            url = "/" + url;
        }

        if (url.startsWith("/") && prefix.endsWith("/")) {
            url = url.substring(1);
        }

        return prefix + url;
    }

    /**
     * 获取方法的前缀
     *
     * @param method
     * @return
     */
    public static String getPrefixUrl(Method method) {
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(method.getDeclaringClass(), RequestMapping.class);
        String prefix = "";
        if (requestMapping != null) {
            prefix = requestMapping.value()[0];
        }

        return prefix;
    }

    /**
     * 获取标题
     *
     * @param method
     * @return
     */
    public static String getTitle(Method method) {
        ApiOperation ao = AnnotatedElementUtils.findMergedAnnotation(method, ApiOperation.class);
        if (ao == null) {
            throw new HandleException("方法" + method.toString() + "不存在ApiOperation注解");
        }

        return ao.value();
    }

    /**
     * 获取方法映射的url
     *
     * @param method
     * @return
     */
    public static String getMappingUrl(Method method) {
        RequestMapping rm = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        return rm.value()[0];
    }

    /**
     * 获取HTTP METHOD
     *
     * @param method
     * @return
     */
    public static String getHttpMethod(Method method) {
        RequestMapping rm = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        if (rm.method().length == 0) {
            throw new HandleException("当前方法中不存在RequestMapping注解");
        }
        return rm.method()[0].toString();
    }


    public static String getRoleByMethod(Method method){
        PreAuthorize authAnn = AnnotationUtils.findAnnotation(method, PreAuthorize.class);
        if(authAnn == null){
            return "";
        }

        return StringUtils.substring(authAnn.value(),
                authAnn.value().indexOf("'") + 1, authAnn.value().lastIndexOf("'")).trim();
    }
}
