package com.youngtao.web.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author ankoye@qq.com
 * @date 2021/01/09
 */
public class PageArg {
    @Min(value = 1, message = "page cannot be less than 1")
    protected Integer page;

    @Max(value = 100, message = "size cannot be more than 100")
    protected Integer size;

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
}
