package com.youngtao.omc.model.req;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@Data
public class AddCartReq {
    private String skuId;

    private Integer num;
}
