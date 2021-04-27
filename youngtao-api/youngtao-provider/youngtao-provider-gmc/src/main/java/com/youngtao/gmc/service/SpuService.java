package com.youngtao.gmc.service;

import com.youngtao.core.data.DeleteArg;
import com.youngtao.gmc.model.domain.SpuDO;
import com.youngtao.gmc.model.request.UpdateSpuRequest;
import com.youngtao.web.support.IService;

/**
 * @author ankoye@qq.com
 * @date 2021/04/18
 */
public interface SpuService extends IService<SpuDO> {

    /**
     * 更新spu
     */
    void update(UpdateSpuRequest request);

    /**
     * 删除spu
     * @param arg
     */
    void deleteBySpuId(DeleteArg arg);

    /**
     * 回收商品
     */
    void recycleBySpuId(String spuId);
}
