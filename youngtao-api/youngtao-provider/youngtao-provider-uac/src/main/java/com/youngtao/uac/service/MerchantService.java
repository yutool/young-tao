package com.youngtao.uac.service;

import com.youngtao.uac.model.domain.MerchantInfoDO;
import com.youngtao.uac.model.req.RegisterReq;
import com.youngtao.uac.model.req.UpdateMerchantInfoReq;
import com.youngtao.uac.model.req.UpdatePasswordReq;
import com.youngtao.web.support.IService;

/**
 * @author ankoye@qq.com
 * @date 2021/04/17
 */
public interface MerchantService extends IService<MerchantInfoDO> {
    /**
     * 注册
     * @param request
     */
    void register(RegisterReq request);

    /**
     * 修改信息
     * @param request
     */
    void updateInfo(UpdateMerchantInfoReq request);

    /**
     * 修改密码
     * @param request
     */
    void updatePassword(UpdatePasswordReq request);
}
