package com.youngtao.gmc.common.constant;

import lombok.Getter;

/**
 * @author ankoye@qq.com
 * @date 2021/03/15
 */
public enum RedisKey {
    /**
     * key: menu + spuId
     */
    SKU_INFO_KEY("youngtao:gsc:sku:info:%s_%s"),

    ;

    @Getter
    private final String key;

    RedisKey(String key) {
        this.key = key;
    }

    public String format(Object ...arg) {
        return String.format(key, arg);
    }

}
