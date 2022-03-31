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

import java.util.stream.Collectors;

@Component
public class Receiver {

    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    private final EmailSender emailSender = new EmailSender();

    @JmsListener(destination = JmsConfig.QUEUE)
    public void listener(@Payload MailEntity message) {

        logger.info(String.format("Received message: %s", message));

        var recipients = message.getRecipients()
                .stream()
                .map(RecipientEntity::getEmail)
                .collect(Collectors.toList());
        try {
            var response = emailSender.sendMail(message.getMsg(), recipients);
            logger.info(String.format("Email was sent with data %s", response.getData()));
        } catch (MailjetException e) {
            logger.warn(String.format("Failed to send message because: %s", e));
        }
    }
}
