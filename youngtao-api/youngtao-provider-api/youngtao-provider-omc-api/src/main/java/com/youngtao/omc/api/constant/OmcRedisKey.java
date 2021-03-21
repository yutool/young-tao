package com.youngtao.omc.api.constant;

import lombok.Getter;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
public enum OmcRedisKey {
    /**
     * key: paymentId
     */
    ORDER_STATUS("youngtao:omc:order:status:%s")
    ;

    @Getter
    private final String key;

    OmcRedisKey(String key) {
        this.key = key;
    }

    public String format(Object ...arg) {
        return String.format(key, arg);
    }

}
