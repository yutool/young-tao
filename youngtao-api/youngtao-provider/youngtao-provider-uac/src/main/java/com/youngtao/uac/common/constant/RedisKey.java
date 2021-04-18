package com.youngtao.uac.common.constant;

import lombok.Getter;

/**
 * @author ankoye@qq.com
 * @date 2021/03/15
 */
public enum RedisKey {
    /**
     * key: toAddr
     */
    REGISTER_USER_CODE("youngtao:uac:email:registerUser:%s"),

    /**
     * key: toAddr
     */
    REGISTER_MER_CODE("youngtao:uac:email:registerMer:%s"),

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
