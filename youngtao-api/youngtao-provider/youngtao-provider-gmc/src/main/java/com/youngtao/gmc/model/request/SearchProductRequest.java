package com.youngtao.gmc.model.request;

import com.youngtao.web.page.PageArg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankoye@qq.com
 * @date 2021/04/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchProductRequest extends PageArg {
    private Long category;

    private String searchValue;
}
