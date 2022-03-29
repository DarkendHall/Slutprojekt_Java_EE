package org.darkend.slutprojekt_java_ee.jms.receiver;

import org.darkend.slutprojekt_java_ee.entity.MailEntity;
import org.darkend.slutprojekt_java_ee.jms.confiq.JmsConfig;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @JmsListener(destination = JmsConfig.QUEUE)
    public void listener(@Payload MailEntity message) {
        System.out.println(message.getMsg());
    }
}
