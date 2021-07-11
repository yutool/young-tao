package com.youngtao.uac.model.req;

import lombok.Data;

import java.util.Date;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
@Data
public class UpdateUserInfoReq {

    private String username;

    private String gender;

    private Date birthday;

}
