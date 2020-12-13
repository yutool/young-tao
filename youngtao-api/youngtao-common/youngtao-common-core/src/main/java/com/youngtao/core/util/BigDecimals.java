package com.youngtao.core.util;

import java.math.BigDecimal;

/**
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
public class BigDecimals {
    public static final BigDecimal MAX_INT_VALUE = BigDecimal.valueOf(Integer.MAX_VALUE);

    /**
     * 四舍五入
     */
    public static BigDecimal round(BigDecimal num, Integer mul) {
        return num.multiply(new BigDecimal(mul)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
