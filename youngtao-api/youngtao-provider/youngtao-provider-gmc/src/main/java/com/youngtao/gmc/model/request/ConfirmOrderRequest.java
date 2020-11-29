package com.youngtao.gmc.model.request;

import lombok.Data;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/28
 */
@Data
public class ConfirmOrderRequest {
    private List<Data> skuList;

    @lombok.Data
    public static class Data {
        private String skuId;

        private Integer count;
    }
}
