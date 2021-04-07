package com.youngtao.omc.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.omc.model.domain.CartDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@Mapper
public interface CartMapper extends BaseMapper<CartDO> {

    /**
     * 新增数据
     *
     * @param cartDO 实例对象
     * @return 影响行数
     */
    int insert0(CartDO cartDO);

    CartDO selectByUserIdAndSkuId(@Param("userId") String userId, @Param("skuId") String skuId);

    List<CartDO> listByUserId(String userId);

    /**
     * 批量删除购物车
     */
    int batchDelete(@Param("userId") String userId, @Param("skuIds") List<String> skuIds);

    int deleteBySkuId(@Param("userId") String userId, @Param("skuId") String skuId);

    int updateNum(@Param("id") String id, @Param("num") String num);
}