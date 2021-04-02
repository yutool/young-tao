package com.youngtao.uac.model.request;

import lombok.Data;

import java.util.Date;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
@Data
public class UpdateUserInfoRequest {

    private String username;

    private String gender;

    private Date birthday;

}
