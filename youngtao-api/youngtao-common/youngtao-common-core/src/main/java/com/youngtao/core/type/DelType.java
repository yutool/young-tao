package com.youngtao.core.type;

/**
 * @author ankoye@qq.com
 * @date 2021/07/01
 */
public class DelType extends IBaseType {

    private DelType(Integer code, String desc) {
        super(code, desc);
    }

    public static DelType DELETED = new DelType(0, "已删除");

    public static DelType NOT_DEL = new DelType(1, "未删除");
}
