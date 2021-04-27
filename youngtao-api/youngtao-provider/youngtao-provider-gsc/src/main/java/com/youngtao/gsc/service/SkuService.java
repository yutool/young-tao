package com.youngtao.gsc.service;

import com.youngtao.gsc.model.domain.SkuDO;
import com.youngtao.gsc.model.request.AddOrUpdateSkuRequest;
import com.youngtao.web.support.IService;

/**
 * @author ankoye@qq.com
 * @date 2021/04/18
 */
public interface SkuService extends IService<SkuDO> {
    /**
     * 新增或修改
     */
    void addOrUpdate(AddOrUpdateSkuRequest request);

    /**
     * 删除数据
     */
    void deleteBySkuId(String skuId);
}
