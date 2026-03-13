package com.mobicule.vodafone.apigateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobicule.vodafone.apigateway.util.CryptoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.*;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RequestDecryptionFilter implements GlobalFilter, Ordered {

    private final ObjectMapper objectMapper = new ObjectMapper();

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

        return ServerWebExchangeUtils.cacheRequestBody(exchange, serverHttpRequest ->

                ServerRequest.create(
                                exchange.mutate().request(serverHttpRequest).build(),
                                HandlerStrategies.withDefaults().messageReaders()
                        )
                        .bodyToMono(String.class)
                        .flatMap(body -> {

                            try {

                                JsonNode jsonNode = objectMapper.readTree(body);

                                String encryptedData =
                                        jsonNode.path("data").asText();

                                String decrypted =
                                        cryptoUtil.decrypt(encryptedData);

                               // log.info("DECRYPTED BODY: {}", decrypted);

                                byte[] newBody =
                                        decrypted.getBytes(StandardCharsets.UTF_8);

                                ServerHttpRequestDecorator mutatedRequest =
                                        new ServerHttpRequestDecorator(serverHttpRequest) {

                                            @Override
                                            public Flux<DataBuffer> getBody() {

                                                return Flux.defer(() -> {

                                                    DataBuffer buffer =
                                                            exchange.getResponse()
                                                                    .bufferFactory()
                                                                    .wrap(newBody);

                                                    return Mono.just(buffer);
                                                });
                                            }

                                            @Override
                                            public HttpHeaders getHeaders() {

                                                HttpHeaders headers = new HttpHeaders();
                                                headers.putAll(super.getHeaders());

                                                headers.remove(HttpHeaders.CONTENT_LENGTH);
                                                headers.setContentLength(newBody.length);

                                                return headers;
                                            }
                                        };

                                return chain.filter(
                                        exchange.mutate()
                                                .request(mutatedRequest)
                                                .build()
                                );

                            } catch (Exception e) {

                                log.error("Decryption error", e);
                                return chain.filter(exchange);
                            }
                        })
        );
    }

    @Override
    public int getOrder() {
        return -3;
    }
}