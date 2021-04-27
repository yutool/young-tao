package com.youngtao.omc.model.request;

import com.youngtao.web.model.PageArg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GetUserOrderRequest extends PageArg {
    private Integer status;

    private boolean deleted = false;
}
