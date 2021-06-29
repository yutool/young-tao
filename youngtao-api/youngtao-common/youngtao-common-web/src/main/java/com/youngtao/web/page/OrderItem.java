package com.youngtao.web.page;

/**
 * @author ankoye@qq.com
 * @date 2021/06/28
 */
public class OrderItem {
    private String field;

    private String sort;

    public static OrderItem asc(String field) {
        OrderItem item = new OrderItem();
        item.setField(field);
        item.setSort("asc");
        return item;
    }

    public static OrderItem desc(String field) {
        OrderItem item = new OrderItem();
        item.setField(field);
        item.setSort("desc");
        return item;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
