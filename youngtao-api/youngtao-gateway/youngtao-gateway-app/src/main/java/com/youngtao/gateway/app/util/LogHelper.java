package com.youngtao.gateway.app.util;

import com.youngtao.core.constant.ContentType;
import com.youngtao.core.util.JsonUtils;
import com.youngtao.gateway.app.model.LogData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ankoye@qq.com
 * @date 2020/11/15
 */
public class LogHelper {

    private final static Map<String , String> FILE_CONTENT_TYPE = ContentType.getAllFile();

    private static final Logger log = LoggerFactory.getLogger(LogHelper.class);

    /**
     * 根据MediaType获取字符集，如果获取不到，则默认返回<tt>UTF_8</tt>
     * @param mediaType MediaType
     * @return Charset
     */
    public static Charset getMediaTypeCharset(MediaType mediaType) {
        if (Objects.nonNull(mediaType) && mediaType.getCharset() != null) {
            return mediaType.getCharset();
        } else {
            return StandardCharsets.UTF_8;
        }
    }

    /**
     * 记录日志（后期可扩展为通过MQ将日志发送到ELK系统）
     * @param logData Log
     * @return Mono.empty()
     */
    public static Mono<Void> doRecord(LogData logData) {
         log.info(JsonUtils.toJson(logData));
        return Mono.empty();
    }


    /**
     * 获取url
     * @param request
     * @return url
     */
    public static String getUrl(ServerHttpRequest request) {
        URI uri = request.getURI();
        return uri.getAuthority() + uri.getPath();
    }

    /**
     * 获取客户端ip
     * @param request request
     * @return ip
     */
    public static String getClientIp(ServerHttpRequest request) {
        String result = null;
        HttpHeaders headers = request.getHeaders();
        if (headers.getFirst("X-Forwarded-For") != null) {
            result = headers.getFirst("X-Forwarded-For");
        } else if (headers.getFirst("Proxy-Client-IP") != null) {
            result = headers.getFirst("Proxy-Client-IP");
        } else if (headers.getFirst("WL-Proxy-Client-IP") != null) {
            result = headers.getFirst("WL-Proxy-Client-IP");
        } else if (headers.getFirst("HTTP_CLIENT_IP") != null) {
            result = headers.getFirst("HTTP_CLIENT_IP");
        } else if (headers.getFirst("X-Real-IP") != null) {
            result = headers.getFirst("X-Real-IP");
        }
        if (StringUtils.isBlank(result) || "unknown".equalsIgnoreCase(result)) {
            result = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        }
        return result;
    }

    public static String getRequestParams(ServerHttpRequest request) {
        if (!request.getQueryParams().isEmpty()) {
            return request.getQueryParams().toString();
        }
        return null;
    }

    /**
     * 读取请求体内容
     * @param request ServerHttpRequest
     * @return 请求体
     */
    public static String getRequestBody(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        MediaType mediaType = headers.getContentType();
        if (Objects.nonNull(mediaType) && mediaType.equals(MediaType.MULTIPART_FORM_DATA)) {
            return "upload file";
        } else {
            AtomicReference<String> bodyString = new AtomicReference<>();
            request.getBody().subscribe(buffer -> {
                byte[] bytes = new byte[buffer.readableByteCount()];
                buffer.read(bytes);
                DataBufferUtils.release(buffer);
                bodyString.set(new String(bytes, getMediaTypeCharset(mediaType)));
            });
            return bodyString.get();
        }
    }

    /**
     * 判断是否是上传文件
     * @param mediaType MediaType
     * @return Boolean
     */
    public static boolean isUploadFile(MediaType mediaType) {
        if (Objects.isNull(mediaType)) {
            return false;
        }
        String mediaTypeStr = mediaType.toString();
        // 处理类似multipart/form-data; boundary=<calculated when request is sent>的情况
        mediaTypeStr = mediaTypeStr.split(";")[0];
        return mediaTypeStr.equals(MediaType.MULTIPART_FORM_DATA.toString())
                || mediaTypeStr.equals(MediaType.IMAGE_GIF.toString())
                || mediaTypeStr.equals(MediaType.IMAGE_JPEG.toString())
                || mediaTypeStr.equals(MediaType.IMAGE_PNG.toString())
                || mediaTypeStr.equals(MediaType.MULTIPART_MIXED.toString())
                || FILE_CONTENT_TYPE.containsValue(mediaTypeStr);
    }
}