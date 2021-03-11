package com.youngtao.gsc.common.util;

import com.youngtao.core.lang.JsonMap;
import org.springframework.util.Assert;

/**
 * @author ankoye@qq.com
 * @date 2021/01/10
 */
public class ProductUtils {
    public static String generateTitle(String spu, JsonMap<String> sku) {
        Assert.notNull(spu, "the spu cannot be null");
        Assert.notNull(sku, "the spu cannot be null");
        StringBuilder sb = new StringBuilder(spu);
        sku.forEach((key, value) -> {
            sb.append(" ").append(key).append(" ").append(value);
        });
        return sb.toString();
    }
}
