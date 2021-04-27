package com.youngtao.gmc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.gmc.mapper.SkuMapper;
import com.youngtao.gmc.model.domain.SkuDO;
import com.youngtao.gmc.model.request.UpdateSkuRequest;
import com.youngtao.gmc.service.SkuService;
import com.youngtao.web.support.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ankoye@qq.com
 * @date 2021/04/18
 */
@Service
public class SkuServiceImpl extends BaseService<SkuDO> implements SkuService {

    @Resource
    private SkuMapper skuMapper;

    @Override
    public void update(UpdateSkuRequest request) {
        SkuDO skuDO = BeanUtils.copy(request, SkuDO.class);
        skuMapper.update(skuDO, new QueryWrapper<SkuDO>()
                .eq("sku_id", request.getSkuId())
        );
    }

    @Override
    public void deleteBySkuId(String skuId) {
        skuMapper.delete(new QueryWrapper<SkuDO>()
                .eq("sku_id", skuId)
        );
        // 删除活动商品
    }
}
