package com.youngtao.core.util;

import com.youngtao.core.exception.RpcResultException;
import com.youngtao.core.result.RpcResult;

/**
 * @author ankoye@qq.com
 * @date 2020/12/19
 */
public class RpcResultUtils {

    /**
     * 判断result是否为空或调用不成功
     * @param result result
     * @param message error message
     */
    public static void check(RpcResult<?> result, String message) {
        if (result == null) {
            throw new RpcResultException(-1000, message != null ? message : "result is null");
        }
        if (!result.isSuccess()) {
            throw new RpcResultException(result.getCode(), message != null ? message : result.getMessage());
        }
    }

    public static void check(RpcResult<?> result) {
        check(result, null);
    }

    /**
     * 判断result是否为空 或 调用不成功 或返回的数据为空
     * param result result
     * @param message error message
     */
    public static void checkNotNull(RpcResult<?> result, String message) {
        check(result, message);
        if (result.getData() == null) {
            throw new RpcResultException(result.getCode(), message != null ? message : result.getMessage());
        }
    }

    public static void checkNotNull(RpcResult<?> result) {
        checkNotNull(result, null);
    }
}
