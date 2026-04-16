package com.mobicule.vodafone.apigateway.filter;

import com.mobicule.vodafone.apigateway.util.EncryptDecryptService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.*;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.*;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class ResponseEncryptionFilter implements GlobalFilter, Ordered {

    @Autowired
    private EncryptDecryptService encryptDecryptService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        boolean isEncrypted = "true".equalsIgnoreCase(
                exchange.getRequest().getHeaders().getFirst("X-Encrypted")
        );

        if (!isEncrypted) {
            return chain.filter(exchange);
        }

        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();

        ServerHttpResponseDecorator decoratedResponse =
                new ServerHttpResponseDecorator(originalResponse) {

                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                        return DataBufferUtils.join(body)
                                .flatMap(dataBuffer -> {

                                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(bytes);
                                    DataBufferUtils.release(dataBuffer);

                                    String responseBody =
                                            new String(bytes, StandardCharsets.UTF_8);

                                    log.info("Original Response: {}", responseBody);

                                    String encrypted;
                                    try {
                                        encrypted = encryptDecryptService.encryption(responseBody);
                                    } catch (Exception e) {
                                        log.error("Encryption failed", e);
                                        return Mono.error(e);
                                    }

                                    String finalResponse =
                                            "{\"data\":\"" + encrypted + "\"}";

                                    byte[] finalBytes =
                                            finalResponse.getBytes(StandardCharsets.UTF_8);

                                    getDelegate().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                                    getDelegate().getHeaders().setContentLength(finalBytes.length);

                                    DataBuffer buffer = bufferFactory.wrap(finalBytes);

                                    return Mono.just(buffer)
                                            .flatMap(b -> super.writeWith(Mono.just(b)));
                                });
                    }
                };

        return chain.filter(exchange.mutate()
                .response(decoratedResponse)
                .build());
    }

    @Override
    public int getOrder() {
        return 2; // VERY IMPORTANT: run before response commit
    }
}