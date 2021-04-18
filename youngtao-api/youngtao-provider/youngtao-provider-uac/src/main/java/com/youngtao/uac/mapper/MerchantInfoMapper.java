package com.youngtao.uac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.uac.model.domain.MerchantInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author makejava
 *
 * @date 2021-04-17 10:26:06
 */
@Mapper
public interface MerchantInfoMapper extends BaseMapper<MerchantInfo> {

    /**
     * 新增数据
     *
     * @param merchantInfo 实例对象
     * @return 影响行数
     */
    int insert0(MerchantInfo merchantInfo);

    MerchantInfo login(@Param("account") String account, @Param("password") String password);
}