package com.youngtao.gpc.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @author ankoye@qq.com
 * @date 2021/03/20
 */
public final class IdUtils {
    private static final Integer WORKER_ID = 1;

    private static final Integer DATACENTER_ID = 1;

    private static final Snowflake SNOWFLAKE = IdUtil.createSnowflake(WORKER_ID, DATACENTER_ID);

    public static String orderId() {
        return SNOWFLAKE.nextIdStr();
    }

}