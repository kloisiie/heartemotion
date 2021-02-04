package cn.bluetech.gragas.config;

import com.alibaba.fastjson.JSON;
import com.brframework.commonweb.json.BaseJson;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author xu
 * @date 2020/11/25 23:41
 */
@Component
public class StringToGlobalConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        HashSet<ConvertiblePair> convertiblePairs = Sets.newHashSet();
        convertiblePairs.add(new ConvertiblePair(String.class, String[].class));
        convertiblePairs.add(new ConvertiblePair(String.class, Integer[].class));
        convertiblePairs.add(new ConvertiblePair(String.class, Long[].class));
        convertiblePairs.add(new ConvertiblePair(String.class, List.class));
        convertiblePairs.add(new ConvertiblePair(String.class, Map.class));
        convertiblePairs.add(new ConvertiblePair(String.class, BaseJson.class));
        return convertiblePairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if(source == null || StringUtils.isBlank(source.toString())){
            return null;
        }

        return JSON.parseObject(source.toString(), targetType.getResolvableType().getType());
    }
}
