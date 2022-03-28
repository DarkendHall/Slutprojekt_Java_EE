package org.darkend.slutprojekt_java_ee.jms.receiver;

import org.darkend.slutprojekt_java_ee.jms.confiq.JmsConfig;
import org.darkend.slutprojekt_java_ee.jms.message.MessageObject;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @JmsListener(destination = JmsConfig.QUEUE)
    public void listener(@Payload MessageObject messageObject) {
        System.out.println(messageObject.getMessage());
    }
}
