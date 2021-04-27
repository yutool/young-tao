package com.youngtao.core.data;

/**
 * @author ankoye@qq.com
 * @date 2021/03/20
 */
public class DeleteArg {
    private String id;

    private boolean logic = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLogic() {
        return logic;
    }

    public void setLogic(boolean logic) {
        this.logic = logic;
    }
}
