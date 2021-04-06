package com.youngtao.gmc.api.model.arg;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ankoye@qq.com
 * @date 2020/12/13
 */
@Data
public class UpdateStockArg implements Serializable {

    private String skuId;

    private Integer num;
}
