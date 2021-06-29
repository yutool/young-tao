package com.youngtao.uac.model.request;

import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/05/07
 */
@Data
public class ResetPasswordRequest {
    private String email;
    private String verifyCode;
    private String password;
}