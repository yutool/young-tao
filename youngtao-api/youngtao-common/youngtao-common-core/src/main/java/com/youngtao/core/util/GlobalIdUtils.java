package com.youngtao.core.util;

import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ankoye@qq.com
 * @date 2020/12/12
 */
public class GlobalIdUtils {

    public static String objectId(String prefix) {
        String objectId = IdUtil.objectId();
        if (StringUtils.isBlank(prefix)) {
            return objectId;
        }
        return prefix + objectId.substring(prefix.length());
    }
}
