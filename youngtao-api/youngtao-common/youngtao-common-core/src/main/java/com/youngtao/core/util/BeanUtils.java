package com.youngtao.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/29
 */
public class BeanUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    static  {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T copy(Object source, Class<T> target) {
        try {
            String json = mapper.writeValueAsString(source);
            return mapper.readValue(json, target);
        } catch (JsonProcessingException e) {
            // do nothing
        }
        return null;
    }

    public static <T> List<T> copyList(Object source, Class<T> target) {
        try {
            String json = mapper.writeValueAsString(source);
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, target));
        } catch (JsonProcessingException e) {
            // do nothing
        }
        return null;
    }

}
