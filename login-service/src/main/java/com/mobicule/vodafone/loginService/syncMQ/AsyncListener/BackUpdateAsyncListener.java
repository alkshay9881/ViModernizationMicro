package com.mobicule.vodafone.loginService.syncMQ.AsyncListener;

import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("activationBackUpdateListener")
@Slf4j
public class BackUpdateAsyncListener implements MessageListener {
    @Override
    public void onMessage(Message message) {

        try {
            String payload = ((TextMessage) message).getText();
            log.info("activationBackUpdateListener PROCESSING: {}", payload);
        } catch (Exception e) {
            log.error("Error in CafLiteListener", e);
        }
    }
}
