package com.youngtao.core.exception;


import com.youngtao.core.result.RestResCode;

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

    public static void cast(RestResCode restResCode) {
        throw new ServiceException(restResCode.code(), restResCode.message());
    }
}
