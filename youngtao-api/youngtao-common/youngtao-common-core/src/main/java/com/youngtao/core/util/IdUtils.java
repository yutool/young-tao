package com.youngtao.core.util;

import java.util.UUID;

/**
 * @author ankoye@qq.com
 * @date 2021/04/02
 */
public class IdUtils {

    public static String getId(String prefix) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return prefix + "_" + uuid.substring(prefix.length() + 1);
    }
}
