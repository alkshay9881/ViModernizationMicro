package com.mobicule.vodafone.loginService.syncMQ.config;

import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.MessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AsyncListenerConfig {

    private final MultiMQProperties properties;
    private final Map<String, MessageListener> listeners;

    @Bean
    public List<DefaultMessageListenerContainer> asyncContainers() {

        List<DefaultMessageListenerContainer> containers = new ArrayList<>();

        if (properties.getManagers() == null || properties.getManagers().isEmpty()) {
            log.warn("No MQ managers found in configuration");
            return containers;
        }

        log.info("Initializing async MQ listeners");

        properties.getManagers().forEach((name, config) -> {

            try {
                // Skip non-async configs
                if (!"async".equalsIgnoreCase(config.getType())) {
                    log.debug("Skipping sync MQ config: {}", name);
                    return;
                }

                if (config.getEnabled() != null && !config.getEnabled()) {
                    log.info("Listener disabled for {}", name);
                    return;
                }

                log.info("Setting up listener for {}", name);

                MessageListener listener = listeners.get(name);

                if (listener == null) {
                    log.error("No listener bean found for {}", name);
                    return;
                }

                DefaultMessageListenerContainer container =
                        new DefaultMessageListenerContainer();

                container.setConnectionFactory(createFactory(config));
                container.setDestinationName(config.getOutputQueue());
                container.setMessageListener(listener);

                container.setConcurrentConsumers(5);
                container.setMaxConcurrentConsumers(20);

                container.setBeanName("Listener-" + name);


                container.setErrorHandler(t ->
                        log.error("Error while processing message in listener {}", name, t)
                );

                container.initialize();
                container.start();

                containers.add(container);

                log.info("Listener started for {} on queue {}", name, config.getOutputQueue());

            } catch (Exception ex) {
                log.error("Failed to initialize listener for {}", name, ex);
            }
        });

        log.info("Total async listeners started: {}", containers.size());

        return containers;
    }

    private ConnectionFactory createFactory(MultiMQProperties.MQManager config) {

        try {
            MQQueueConnectionFactory factory = new MQQueueConnectionFactory();

            factory.setQueueManager(config.getQueueManager());
            factory.setChannel(config.getChannel());
            factory.setConnectionNameList(config.getConnName());
            factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);

            //  SSL CONFIGURATION
            if (config.getSslCipherSuite() != null) {
                factory.setSSLCipherSuite(config.getSslCipherSuite());
                log.info("Using SSL Cipher Suite: {}", config.getSslCipherSuite());
            }

            if (config.getTrustStore() != null) {
                System.setProperty("javax.net.ssl.trustStore", config.getTrustStore());

                System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
                log.info("TrustStore configured: {}", config.getTrustStore());
            }

            return new SingleConnectionFactory(factory);

        } catch (Exception e) {
            log.error("Error creating MQ connection factory", e);
            throw new RuntimeException(e);
        }
    }
}