package org.darkend.slutprojekt_java_ee.beans;

import com.mailjet.client.MailjetClient;
import org.darkend.slutprojekt_java_ee.jms.receiver.EmailSender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class EmailSenderBeanTest {

    private final EmailSenderBean emailSenderBean = new EmailSenderBean();

    @Test
    void emailSenderShouldReturnEmailSender() {
        assertThat(emailSenderBean.emailSender(mock(MailjetClient.class))).isExactlyInstanceOf(EmailSender.class);
    }

}
