package org.darkend.slutprojekt_java_ee.beans;

import com.mailjet.client.MailjetClient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MailjetClientBeanTest {

    private final MailjetClientBean mailjetClientBean = new MailjetClientBean();

    @Test
    void mailjetClientShouldReturnMailjetClient() {
        assertThat(mailjetClientBean.mailjetClient()).isExactlyInstanceOf(MailjetClient.class);
    }
}
