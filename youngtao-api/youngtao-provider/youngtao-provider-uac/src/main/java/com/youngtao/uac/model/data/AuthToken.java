package com.youngtao.uac.model.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@Data
public class AuthToken implements Serializable {

    String accessToken;

    String refreshToken;

    /** jwt短令牌 */
    String jti;
}