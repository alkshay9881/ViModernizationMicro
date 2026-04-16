package com.mobicule.vodafone.loginService.syncMQ.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MultiMQProperties.class)
public class MQConfig {
}
