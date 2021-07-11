package com.youngtao.uac.model.req;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@Data
public class RegisterReq {
    private String name;

    private String email;

    private String password;

    private String verifyCode;
}
