package com.youngtao.core.context;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
public class AuthContext {

    private static ThreadLocal<AuthInfo> tl = new ThreadLocal<>();

    public static void set(AuthInfo authInfo) {
        tl.set(authInfo);
    }

    public static AuthInfo get() {
        return tl.get();
    }

    public static void clear() {
        tl.remove();
    }

}
