package com.youngtao.gmc.api.model.arg;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/04/06
 */
@Data
public class UpdateStockArg {
    private String skuId;

    private Integer num;
}
