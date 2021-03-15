package com.youngtao.gsc.common.constant;

import lombok.Getter;

/**
 * 专门存放Redis的各种Key，统一起来方便管理和维护
 * 每个Key需要写清楚注释，哪个流程的，有什么作用，里面的占位符是什么含义
 *
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
public enum RedisKey {
    /**
     * key: menu + spuId
     */
    SKU_INFO_KEY("youngtao:gsc:sku:info:%s_%s"),
    /**
     * key: menu
     */
    SKU_SET_KEY("youngtao:gsc:sku:set:%s"),
    /**
     * 扣减库存
     * key: menu + skuId
     */
    SKU_COUNT_KEY("youngtao:gsc:sku:count:%s_%s"),
    /**
     * 预订单队列
     * key: menu
     */
    ORDER_QUEUE("youngtao:gsc:order:queue:%s"),
    /**
     * 预订单队列
     * key userId + skuId
     */
    ORDER_QUEUE_KEY("youngtao:gsc:order:queue:%s_%s"),
    /**
     * 预订单队列
     * key userId + skuId
     */
    LOCK_ORDER_QUEUE("youngtao:gsc:lock:order:queue:%s_%s")

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
