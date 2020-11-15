package com.ankoye.youngtao.gateway.app.filter;

import com.ankoye.youngtao.gateway.app.model.LogData;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ankoye@qq.com
 * @date 2020/11/15
 */
@Order(-1)
@Component
public class RequestLogFilter implements GlobalFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestLogFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        RecorderServerHttpRequestDecorator requestDecorator = new RecorderServerHttpRequestDecorator(request);

        Flux<DataBuffer> body = requestDecorator.getBody();

        // 读取requestBody传参
        AtomicReference<String> requestBody = new AtomicReference<>("");
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            requestBody.set(charBuffer.toString());
        });


        LogData logData = new LogData();
        logData.setUri(requestDecorator.getURI().toString());
        logData.setMethod(requestDecorator.getMethod().toString());
        logData.setBody(requestBody.get());
        logData.setIp(getClientIp(requestDecorator));


        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        // probably should reuse buffers
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        String responseResult = new String(content, StandardCharsets.UTF_8);
                        logData.setResponse(responseResult);
                        log.info("app gateway request info: {}", logData);
                        return bufferFactory.wrap(content);
                    }));
                }
                // if body is not a flux. never got there.
                return super.writeWith(body);
            }
        };

        return chain.filter(exchange.mutate().request(requestDecorator).response(decoratedResponse).build());
    }

    private String getClientIp(ServerHttpRequestDecorator request) {
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

}