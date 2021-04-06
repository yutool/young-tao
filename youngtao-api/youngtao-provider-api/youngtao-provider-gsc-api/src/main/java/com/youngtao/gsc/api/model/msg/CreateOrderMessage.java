package com.youngtao.gsc.api.model.msg;

import com.youngtao.gsc.api.model.dto.GscSkuDTO;
import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/03/20
 */
@Data
public class CreateOrderMessage {
    private String skuId;

    private String shippingAddressId;

    private String remark;

    private String userId;

    private String paymentId;

    private GscSkuDTO skuDTO;
}
