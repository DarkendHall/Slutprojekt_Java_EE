package org.darkend.slutprojekt_java_ee.jms.receiver;

import com.mailjet.client.errors.MailjetException;
import org.darkend.slutprojekt_java_ee.entity.MailEntity;
import org.darkend.slutprojekt_java_ee.entity.RecipientEntity;
import org.darkend.slutprojekt_java_ee.jms.confiq.JmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    private final EmailSender emailSender = new EmailSender();

    @JmsListener(destination = JmsConfig.QUEUE)
    public void listener(@Payload MailEntity message) {

        logger.info("Received message: {}", message);

        var recipients = message.getRecipients()
                .stream()
                .map(RecipientEntity::getEmail)
                .toList();
        try {
            var response = emailSender.sendMail(message.getMsg(), recipients);
            logger.info("Email was sent with data {}", response.getData());
        } catch (MailjetException e) {
            logger.warn("Failed to send message because: {}", e.getMessage());
        }
    }
}
