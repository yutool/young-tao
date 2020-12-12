package com.youngtao.gmc.common.util;


import com.youngtao.core.util.RandomUtils;

/**
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
public final class IdUtils {

    /**
     * 有可能造成重复，未来需要写个专属的Id生成策略
     * @return int类型9位数最佳
     */
    public static String productId() {
        return RandomUtils.makeUpNewData(Thread.currentThread().hashCode() + "", 3) + RandomUtils.randomDigitNumber(6);
    }
}
