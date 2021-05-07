package com.youngtao.uac.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.youngtao.core.context.AuthContext;
import com.youngtao.core.exception.CastException;
import com.youngtao.core.util.IdUtils;
import com.youngtao.uac.common.constant.RedisKey;
import com.youngtao.uac.mapper.MerchantInfoMapper;
import com.youngtao.uac.model.domain.MerchantInfo;
import com.youngtao.uac.model.request.RegisterRequest;
import com.youngtao.uac.model.request.UpdateMerchantInfoRequest;
import com.youngtao.uac.model.request.UpdatePasswordRequest;
import com.youngtao.uac.service.MerchantService;
import com.youngtao.web.cache.RedisManager;
import com.youngtao.web.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author ankoye@qq.com
 * @date 2021/04/17
 */
@Service
public class MerchantServiceImpl extends BaseService<MerchantInfo> implements MerchantService {

    @Autowired
    private RedisManager<String> redisManager;
    @Resource
    private MerchantInfoMapper merchantInfoMapper;

    @Override
    public void register(RegisterRequest request) {
        String code = redisManager.get(RedisKey.REGISTER_MER_CODE.format(request.getEmail()));
        if (!Objects.equals(code, request.getVerifyCode())) {
            CastException.cast("验证码错误");
        }
        MerchantInfo merchant = new MerchantInfo();
        merchant.setShopName(request.getName());
        merchant.setEmail(request.getEmail());
        merchant.setPassword(request.getPassword());
        merchant.setMerchantId(IdUtils.getId("shop"));
        merchant.setAvatar("https://avatars.githubusercontent.com/u/56569932?v=4");
        merchantInfoMapper.insert(merchant);
    }

    @Override
    public void updateInfo(UpdateMerchantInfoRequest request) {
        MerchantInfo merchantInfo = new MerchantInfo(){{
           setShopName(request.getShopName());
        }};
        merchantInfoMapper.update(merchantInfo, new UpdateWrapper<MerchantInfo>()
            .eq("merchant_id", AuthContext.get().getMerchantId())
        );
        // 更新所有被 冗余的表
    }

    @Override
    public void updatePassword(UpdatePasswordRequest request) {
        MerchantInfo merchantInfo = new MerchantInfo(){{
            setPassword(request.getPassword());
        }};
        merchantInfoMapper.update(merchantInfo, new UpdateWrapper<MerchantInfo>()
                .eq("merchant_id", AuthContext.get().getMerchantId())
        );
    }
}
