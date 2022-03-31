package org.darkend.slutprojekt_java_ee.jms.sender;

import org.darkend.slutprojekt_java_ee.entity.MailEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    private final Logger logger = LoggerFactory.getLogger(Sender.class);
    private final JmsTemplate jmsTemplate;

    public Sender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(MailEntity mail) {
        logger.info("Sending message...");
        jmsTemplate.convertAndSend("QUEUE", mail);
        logger.info("Message sent!");
    }
}
