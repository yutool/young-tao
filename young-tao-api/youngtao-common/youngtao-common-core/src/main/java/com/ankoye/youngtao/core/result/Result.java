package com.ankoye.youngtao.core.result;

import java.io.Serializable;
import java.util.Objects;

/**
 * api request request
 *
 * @author ankoye@qq.com
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 4268670999252934435L;

    private static final Integer SUCCESS_CODE = ResultCode.SUCCESS.code();

    private Integer code;

    private String message;

    private T data;

    public Result() {
    }

    public Result(ResultCode resultCode) {
        this.setResultCode(resultCode);
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error() {
        return new Result<>(ResultCode.ERROR);
    }

    public static <T> Result<T> error(T data) {
        Result<T> result = new Result<>(ResultCode.ERROR);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(ResultCode resultCode) {
        return new Result<>(resultCode);
    }

    public static <T> Result<T> error(ResultCode resultCode, T data) {
        Result<T> result = new Result<>(resultCode);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String errorMsg, T data) {
        Result<T> result = new Result<>(-1, errorMsg);
        result.setData(data);
        return result;
    }

    public boolean isSuccess() {
        if (!Objects.equals(this.code, SUCCESS_CODE)) {
            return false;
        }
        return this.data != null;
    }

    private void setResultCode(ResultCode code) {
        this.code = code.code();
        this.message = code.message();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result = { code: " + code +
                ", message: " + message +
                ", data: " + data + " }";
    }
}
