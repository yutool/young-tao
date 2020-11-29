package com.youngtao.core.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * web request result
 *
 * @author ankoye@qq.com
 */
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 7478131510160474918L;

    private Integer code;

    private String message;

    private T data;

    public ResponseResult() {
    }

    public ResponseResult(ResponseCode responseCode) {
        this.setResponseCode(responseCode);
    }

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }

    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<>(ResponseCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> ResponseResult<T> error() {
        return new ResponseResult<>(ResponseCode.ERROR);
    }

    public static <T> ResponseResult<T> error(T data) {
        ResponseResult<T> result = new ResponseResult<>(ResponseCode.ERROR);
        result.setData(data);
        return result;
    }

    public static <T> ResponseResult<T> error(ResponseCode responseCode) {
        return new ResponseResult<>(responseCode);
    }

    public static <T> ResponseResult<T> error(ResponseCode responseCode, T data) {
        ResponseResult<T> result = new ResponseResult<>(responseCode);
        result.setData(data);
        return result;
    }

    public static <T> ResponseResult<T> error(String errorMsg, T data) {
        ResponseResult<T> result = new ResponseResult<>(-1, errorMsg);
        result.setData(data);
        return result;
    }

    private void setResponseCode(ResponseCode code) {
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
    public String toString() {
        return "ResponseResult = { code: " + code +
                ", message: '" + message +
                ", data: " + data + " }";
    }
}
