package com.youngtao.core.util;

/**
 * @author ankoye@qq.com
 * @date 2020/12/13
 */
public class RocketMQUtils {
    public static String withTag(String topic, String tag) {
        return topic + ":" + tag;
    }
}
