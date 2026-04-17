package com.mobicule.vodafone.loginService.syncMQ.service;

import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import com.mobicule.vodafone.loginService.exceptions.MQException;
import com.mobicule.vodafone.loginService.syncMQ.config.MultiMQProperties;
import com.mobicule.vodafone.loginService.syncMQ.util.XMLUtil;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class XMLmQRequestReplyService {

    private final MultiMQProperties properties;

    public Object callMQ(Object requestObj, String entity) {

        long startTime = System.currentTimeMillis();

        MultiMQProperties.MQManager config = properties.getManagers().get(entity);

        if (config == null) {
            throw new MQException("Invalid MQ entity: " + entity);
        }

        QueueConnection connection = null;
        QueueSession session = null;

        try {
            log.info("MQ call started. entity={}", entity);

            MQQueueConnectionFactory factory = createFactory(config);

            connection = factory.createQueueConnection();
            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue inputQueue = session.createQueue(config.getInputQueue());
            Queue outputQueue = session.createQueue(config.getOutputQueue());

            // Convert request to XML
            String xml = XMLUtil.toXML(requestObj);
            log.info("Request XML: {}", xml);

            // Create message
            TextMessage message = session.createTextMessage(xml);
            message.setJMSReplyTo(outputQueue);

            QueueSender sender = session.createSender(inputQueue);
            sender.send(message);

            String correlationId = message.getJMSMessageID();
            log.info("Message sent. correlationId={}", correlationId);

            // Receive response using correlation ID
            String selector = "JMSCorrelationID = '" + correlationId + "'";
            QueueReceiver receiver = session.createReceiver(outputQueue, selector);

            connection.start();

            Message response = receiver.receive(config.getTimeout());

            if (response == null) {
                throw new MQException("MQ Timeout after " + config.getTimeout() + " ms");
            }

            if (!(response instanceof TextMessage)) {
                throw new MQException("Invalid MQ response type");
            }

            TextMessage textMessage = (TextMessage) response;
            String responseXML = textMessage.getText();

            log.info("Response XML: {}", responseXML);

            // Convert XML to Java object dynamically
            Class<?> responseClass = Class.forName(config.getResponseClass());
            Object finalResponse = XMLUtil.fromXML(responseXML, responseClass);

            log.info("MQ call completed in {} ms",
                    System.currentTimeMillis() - startTime);

            return finalResponse;

        } catch (JMSException e) {
            log.error("MQ JMS Exception", e);
            throw new MQException("MQ communication failed", e);

        } catch (ClassNotFoundException e) {
            log.error("Response class not found", e);
            throw new MQException("Invalid response class configuration", e);

        } catch (Exception e) {
            log.error("Unexpected MQ error", e);
            throw new MQException("Unexpected MQ error", e);

        } finally {
            close(session, connection);
        }
    }

    private MQQueueConnectionFactory createFactory(MultiMQProperties.MQManager config) throws JMSException {

        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();

        factory.setQueueManager(config.getQueueManager());
        factory.setChannel(config.getChannel());
        factory.setConnectionNameList(config.getConnName());
        factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);

        // SSL Configuration
        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
        System.setProperty("javax.net.ssl.trustStore", config.getTrustStore());

        factory.setSSLCipherSuite(config.getSslCipherSuite());

        return factory;
    }

    private void close(Session session, Connection connection) {

        try {
            if (session != null) {
                session.close();
            }
        } catch (Exception e) {
            log.warn("Error closing session", e);
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            log.warn("Error closing connection", e);
        }
    }
}