package com.youngtao.uac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.youngtao.core.context.AuthContext;
import com.youngtao.core.context.AuthInfo;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.core.util.IdUtils;
import com.youngtao.uac.mapper.ShippingAddressMapper;
import com.youngtao.uac.model.data.ShippingAddressData;
import com.youngtao.uac.model.domain.ShippingAddress;
import com.youngtao.uac.model.request.AddShippingAddressRequest;
import com.youngtao.uac.service.ShippingAddressService;
import com.youngtao.web.support.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateAddress(AddShippingAddressRequest request, String userId) {
        ShippingAddress address = BeanUtils.copy(request, ShippingAddress.class);
        if (address.getIsDefault()) {
            // 设置其他为false
            shippingAddressMapper.cancelDefault(userId);
        }
        if (StringUtils.isBlank(request.getShippingAddrId())) {
            // 添加
            address.setUserId(userId);
            address.setShippingAddrId(IdUtils.getId("s_a"));
            // 如果不是默认，判断是否有默认的，没有设置当前为默认
            if (!address.getIsDefault()) {
                int count = shippingAddressMapper.selectCount(new QueryWrapper<ShippingAddress>()
                        .eq("user_id", userId)
                        .eq("is_default", true)
                );
                address.setIsDefault(count == 0);
            }
            shippingAddressMapper.insert(address);
        } else {
            // 更新
            shippingAddressMapper.update(address, new UpdateWrapper<ShippingAddress>()
                    .eq("shipping_addr_id", request.getShippingAddrId())
            );
            // 如果不是默认，判断是否有默认的，没有设置一个
            if (!address.getIsDefault()) {
                int count = shippingAddressMapper.selectCount(new QueryWrapper<ShippingAddress>()
                        .eq("user_id", userId)
                        .eq("is_default", true)
                );
                if (count == 0) {
                    shippingAddressMapper.resetDefault(userId);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefault(String saId, String userId) {
        // 取消其他默认地址
        shippingAddressMapper.cancelDefault(userId);
        // 设置当前为默认地址
        shippingAddressMapper.setDefault(saId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByAddrId(String id) {
        // 删除
        shippingAddressMapper.delete(new UpdateWrapper<ShippingAddress>()
                .eq("shipping_addr_id", id)
        );
        // 如果默认的被删除，重新设置一个默认的
        AuthInfo authInfo = AuthContext.get();
        int count = shippingAddressMapper.selectCount(new QueryWrapper<ShippingAddress>()
                .eq("user_id", authInfo.getUserId())
                .eq("is_default", true)
        );
        if (count == 0) {
            shippingAddressMapper.resetDefault(authInfo.getUserId());
        }
    }
}
