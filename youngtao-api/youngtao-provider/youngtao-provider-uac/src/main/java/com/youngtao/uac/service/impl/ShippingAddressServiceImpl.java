package com.youngtao.uac.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.core.util.IdUtils;
import com.youngtao.uac.mapper.ShippingAddressMapper;
import com.youngtao.uac.model.data.ShippingAddressData;
import com.youngtao.uac.model.domain.ShippingAddress;
import com.youngtao.uac.model.request.AddShippingAddressRequest;
import com.youngtao.uac.service.ShippingAddressService;
import com.youngtao.web.support.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/04/02
 */
@Service
public class ShippingAddressServiceImpl extends BaseService<ShippingAddress> implements ShippingAddressService {

    @Resource
    private ShippingAddressMapper shippingAddressMapper;

    @Override
    public List<ShippingAddressData> getByUserId(String userId) {
        List<ShippingAddress> addressList = shippingAddressMapper.selectByUserId(userId);
        return BeanUtils.copyList(addressList, ShippingAddressData.class);
    }

    @Override
    public void addAddress(AddShippingAddressRequest request, String userId) {
        ShippingAddress address = BeanUtils.copy(request, ShippingAddress.class);
        address.setUserId(userId);
        address.setShippingAddrId(IdUtils.getId("s_a"));
        shippingAddressMapper.insert(address);
    }

    @Override
    public void deleteByAddrId(String id) {
        shippingAddressMapper.delete(new UpdateWrapper<ShippingAddress>()
                .eq("shipping_addr_id", id)
        );
    }
}
