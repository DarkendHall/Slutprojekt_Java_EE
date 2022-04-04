package org.darkend.slutprojekt_java_ee.jms.receiver;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SystemStubsExtension.class)
class EmailSenderTest {

    private MailjetClient client;

    private EmailSender emailSender;

    @SystemStub
    private EnvironmentVariables envVars;

    @BeforeEach
    void setUp() {
        envVars = new EnvironmentVariables();
        client = mock(MailjetClient.class);
        envVars.set("MJ_EMAIL_ADDRESS", "some@email.com");
        emailSender = new EmailSender(client);
    }

    @Test
    void sendEmailShouldReturnResponse() throws MailjetException {
        var response = new MailjetResponse(200, "{\"msg\": msg}");

        when(client.post(any())).thenReturn(response);

        var result = emailSender.sendMail("msg", List.of("Recipient"));

        assertThat(result).isEqualTo(response);
    }

    @Test
    void envShould() {
        var env = System.getenv("MJ_EMAIL_ADDRESS");
        assertThat(env).isEqualTo(emailSender.getEmailAddress());
    }
}
