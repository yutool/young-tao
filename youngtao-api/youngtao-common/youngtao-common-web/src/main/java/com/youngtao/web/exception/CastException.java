package com.youngtao.web.exception;


import com.youngtao.core.result.ResponseCode;

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
}
