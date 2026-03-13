package com.mobicule.vodafone.apigateway.filter;

import com.mobicule.vodafone.apigateway.util.CryptoUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.*;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.HttpHeaders;
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
    private CryptoUtil cryptoUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String encryptedHeader =
                exchange.getRequest().getHeaders().getFirst("X-Encrypted");

        boolean isEncrypted = "true".equalsIgnoreCase(encryptedHeader);

        if (!isEncrypted) {
            return chain.filter(exchange);
        }

        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();

        ServerHttpResponseDecorator decoratedResponse =
                new ServerHttpResponseDecorator(originalResponse) {

                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                        if (!(body instanceof Flux)) {
                            return super.writeWith(body);
                        }

                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);

                        return DataBufferUtils.join(fluxBody)
                                .flatMap(dataBuffer -> {

                                    byte[] content = new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(content);

                                    DataBufferUtils.release(dataBuffer);

                                    String responseBody =
                                            new String(content, StandardCharsets.UTF_8);

                               //     log.info("Original Response: {}", responseBody);

                                    String encrypted = cryptoUtil.encrypt(responseBody);

                                    String finalResponse =
                                            "{\"data\":\"" + encrypted + "\"}";

                                    byte[] newBytes =
                                            finalResponse.getBytes(StandardCharsets.UTF_8);

                                    originalResponse.getHeaders()
                                            .setContentLength(newBytes.length);

                                    DataBuffer newBuffer =
                                            bufferFactory.wrap(newBytes);

                                    return super.writeWith(Mono.just(newBuffer));
                                });
                    }
                };

        return chain.filter(
                exchange.mutate().response(decoratedResponse).build()
        );
    }

    @Override
    public int getOrder() {
        return 2;
    }
}