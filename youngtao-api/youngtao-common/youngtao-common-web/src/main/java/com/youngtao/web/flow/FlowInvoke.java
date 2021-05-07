package com.youngtao.web.flow;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author ankoye@qq.com
 * @date 2021/05/04
 */
public interface FlowInvoke<T> extends InitializingBean {

    void invoke(T data);
}
