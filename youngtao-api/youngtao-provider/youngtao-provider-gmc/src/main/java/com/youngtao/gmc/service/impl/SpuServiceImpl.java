package com.youngtao.gmc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.youngtao.core.data.DeleteArg;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.gmc.mapper.SkuMapper;
import com.youngtao.gmc.mapper.SpuMapper;
import com.youngtao.gmc.model.domain.SkuDO;
import com.youngtao.gmc.model.domain.SpuDO;
import com.youngtao.gmc.model.request.UpdateSpuRequest;
import com.youngtao.gmc.service.SpuService;
import com.youngtao.web.support.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author ankoye@qq.com
 * @date 2021/04/18
 */
@Service
public class SpuServiceImpl extends BaseService<SpuDO> implements SpuService {
    @Resource
    private SpuMapper spuMapper;
    @Resource
    private SkuMapper skuMapper;

    @Override
    public void update(UpdateSpuRequest request) {
        SpuDO spuDO = BeanUtils.copy(request, SpuDO.class);
        spuMapper.update(spuDO, new QueryWrapper<SpuDO>()
                .eq("spu_id", request.getSpuId())
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBySpuId(DeleteArg arg) {
        if (arg.isLogic()) {
            spuMapper.logicDelete(arg.getId());
            return;
        }
        spuMapper.delete(new UpdateWrapper<SpuDO>()
                .eq("spu_id", arg.getId())
        );
        skuMapper.delete(new UpdateWrapper<SkuDO>()
                .eq("spu_id", arg.getId())
        );

        // 删除活动商品
    }

    @Override
    public void recycleBySpuId(String spuId) {
        spuMapper.recycleBySpuId(spuId);
    }
}
