package com.ankoye.youngtao.gmc.service.impl;

import com.ankoye.youngtao.core.common.BigDecimals;
import com.ankoye.youngtao.core.util.GlobalIdUtils;
import com.ankoye.youngtao.gmc.mapper.SkuMapper;
import com.ankoye.youngtao.gmc.mapper.SpuMapper;
import com.ankoye.youngtao.gmc.model.domain.SkuDO;
import com.ankoye.youngtao.gmc.model.domain.SpuDO;
import com.ankoye.youngtao.gmc.model.request.AddProductRequest;
import com.ankoye.youngtao.gmc.service.ProductService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private SkuMapper skuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addProduct(AddProductRequest request) {
        SpuDO spuDO = request.convertToSpu();
        String spuId = GlobalIdUtils.generateId();
        // fill sku
        List<SkuDO> skuDOList = request.convertToSkuList();
        BigDecimal minPrice = BigDecimals.MAX_INT_VALUE;
        BigDecimal maxPrice = BigDecimal.ZERO;
        for (SkuDO skuDO : skuDOList) {
            skuDO.setSpuId(spuId);
            minPrice = minPrice.min(skuDO.getPrice());
            maxPrice = maxPrice.max(skuDO.getPrice());
        }
        // fill spu
        spuDO.setSpuId(spuId);
        spuDO.setPriceRange(Lists.newArrayList(minPrice, maxPrice));
        // save the data
        spuMapper.insert(spuDO);
        skuMapper.batchInset(skuDOList);
        return true;
    }
}
