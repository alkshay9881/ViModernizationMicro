package com.mobicule.vodafone.apigateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class EtopExtractionGlobalFilter implements GlobalFilter, Ordered {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getPath().value();

        if (!path.equals("/api/v1/auth/generateOTP")) {
            return chain.filter(exchange);
        }

        return ServerWebExchangeUtils.cacheRequestBody(exchange, (serverHttpRequest) ->

                ServerRequest.create(
                                exchange.mutate().request(serverHttpRequest).build(),
                                HandlerStrategies.withDefaults().messageReaders()
                        )
                        .bodyToMono(String.class)
                        .flatMap(body -> {

                            try {
                                JsonNode jsonNode = objectMapper.readTree(body);

                                String etop = jsonNode
                                        .path("user")
                                        .path("etop")
                                        .asText();

                                exchange.getAttributes().put("etopNo", etop);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
                        })
        );
    }

    @Override
    public int getOrder() {
        return -2;
    }
}