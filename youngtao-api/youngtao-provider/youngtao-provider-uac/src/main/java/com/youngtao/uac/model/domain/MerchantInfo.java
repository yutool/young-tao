package com.youngtao.uac.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youngtao.web.support.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 商家信息
 *
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("uac_merchant_info")
public class MerchantInfo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 428486670449283711L;

    /**
     * 商家ID
     */
    private String merchantId;

    /**
     * 店名
     */
    private String shopName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;
}