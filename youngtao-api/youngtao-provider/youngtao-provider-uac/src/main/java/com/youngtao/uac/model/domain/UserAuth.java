package com.youngtao.uac.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youngtao.core.lang.JsonList;
import com.youngtao.web.typehandler.JsonListTypeHandler;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户账户
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@Data
@TableName("uac_user_auth")
public class UserAuth implements Serializable {
    private static final long serialVersionUID = -36764034927975685L;

    private Long id;

    /**
     * 用户id
     */
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
     * 密码
     */
    private String password;

    /**
     * 角色
     */
    @TableField(typeHandler = JsonListTypeHandler.class)
    private JsonList<String> role;

    /**
     * 账号状态
     */
    private Integer status;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

}