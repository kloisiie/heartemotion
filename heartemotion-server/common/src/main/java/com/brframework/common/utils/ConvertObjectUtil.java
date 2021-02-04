package com.brframework.common.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConvertObjectUtil {

    static Map<String, Method[]> methodMap = new ConcurrentHashMap<>();


    public static <T> T convertJavaBean(T to, Object from){
        return convertJavaBean(to, from, false);
    }

    /**
     * <p>
     *
     * 两个不同对象之间的赋值
     *
     * @param to   基准类,被赋值对象
     * @param from 提供数据的对象
     * @param constraint  是否强制赋值，false情况下，对象如果已经有值的不会重复赋值
     * @throws Exception
     * @description 转换javabean ,将class2中的属性值赋值给class1，如果class1属性有值，则不覆盖
     * ，前提条件是有相同的属性名
     * </p>
     * @author ex_dingyongbiao
     * @see
     */
    public static <T> T convertJavaBean(T to, Object from, boolean constraint) {

        if(from == null){
            return null;
        }

        try {

            Class<?> clazz1 = to.getClass();
            Class<?> clazz2 = from.getClass();
            // 得到method方法
            Method[] method1;
            Method[] method2;
            if (methodMap.containsKey(clazz1.getName())) {
                method1 = methodMap.get(clazz1.getName());
            } else {
                method1 = clazz1.getMethods();
                methodMap.put(clazz1.getName(), method1);
            }

            if (methodMap.containsKey(clazz2.getName())) {
                method2 = methodMap.get(clazz2.getName());
            } else {
                method2 = clazz2.getMethods();
                methodMap.put(clazz2.getName(), method2);
            }


            int length1 = method1.length;
            int length2 = method2.length;
            if (length1 != 0 && length2 != 0) {
                // 创建一个get方法数组，专门存放class2的get方法。
                Method[] get = new Method[length2];
                for (int i = 0, j = 0; i < length2; i++) {
                    method2[i].setAccessible(true);
                    if (method2[i].getName().indexOf("get") == 0) {
                        get[j] = method2[i];
                        ++j;
                    }
                }

                for (Method method : get) {
                    if (method == null)// 数组初始化的长度多于get方法，所以数组后面的部分是null
                    {
                        continue;
                    }
                    // 得到get方法的值，判断时候为null，如果为null则进行下一个循环
                    Object value = method.invoke(from, null);
                    if (null == value) {
                        continue;
                    }
                    // 得到get方法的名称 例如：getXxxx
                    String getName = method.getName();
                    if(StringUtils.equals(getName, "getClass")){
                        //跳过Object的getClass方法
                        continue;
                    }

                    // 得到set方法的时候传入的参数类型，就是get方法的返回类型
                    Class<?> paramType = method.getReturnType();

                    Method getMethod = null;
                    try {
                        // 判断在class1中时候有class2中的get方法，如果没有则抛异常继续循环
                        getMethod = clazz1.getMethod(getName, null);
                    } catch (NoSuchMethodException e) {
                        continue;
                    }
                    // class1的get方法不为空并且class1中get方法得到的值为空，进行赋值，如果class1属性原来有值，则跳过
                    if (null == getMethod) {
                        continue;
                    }
                    if(!constraint){
                        // class1的get方法不为空并且class1中get方法得到的值为空，进行赋值，如果class1属性原来有值，则跳过
                        if (null != getMethod.invoke(to, null)) {
                            continue;
                        }
                    }

                    // 通过getName 例如getXxxx 截取后得到Xxxx，然后在前面加上set，就组装成set的方法名
                    String setName = "set" + getName.substring(3);

                    // 得到class1的set方法，并调用
                    Method setMethod = null;
                    for (Method method3 : method1) {
                        if(method3.getName().equals(setName)){
                            Class<?> parameterType = method3.getParameterTypes()[0];
                            if(parameterType.getName().equals(paramType.getName())){
                                setMethod = method3;
                                break;
                            }
                            if(parameterType.getName().equals(String.class.getName())){
                                //匹配到set方法的参数为String
                                value = value.toString();
                                setMethod = method3;
                                break;
                            }
                            if(parameterType.getName().equals(Long.class.getName())){
                                //匹配到set方法的参数为Long
                                value = Long.parseLong(value.toString());
                                setMethod = method3;
                                break;
                            }
                            if(parameterType.getName().equals(Integer.class.getName())){
                                //匹配到set方法的参数为Integer
                                value = Integer.parseInt(value.toString());
                                setMethod = method3;
                                break;
                            }
                            if(parameterType.getName().equals(BigDecimal.class.getName())){
                                //匹配到set方法的参数为BigDecimal
                                value = new BigDecimal(value.toString());
                                setMethod = method3;
                                break;
                            }
                        }
                    }
                    if(setMethod == null) {
                        throw new RuntimeException("无法匹配Method的参数类型: + " + paramType.getName());
                    }
                    setMethod.invoke(to, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return to;
    }


    public static <T> List<T> convertJavaBeanList(Class<T> cla, List from) {
        List<T> result = Lists.newArrayList();
        for (int i = 0; i < from.size(); i++) {
            try {
                result.add( convertJavaBean(cla.newInstance(), from.get(i)));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
