package com.youngtao.uac.service;

import com.youngtao.uac.model.domain.MerchantInfo;
import com.youngtao.uac.model.request.RegisterRequest;
import com.youngtao.uac.model.request.UpdateMerchantInfoRequest;
import com.youngtao.uac.model.request.UpdatePasswordRequest;
import com.youngtao.web.support.IService;

/**
 * @author ankoye@qq.com
 * @date 2021/04/17
 */
public interface MerchantService extends IService<MerchantInfo> {
    /**
     * 注册
     * @param request
     */
    void register(RegisterRequest request);

    /**
     * 修改信息
     * @param request
     */
    void updateInfo(UpdateMerchantInfoRequest request);

    /**
     * 修改密码
     * @param request
     */
    void updatePassword(UpdatePasswordRequest request);
}
