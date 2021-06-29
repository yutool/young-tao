package com.youngtao.core.result;

import java.io.Serializable;

/**
 * @author ankoye@qq.com
 * @date 2021/06/28
 */
public abstract class Result<T> implements Serializable {

    private static final long serialVersionUID = -7915702355589855666L;

    protected Integer code;

    protected String message;

    protected T data;

    public abstract boolean isSuccess();

}
