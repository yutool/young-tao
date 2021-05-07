package com.youngtao.web.flow;

/**
 * @author ankoye@qq.com
 * @date 2021/05/04
 */
public interface FlowHandler<T, E> {

    void handle(T data, E ctx);
}
