package com.mobicule.vodafone.loginService.syncMQ.service;

import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import com.mobicule.vodafone.loginService.syncMQ.config.MultiMQProperties;
import com.mobicule.vodafone.loginService.syncMQ.util.XMLUtil;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MQRequestReplyService {

    private final MultiMQProperties properties;

    public Object callMQ(String requestObj, String entity) throws Exception {

        long startTime = System.currentTimeMillis();
        var config = properties.getManagers().get(entity);

        log.info("MQ call started. entity={}", entity);

        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
        factory.setQueueManager(config.getQueueManager());
        factory.setChannel(config.getChannel());
        factory.setConnectionNameList(config.getConnName());
        factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);

        // 🔥 MUST HAVE
        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
       // System.setProperty("com.ibm.mq.ssl.trustAll", "true");
        System.setProperty("javax.net.ssl.trustStore", config.getTrustStore());
        //System.setProperty("javax.net.ssl.trustStorePassword", config.getTrustStorePassword());
        // SSL
        factory.setSSLCipherSuite(config.getSslCipherSuite());


        QueueConnection connection = null;
        QueueSession session = null;

        try {
            connection = factory.createQueueConnection();
            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue inputQueue = session.createQueue(config.getInputQueue());
            Queue outputQueue = session.createQueue(config.getOutputQueue());

            log.info("MQ configuration loaded. queueManager={}, channel={}",
                    config.getQueueManager(), config.getChannel());

            String xml =requestObj;
            log.info("Request XML: {}", xml);


            TextMessage message = session.createTextMessage(xml);
            message.setJMSReplyTo(outputQueue);

            QueueSender sender = session.createSender(inputQueue);
            sender.send(message);

            String correlationId = message.getJMSMessageID();
            log.info("Message sent. correlationId={}", correlationId);

            String selector = "JMSCorrelationID = '" + correlationId + "'";
            QueueReceiver receiver = session.createReceiver(outputQueue, selector);

            connection.start();

            Message response = receiver.receive(config.getTimeout());

            if (response == null) {
                throw new RuntimeException("MQ Timeout");
            }

            String responseXML = ((TextMessage) response).getText();
            log.info("Response XML: {}", responseXML);
            Class<?> responseClass = Class.forName(config.getResponseClass());
            Object finalResponse = XMLUtil.fromXML(responseXML,responseClass);

            long time = System.currentTimeMillis() - startTime;
            log.info("MQ call completed. timeTaken={} ms", time);

            return finalResponse;

        } finally {
            if (session != null) session.close();
            if (connection != null) connection.close();
        }
    }
}
