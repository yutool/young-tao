package com.youngtao.omc.model.request;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@Data
public class AddCartRequest {
    private String skuId;

    private Integer num;
}
