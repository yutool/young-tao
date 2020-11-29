package com.youngtao.core.lang;

import com.youngtao.core.util.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 数组形式的Json类
 *
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
public class JsonList<E> extends ArrayList<E> {

    public static <T> JsonList<T> build(T... elements) {
        JsonList<T> instance = new JsonList<>();
        Collections.addAll(instance, elements);
        return instance;
    }

    public static <T> JsonList<T> fromJson(String content) {
        return JsonUtils.fromJson(content, JsonList.class);
    }

    public String toJson() {
        return JsonUtils.toJson(this);
    }
}
