package com.youngtao.omc.model.req;

import com.youngtao.web.page.PageArg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GetUserOrderReq extends PageArg {
    private Integer status;

    private boolean deleted = false;
}
