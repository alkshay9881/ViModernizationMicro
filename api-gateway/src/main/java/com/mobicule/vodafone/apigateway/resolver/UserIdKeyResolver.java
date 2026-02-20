package com.mobicule.vodafone.apigateway.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component("userIdKeyResolver")
@Primary
public class UserIdKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {

        String path = exchange.getRequest().getPath().value();

        return exchange.getPrincipal()
                .cast(Authentication.class)
                .map(auth -> {
                    String user = auth.getName();
                    log.info(" RateLimiter KeyResolver | User: {} | Path: {}", user, path);
                    return user;
                })   // userId from JWT
                .defaultIfEmpty("anonymous");
    }
}
