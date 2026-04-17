package com.mobicule.vodafone.loginService.syncMQ.service;

import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import com.mobicule.vodafone.loginService.common.entities.Response;
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
public class AsyncMQSendSmsService {

    private final MultiMQProperties properties;

    /**
     * ? Async MQ Send (Fire & Forget)
     * Uses JMSMessageID for tracking
     */
    public Response sendAsync(Object requestObj, String entity) {

        long startTime = System.currentTimeMillis();

        MultiMQProperties.MQManager config =
                properties.getManagers().get(entity);

        if (config == null) {
            throw new MQException("Invalid MQ entity: " + entity);
        }

        QueueConnection connection = null;
        QueueSession session = null;
        QueueSender sender = null;

        try {
            log.info("Async MQ call started. entity={}", entity);

            MQQueueConnectionFactory factory = createFactory(config);

            connection = factory.createQueueConnection();
            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            // ? Input Queue
            Queue inputQueue = session.createQueue(config.getInputQueue());

            // ? Convert request ? XML
            String xml = XMLUtil.toXML(requestObj);
            log.info("Request XML: {}", xml);

            // ? Create message
            TextMessage message = session.createTextMessage(xml);

            // ? (Optional but recommended) Reply Queue
            if (config.getOutputQueue() != null) {
                Queue replyQueue = session.createQueue(config.getOutputQueue());
                message.setJMSReplyTo(replyQueue);
            }

            sender = session.createSender(inputQueue);

            // ? SEND MESSAGE
            sender.send(message);

            // ? GET JMSMessageID (IMPORTANT)
            String messageId = message.getJMSMessageID();

            log.info("Async MQ message sent. JMSMessageID={}", messageId);

            return new Response(
                    Response.ResponseStatus.SUCCESS,
                    "Request processed successfully",
                    messageId
            );

        } catch (JMSException e) {
            log.error("MQ JMS Exception", e);
            throw new MQException("MQ async communication failed", e);

        } catch (Exception e) {
            log.error("Unexpected async MQ error", e);
            throw new MQException("Unexpected async MQ error", e);

        } finally {
            close(sender, session, connection);

            log.info("Async MQ completed in {} ms",
                    System.currentTimeMillis() - startTime);
        }
    }

    /**
     * ? MQ Factory creation
     */
    private MQQueueConnectionFactory createFactory(MultiMQProperties.MQManager config)
            throws JMSException {

        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();

        factory.setQueueManager(config.getQueueManager());
        factory.setChannel(config.getChannel());
        factory.setConnectionNameList(config.getConnName());
        factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);

        // ? SSL Configuration
        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
        System.setProperty("javax.net.ssl.trustStore", config.getTrustStore());

        factory.setSSLCipherSuite(config.getSslCipherSuite());

        return factory;
    }

    /**
     * ? Safe resource cleanup
     */
    private void close(QueueSender sender, Session session, Connection connection) {

        try {
            if (sender != null) sender.close();
        } catch (Exception e) {
            log.warn("Error closing sender", e);
        }

        try {
            if (session != null) session.close();
        } catch (Exception e) {
            log.warn("Error closing session", e);
        }

        try {
            if (connection != null) connection.close();
        } catch (Exception e) {
            log.warn("Error closing connection", e);
        }
    }
}