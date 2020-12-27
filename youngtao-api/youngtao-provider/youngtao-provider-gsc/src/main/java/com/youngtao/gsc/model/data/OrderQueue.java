package com.youngtao.gsc.model.data;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户排队抢单信息
 *
 * @author ankoye@qq.com
 */
@Data
public class OrderQueue implements Serializable {

    /** 排队中 */
    public static final int QUEUING = 1;
    /** 订单已创建 */
    public static final int CREATED = 2;
    /** 售罄 */
    public static final int SOLD_OUT =  3;
    /** 订单关闭 */
    public static final int CLOSE = 9;

    private String userId;

    private String skuId;

    private Integer num;

    private String menu;

    private Integer status;

    private String paymentId;

    private Date updateTime = new Date();

    private Date createTime = new Date();

}
