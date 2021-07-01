package com.youngtao.gpc.common.constant;

import lombok.Getter;

/**
 * @author ankoye@qq.com
 * @date 2020/12/26
 */
public enum  CacheKey {
    /**
     * key: menu + skuId
     */
    PRODUCT_KEY("youngtao:gsc:cache:product:%s_%s"),

    /**
     * key: menu + skuId
     */
    SPU_KEY("youngtao:gsc:cache:product:%s_%s")
    ;

    @Getter
    private final String key;

    CacheKey(String key) {
        this.key = key;
    }

    public String format(Object ...arg) {
        return String.format(key, arg);
    }

}