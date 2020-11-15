package com.ankoye.youngtao.gateway.app.util;

import com.ankoye.youngtao.core.constant.ContentType;
import com.ankoye.youngtao.gateway.app.model.LogData;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

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
//        log.info(toJsonString(logData));
        return Mono.empty();
}


    /**
     * 读取请求体内容
     * @param request ServerHttpRequest
     * @return 请求体
     */
    public static String readRequestBody(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        MediaType mediaType = headers.getContentType();
        String method = request.getMethodValue().toUpperCase();
        if (Objects.nonNull(mediaType) && mediaType.equals(MediaType.MULTIPART_FORM_DATA)) {
            return "上传文件";
        } else {
            if (method.equals("GET")) {
                if (!request.getQueryParams().isEmpty()) {
                    return request.getQueryParams().toString();
                }
                return null;
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