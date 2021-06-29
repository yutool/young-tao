package com.youngtao.uac.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家信息
 *
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@Data
@TableName("uac_merchant_info")
public class MerchantInfo implements Serializable {
    private static final long serialVersionUID = 428486670449283711L;

    /** 自增ID */
    @TableId(type = IdType.AUTO)
    private Long id;

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

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;
}