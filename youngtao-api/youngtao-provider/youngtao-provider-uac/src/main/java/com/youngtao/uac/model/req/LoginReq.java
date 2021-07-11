package com.youngtao.uac.model.req;

import com.youngtao.core.context.AuthType;
import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@Data
public class LoginReq {
    private String account;

    private String password;

    private Integer type = AuthType.USER;
}
