package com.youngtao.gsc.model.request;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2020/12/27
 */
@Data
public class CreateOrderRequest {
    private String menu;

    private String skuId;

    private Integer num;

}
