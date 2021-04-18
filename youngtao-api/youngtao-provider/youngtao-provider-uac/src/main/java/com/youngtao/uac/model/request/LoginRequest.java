package com.youngtao.uac.model.request;

import com.youngtao.core.context.AuthType;
import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@Data
public class LoginRequest {
    private String account;

    private String password;

    private Integer type = AuthType.USER;
}
