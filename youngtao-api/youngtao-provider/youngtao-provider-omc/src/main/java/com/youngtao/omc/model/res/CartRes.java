package com.youngtao.omc.model.res;

import com.google.common.collect.Lists;
import com.youngtao.omc.model.data.CartData;
import lombok.Data;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@Data
public class CartRes {
    private String merchantId;

    private String shopName;

    List<CartData> skuList = Lists.newArrayList();
}
