package org.darkend.slutprojekt_java_ee.jms.sender;

import org.darkend.slutprojekt_java_ee.entity.MailEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    private final JmsTemplate jmsTemplate;

    public Sender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(MailEntity mail) {
        System.out.println("Sending message...");
        jmsTemplate.convertAndSend("QUEUE", mail);
        System.out.println("Message sent!");
    }
}
