package com.youngtao.web.advice;

import com.youngtao.core.exception.ServiceException;
import com.youngtao.core.result.ResponseCode;
import com.youngtao.core.result.ResponseResult;
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
    private ResponseResult<?> serviceException(ServiceException ex) {
        return new ResponseResult<>(ex.getCode(), ex.getMessage());
    }

    /**
     * feign调用异常
     */
    @ResponseBody
    @ExceptionHandler(RetryableException.class)
    private ResponseResult<?> retryableException(RetryableException ex) {
        return ResponseResult.error(ResponseCode.SERVICE_ERROR);
    }

    /**
     * 参数未通过@Valid验证异常，
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseResult<?> methodArgumentNotValid(MethodArgumentNotValidException ex) {
        return ResponseResult.error(ResponseCode.PARAM_IS_INVALID);
    }

    /**
     * 参数格式有误
     */
    @ResponseBody
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    private ResponseResult<?> typeMismatch(Exception ex) {
        return ResponseResult.error(ResponseCode.PARAM_IS_INVALID);
    }

    /**
     * 缺少参数
     */
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    private ResponseResult<?> missingServletRequestParameter(MissingServletRequestParameterException ex) {
        return ResponseResult.error(ResponseCode.PARAM_IS_BLANK);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    private ResponseResult<?> exceptionHandler(Exception ex) {
        return ResponseResult.error(ResponseCode.SYSTEM_INNER_ERROR);
    }
}
