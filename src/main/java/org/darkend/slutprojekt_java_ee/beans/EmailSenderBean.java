package org.darkend.slutprojekt_java_ee.beans;

import com.mailjet.client.MailjetClient;
import org.darkend.slutprojekt_java_ee.jms.receiver.EmailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailSenderBean {

    @Bean
    public EmailSender emailSender(MailjetClient client) {
        return new EmailSender(client);
    }
}
