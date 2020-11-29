package com.youngtao.core.lang;

import com.youngtao.core.util.JsonUtils;

import java.util.LinkedHashMap;

/**
 * 对象形式的Json类
 *
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
public class JsonMap<V> extends LinkedHashMap<String, V> {

    public static <T> JsonMap<T> build() {
        return new JsonMap<>();
    }

    public static <T> JsonMap<T> fromJson(String content) {
        return JsonUtils.fromJson(content, JsonMap.class);
    }

    public String toJson() {
        return JsonUtils.toJson(this);
    }
}
