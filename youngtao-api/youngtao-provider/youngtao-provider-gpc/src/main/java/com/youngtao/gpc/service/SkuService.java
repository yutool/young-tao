package com.youngtao.gpc.service;

import com.youngtao.gpc.model.domain.SkuDO;
import com.youngtao.gpc.model.req.AddOrUpdateSkuReq;
import com.youngtao.web.support.IService;

/**
 * @author ankoye@qq.com
 * @date 2021/04/18
 */
public interface SkuService extends IService<SkuDO> {
    /**
     * 新增或修改
     */
    void addOrUpdate(AddOrUpdateSkuReq request);

    /**
     * 删除数据
     */
    void deleteBySkuId(String skuId);
}
