package com.ankoye.youngtao.gateway.app.model;

/**
 * @author ankoye@qq.com
 * @date 2020/11/15
 */
public class LogData {

    private String url;

    private String params;

    private String method;

    private String body;

    private String response;

    private String ip;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "{url: " + url +
                ", method: " + method +
                ", params: " + params +
                ", body: " + body +
                ", ip: " + ip + '}' + '\n' +
                "response: "  + response;
    }
}
