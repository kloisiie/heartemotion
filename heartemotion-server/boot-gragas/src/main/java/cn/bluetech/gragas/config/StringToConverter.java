package cn.bluetech.gragas.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author xu
 * @date 2020/11/25 23:41
 */
@Component
public class StringToConverter implements Converter<String, String> {

    @Override
    public String convert(String source) {
        return StringUtils.isBlank(source) ? null : source;
    }
}
