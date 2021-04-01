package com.youngtao.uac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.uac.model.domain.ShippingAddress;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@Mapper
public interface ShippingAddressMapper extends BaseMapper<ShippingAddress> {

    /**
     * 新增数据
     *
     * @param shippingAddress 实例对象
     * @return 影响行数
     */
    int insert0(ShippingAddress shippingAddress);

}