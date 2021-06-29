package com.youngtao.omc.model.request;

import com.youngtao.web.page.PageArg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GetMerchantOrderRequest extends PageArg {
    private Integer status;
}
