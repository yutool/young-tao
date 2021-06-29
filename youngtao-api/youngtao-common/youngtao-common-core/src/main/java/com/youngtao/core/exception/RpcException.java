package com.youngtao.core.exception;

/**
 * @author ankoye@qq.com
 */
public class RpcException extends RuntimeException {
    private Integer code;

    private String message;

    public RpcException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
