package com.brframework.commondb.core;

import com.brframework.common.utils.ConvertObjectUtil;
import com.brframework.commonweb.exception.HandleException;
import com.brframework.commonweb.json.Page;
import com.brframework.commonweb.json.PageParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 控制器分页处理
 * @author xu
 * @date 2019-8-22 22:57:58
 */
public class ControllerPageHandle {


    /**
     * jpaPage 转 page
     * @param jpaPage
     * @param pageIndex
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> Page<T> jpaPageToPage(org.springframework.data.domain.Page<T> jpaPage, int pageIndex, int pageSize){
        Page<T> page = new Page<>(pageIndex, pageSize, jpaPage.getTotalElements());
        page.setList(jpaPage.getContent());
        return page;
    }

    public static <T> Page<T> jpaPageToPage(org.springframework.data.domain.Page<T> jpaPage, PageParam param){
        Page<T> page = new Page<>(param.getPageIndex(), param.getPageSize(), jpaPage.getTotalElements());
        page.setList(jpaPage.getContent());
        return page;
    }

    /**
     * jpaPage转 page，并且数据对象也进行自动转换
     * @param jpaPage
     * @param param
     * @param toClass
     * @param <T>
     * @return
     */
    public static <T> Page<T> jpaPageConvertToPage(org.springframework.data.domain.Page jpaPage, PageParam param, Class<T> toClass){
        Page<T> page = new Page<>(param.getPageIndex(), param.getPageSize(), jpaPage.getTotalElements());
        if( jpaPage.getContent().size() == 0){
            return page;
        }
        page.setList(ConvertObjectUtil.convertJavaBeanList(toClass, jpaPage.getContent()));
        return page;
    }

    /**
     * jpaPage转 page，并且数据对象可自定义处理转换
     * @param jpaPage
     * @param param
     * @param objectHandle
     * @param <T>
     * @param <F>
     * @return
     */
    public static <T, F> Page<T> jpaPageHandleToPage(org.springframework.data.domain.Page<F> jpaPage,
                                                     PageParam param, ObjectHandle<T, F> objectHandle){
        Page<T> page = new Page<>(param.getPageIndex(), param.getPageSize(), jpaPage.getTotalElements());
        if( jpaPage.getContent().size() == 0){
            return page;
        }
        Stream<F> stream = jpaPage.get().collect(Collectors.toList()).stream().parallel();
        page.setList(stream.map(objectHandle::handle).collect(Collectors.toList()) );
        return page;
    }

    /**
     * 从一个类型转换为另一个类型，并集成类型中一模一样字段中的值
     * @param from
     * @param toClass
     * @param <T>
     * @return
     */
    public static <T> List<T> convertListTo(List from, Class<T> toClass){
        return ConvertObjectUtil.convertJavaBeanList(toClass, from);
    }

    /**
     * 从一个类型转换为另一个类型，并集成类型中一模一样字段中的值
     * @param from
     * @param <T>
     * @return
     */
    public static <T> T convertTo(Object from, Class<T> toClass, boolean constraint){
        try {
            return ConvertObjectUtil.convertJavaBean(toClass.newInstance(), from, constraint);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new HandleException("参数异常");
    }

    public static <T> T convertTo(Object from, Class<T> toClass){
        return convertTo(from, toClass, false);
    }

    /**
     * 对象处理
     * @param <T>  处理后的结果类型
     * @param <F>  处理前的数据类型
     */
    public interface ObjectHandle<T, F>{
        T handle(F from);
    }

}
