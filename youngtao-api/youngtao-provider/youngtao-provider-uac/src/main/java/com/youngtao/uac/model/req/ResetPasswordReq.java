package com.youngtao.uac.model.req;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/05/07
 */
@Data
public class ResetPasswordReq {
    private String email;
    private String verifyCode;
    private String password;
}
