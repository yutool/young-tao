package com.youngtao.gmc.service;

import com.youngtao.gmc.model.domain.SkuDO;
import com.youngtao.gmc.model.req.UpdateSkuReq;
import com.youngtao.web.support.IService;

/**
 * @author ankoye@qq.com
 * @date 2021/04/18
 */
public interface SkuService extends IService<SkuDO> {

    /**
     * 更新sku
     */
    void update(UpdateSkuReq request);

    /**
     * 删除sku
     * @param skuId skuId
     */
    void deleteBySkuId(String skuId);
}
