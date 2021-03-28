package com.youngtao.core.util;

import com.google.common.base.Preconditions;
import com.youngtao.core.lang.JsonMap;

/**
 * @author ankoye@qq.com
 * @date 2021/01/10
 */
public class ProductUtils {
    public static String generateTitle(String spu, JsonMap<String> sku) {
        Preconditions.checkArgument(spu != null, "the spu cannot be null");
        Preconditions.checkArgument(sku != null, "the spu cannot be null");
        StringBuilder sb = new StringBuilder(spu);
        sku.forEach((key, value) -> {
            sb.append(" ").append(key).append(" ").append(value);
        });
        return sb.toString();
    }
}
