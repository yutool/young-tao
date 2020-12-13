package com.youngtao.core.exception;


import com.youngtao.core.result.ResponseCode;
import com.youngtao.core.result.RpcResult;

/**
 * 异常抛出类
 *
 * @author ankoye@qq.com
 */
public class CastException {
    public static void cast(Integer code, String message) {
        throw new ServiceException(code, message);
    }

    public static void cast(String message) {
        throw new ServiceException(message);
    }

    public static void cast(ResponseCode responseCode) {
        throw new ServiceException(responseCode.code(), responseCode.message());
    }

    public static void cast(RpcResult<?> result) {
        throw new RpcResultException(result.getCode(), result.getMessage());
    }
}
