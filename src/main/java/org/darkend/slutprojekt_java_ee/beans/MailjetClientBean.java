package org.darkend.slutprojekt_java_ee.beans;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailjetClientBean {

    private final ClientOptions options = ClientOptions.builder()
            .apiKey(System.getenv("MJ_PRIMARY_KEY"))
            .apiSecretKey(System.getenv("MJ_SECRET_KEY"))
            .build();

    @Bean
    public MailjetClient mailjetClient() {
        return new MailjetClient(options);
    }
}
