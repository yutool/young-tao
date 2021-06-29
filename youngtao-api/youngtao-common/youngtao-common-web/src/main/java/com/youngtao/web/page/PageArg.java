package com.youngtao.web.page;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/01/09
 */
public class PageArg {
    @Min(value = 1, message = "page cannot be less than 1")
    protected Integer page;

    @Max(value = 100, message = "size cannot be more than 100")
    protected Integer size;

    protected List<OrderItem> orders;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }
}
