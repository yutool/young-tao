package com.youngtao.web.advice;

import com.youngtao.core.exception.RpcException;
import com.youngtao.core.exception.ServiceException;
import com.youngtao.core.result.RestResCode;
import com.youngtao.core.result.RestResult;
import com.youngtao.core.result.Result;
import feign.RetryableException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/**
 * @author ankoye@qq.com
 * @date 2020/12/29
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    private Result<?> serviceException(ServiceException ex) {
        return new RestResult<>(ex.getCode(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(RpcException.class)
    private Result<?> rpcException(RpcException ex) {
        return new RestResult<>(ex.getCode(), ex.getMessage());
    }

    /**
     * feign调用异常
     */
    @ResponseBody
    @ExceptionHandler(RetryableException.class)
    private Result<?> retryableException(RetryableException ex) {
        return RestResult.error(RestResCode.SERVICE_ERROR);
    }

    /**
     * 参数未通过@Valid验证异常
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private Result<?> methodArgumentNotValid(MethodArgumentNotValidException ex) {
        String message = ex.getMessage().substring(ex.getMessage().lastIndexOf(": [") + 3);
        message = message.substring(0, message.lastIndexOf("]"));
        return new RestResult<>(RestResCode.PARAM_IS_INVALID.code(), message);
    }

    /**
     * 参数格式有误
     */
    @ResponseBody
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    private Result<?> typeMismatch(Exception ex) {
        return RestResult.error(RestResCode.PARAM_IS_INVALID);
    }

    /**
     * 缺少参数
     */
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    private Result<?> missingServletRequestParameter(MissingServletRequestParameterException ex) {
        return RestResult.error(RestResCode.PARAM_IS_BLANK);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    private Result<?> exceptionHandler(Exception ex) {
        return RestResult.error(RestResCode.SYSTEM_INNER_ERROR);
    }
}
