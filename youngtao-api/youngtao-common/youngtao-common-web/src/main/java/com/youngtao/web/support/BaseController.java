package com.youngtao.web.support;

import com.youngtao.core.result.RestResult;
import com.youngtao.core.result.Result;

/**
 * @author ankoye@qq.com
 */
public abstract class BaseController {

    /**
     * 根据返回值判断是否操作成功，一般使用在 增 删 改
     * 若不需要判断返回值，不要使用此方法
     * @param o service result
     */
    protected <T> Result<T> handleResult(T o) {
        boolean flag = isSuccess(o);
        Result<T> result;
        if (flag) {
            result = RestResult.success(o);
        } else {
            result = RestResult.error(o);
        }
        return result;
    }


    private boolean isSuccess(Object result) {
        boolean flag = result != null;
        if (result instanceof Integer) {
            flag = (Integer) result > 0;
        } else if (result instanceof Boolean) {
            flag = (Boolean) result;
        }
        return flag;
    }
}
