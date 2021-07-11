package com.youngtao.uac.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.uac.model.domain.ShippingAddressDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@Mapper
public interface ShippingAddressMapper extends BaseMapper<ShippingAddressDO> {

    /**
     * 新增数据
     *
     * @param shippingAddressDO 实例对象
     * @return 影响行数
     */
    int insert0(ShippingAddressDO shippingAddressDO);

    List<ShippingAddressDO> selectByUserId(String userId);

    int cancelDefault(String userId);

    int setDefault(String saId);

    /**
     * 设置一条默认的
     */
    int resetDefault(String userId);
}