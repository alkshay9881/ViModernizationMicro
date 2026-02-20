package com.mobicule.vodafone.apigateway.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component("mobileKeyResolver")
@Slf4j
public class MobileKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {

        String mobile = exchange.getAttribute("etopNo");

        log.info("etop - "+mobile);

        if (mobile == null || mobile.isEmpty()) {
            log.info("etop null  - "+mobile);
            return Mono.just("unknown-mobile");
        }

        return Mono.just(mobile);
    }
}
