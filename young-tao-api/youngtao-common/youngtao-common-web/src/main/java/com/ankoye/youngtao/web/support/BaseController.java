package com.ankoye.youngtao.web.support;

import com.ankoye.youngtao.core.result.ResponseResult;

/**
 * @author ankoye@qq.com
 */
public abstract class BaseController {

    /**
     * 根据返回值判断是否操作成功，一般使用在 增 删 改
     * 若不需要判断返回值，不要使用此方法
     * @param o service result
     */
    protected <T> ResponseResult<T> handleResult(T o) {
        boolean flag = isSuccess(o);
        ResponseResult<T> result;
        if (flag) {
            result = ResponseResult.success(o);
        } else {
            result = ResponseResult.error(o);
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
