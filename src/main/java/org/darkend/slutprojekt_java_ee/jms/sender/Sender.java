package org.darkend.slutprojekt_java_ee.jms.sender;

import org.darkend.slutprojekt_java_ee.jms.message.MessageObject;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class Sender {

    JmsTemplate jmsTemplate;

    public Sender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String msg) {
        System.out.println("Sending message...");
        MessageObject messageObject = new MessageObject(UUID.randomUUID(), msg, LocalDateTime.now());
        jmsTemplate.convertAndSend("QUEUE", messageObject);
        System.out.println("Message sent!");
    }
}