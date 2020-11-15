package com.ankoye.youngtao.gateway.app.model;

/**
 * @author ankoye@qq.com
 * @date 2020/11/15
 */
public class LogData {

    private String uri;

    private String method;

    private String body;

    private String response;

    private String ip;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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
        return "{ uri: " + uri +
                ", method: " + method +
                ", body: " + body +
                ", response: " + response +
                ", ip: " + ip  + '}';
    }
}
