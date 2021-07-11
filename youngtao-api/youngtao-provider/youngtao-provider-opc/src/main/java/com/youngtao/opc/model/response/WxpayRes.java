package com.youngtao.opc.model.response;

import java.util.HashMap;

/**
 * @author ankoye@qq.com
 * @date 2021/01/30
 */
public class WxpayRes extends HashMap<String, String> {

    public void setAppId(String appId) {
        this.put("appid", appId);
    }

    public void setPartnerId(String partnerId) {
        this.put("partnerid", partnerId);
    }

    public void setPrepayId(String prepayId) {
        this.put("prepayid", prepayId);
    }

    public void setTimestamp(String timestamp) {
        this.put("timestamp", timestamp);
    }

    public void setNoncestr(String noncestr) {
        this.put("noncestr", noncestr);
    }

    public void setPackage(String _package) {
        this.put("package", _package);
    }

    public void setSign(String sign) {
        this.put("sign", sign);
    }
}
