package com.youngtao.core.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * web request result
 *
 * @author ankoye@qq.com
 */
public class RestResult<T> extends Result<T> implements Serializable {
    private static final long serialVersionUID = 7478131510160474918L;

    private static int SUCCESS_CODE = RestResCode.SUCCESS.code();

    public RestResult() {
    }

    public RestResult(RestResCode restResCode) {
        this.setResponseCode(restResCode);
    }

    public RestResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> RestResult<T> success() {
        return new RestResult<>(RestResCode.SUCCESS);
    }

    public static <T> RestResult<T> success(T data) {
        RestResult<T> result = new RestResult<>(RestResCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> RestResult<T> error() {
        return new RestResult<>(RestResCode.ERROR);
    }

    public static <T> RestResult<T> error(T data) {
        RestResult<T> result = new RestResult<>(RestResCode.ERROR);
        result.setData(data);
        return result;
    }

    public static <T> RestResult<T> error(RestResCode restResCode) {
        return new RestResult<>(restResCode);
    }

    public static <T> RestResult<T> error(RestResCode restResCode, T data) {
        RestResult<T> result = new RestResult<>(restResCode);
        result.setData(data);
        return result;
    }

    public static <T> RestResult<T> error(String errorMsg, T data) {
        RestResult<T> result = new RestResult<>(-1, errorMsg);
        result.setData(data);
        return result;
    }

    private void setResponseCode(RestResCode code) {
        this.code = code.code();
        this.message = code.message();
    }

    private Map<String, Object> simple() {
        Map<String, Object> simple = new HashMap<>();
        this.data = (T) simple;
        return simple;
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
    public boolean isSuccess() {
        return this.code == SUCCESS_CODE;
    }

    @Override
    public String toString() {
        return "ResponseResult = { code: " + code +
                ", message: '" + message +
                ", data: " + data + " }";
    }
}
