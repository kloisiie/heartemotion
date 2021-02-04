package cn.bluetech.gragas.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.brframework.common.utils.DateTimeUtils;
import com.brframework.commonweb.utils.ServletUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.alibaba.fastjson.serializer.SerializerFeature.DisableCircularReferenceDetect;

/**
 * @Author: ljr
 * @Date: 2019/9/5 15:39
 * @Description:
 */
@Configuration
public class MessageConverterConfig {


    @Bean
    public HttpMessageConverters customConverters() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter(){
            @Override
            public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                Object body = super.read(type, contextClass, inputMessage);
                if(ServletUtils.getServletRequestAttributes() != null){
                    ServletUtils.request().setAttribute("REQUEST_BODY", body);
                }
                return body;
            }
        };
        FastJsonConfig config = new FastJsonConfig();
        converter.setFastJsonConfig(config);
        config.setSerializerFeatures(DisableCircularReferenceDetect,
                //null的字段也会输出
                SerializerFeature.WriteMapNullValue
                //字符串为null字段返回""
                //SerializerFeature.WriteNullStringAsEmpty,
                //List字段如果为null,输出为[],而非null
                //SerializerFeature.WriteNullListAsEmpty,
                ////数值字段如果为null,输出为0,而非null
                //SerializerFeature.WriteNullNumberAsZero,
                ////Boolean字段如果为null,输出为false,而非null
                //SerializerFeature.WriteNullBooleanAsFalse);
        );
        return new HttpMessageConverters(converter);
    }


    /**
     * 日期时间转换
     */
    @Bean
    public Converter<String, LocalDateTime> LocalDateTimeConvert() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                if(StringUtils.isBlank(source)) return null;
                return DateTimeUtils.stringToDateTime(source);
            }
        };
    }


    /**
     * 日期转换
     */
    @Bean
    public Converter<String, LocalDate> LocalDateConvert() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                if(StringUtils.isBlank(source)) return null;
                return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        };
    }

    /**
     * 时间转换
     */
    @Bean
    public Converter<String, LocalTime> LocalTimeConvert() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                if(StringUtils.isBlank(source)) return null;
                return LocalTime.parse(source, DateTimeFormatter.ofPattern("HH:mm:ss"));
            }
        };
    }
}
