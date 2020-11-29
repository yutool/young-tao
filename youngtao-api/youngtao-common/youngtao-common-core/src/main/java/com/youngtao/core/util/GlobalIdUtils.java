package com.youngtao.core.util;

/**
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
public final class GlobalIdUtils {

    public static String generateId() {
        return MathUtils.makeUpNewData(Thread.currentThread().hashCode() + "", 3) + MathUtils.randomDigitNumber(7);
    }

}
