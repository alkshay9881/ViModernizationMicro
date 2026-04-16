package com.mobicule.vodafone.loginService.syncMQ.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@ConfigurationProperties(prefix = "mq")
@Data
public class MultiMQProperties {

    private Map<String, MQManager> managers;

    @Data
    public static class MQManager {
        private String queueManager;
        private String channel;
        private String connName;
        private String inputQueue;
        private String outputQueue;
        private int timeout;
        private String sslCipherSuite;
        private String trustStore;
        private String trustStorePassword;

        public String responseClass;
    }
}
