package com.youngtao.uac.service;

import com.youngtao.uac.model.data.ShippingAddressData;
import com.youngtao.uac.model.domain.ShippingAddress;
import com.youngtao.uac.model.request.AddShippingAddressRequest;
import com.youngtao.web.support.IService;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/04/02
 */
public interface ShippingAddressService extends IService<ShippingAddress> {
    /**
     * 获取用户收货地址
     * @param userId
     * @return
     */
    List<ShippingAddressData> getByUserId(String userId);

    /**
     * 添加收货地址
     */
    void addOrUpdateAddress(AddShippingAddressRequest request, String userId);

    /**
     * 设置为默认地址
     * @param saId
     * @param userId
     */
    void setDefault(String saId, String userId);

    /**
     * 删除收货地址
     */
    void deleteByAddrId(String id);
}
