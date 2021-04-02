package com.youngtao.uac.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表(UacUserInfo)实体类
 *
 * @author makejava
 * @since 2021-03-30 15:11:39
 */
@Data
@TableName("uac_user_info")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 855121525128743022L;

    private Long id;

    private String userId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

}