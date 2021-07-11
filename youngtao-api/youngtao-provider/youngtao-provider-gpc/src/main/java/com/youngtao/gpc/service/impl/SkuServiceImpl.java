package com.youngtao.gpc.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.youngtao.core.exception.CastException;
import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.RpcUtils;
import com.youngtao.gmc.api.model.dto.SpuSkuDTO;
import com.youngtao.gmc.api.service.ProductFeign;
import com.youngtao.gmc.api.service.SkuFeign;
import com.youngtao.gpc.common.util.DateUtils;
import com.youngtao.gpc.mapper.SkuMapper;
import com.youngtao.gpc.model.domain.SkuDO;
import com.youngtao.gpc.model.req.AddOrUpdateSkuReq;
import com.youngtao.gpc.service.SkuService;
import com.youngtao.web.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author ankoye@qq.com
 * @date 2021/04/18
 */
@Service
public class SkuServiceImpl extends BaseService<SkuDO> implements SkuService {

    @Resource
    private SkuMapper skuMapper;
    @Autowired
    private ProductFeign productFeign;
    @Autowired
    private SkuFeign skuFeign;

    @Override
    public void addOrUpdate(AddOrUpdateSkuReq request) {
        RpcResult<SpuSkuDTO> spuSkuResult = skuFeign.getSpuSku(request.getSkuId());
        RpcUtils.checkNotNull(spuSkuResult);
        SpuSkuDTO spuSku = spuSkuResult.getData();
        if (request.getNum() > spuSku.getNum()) {
            CastException.cast("活动数量不能大于总库存");
        }
        SkuDO skuDO = skuMapper.getBySkuId(request.getSkuId());
        Date startTime = DateUtils.plusHours(request.getDate(), request.getHours());
        if (skuDO != null) {
            skuDO.setPrice(request.getPrice());
            skuDO.setStartTime(startTime);
            skuDO.setEndTime(DateUtils.plusHours(startTime, 2));
            skuDO.setNum(request.getNum());
            skuDO.setResidue(request.getNum());
            skuMapper.updateById(skuDO);
        } else {
            skuDO = new SkuDO();
            skuDO.setSkuId(spuSku.getSkuId());
            skuDO.setSpuId(spuSku.getSpuId());
            skuDO.setMerchantId(spuSku.getMerchantId());
            skuDO.setSku(spuSku.getSku());
            skuDO.setImage(spuSku.getImages().get(0));
            skuDO.setPrice(request.getPrice());
            skuDO.setOldPrice(spuSku.getPrice());
            skuDO.setStartTime(startTime);
            skuDO.setEndTime(DateUtils.plusHours(startTime, 2));
            skuDO.setNum(request.getNum());
            skuDO.setResidue(request.getNum());
            skuDO.setIsMarketable(true);
            skuDO.setStatus(1);
            skuDO.setCheckTime(new Date());
            skuMapper.insert(skuDO);
        }
    }

    @Override
    public void deleteBySkuId(String skuId) {
        skuMapper.delete(new UpdateWrapper<SkuDO>()
                .eq("sku_id", skuId)
        );
    }
}