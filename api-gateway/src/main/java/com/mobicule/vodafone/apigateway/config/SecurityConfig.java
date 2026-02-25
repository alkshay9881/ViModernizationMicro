package com.mobicule.vodafone.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${jwt.enabled:true}")
    private boolean jwtEnabled;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {

        http.csrf(csrf -> csrf.disable());

        if (jwtEnabled) {
            // JWT Enabled
            return http
                    .authorizeExchange(exchanges -> exchanges
                            .pathMatchers("/eureka/**").permitAll()
                            .pathMatchers("/api/v1/auth/generateOTP").permitAll()
                            .pathMatchers("/api/v1/auth/refreshToken").permitAll()
                            .anyExchange().authenticated()
                    )
                    .oauth2ResourceServer(oauth2 ->
                            oauth2.jwt(Customizer.withDefaults())
                    )
                    .build();
        } else {
            // JWT Disabled: all APIs open
            return http
                    .authorizeExchange(exchanges -> exchanges
                            .anyExchange().permitAll()
                    )
                    .build();
        }
    }
}