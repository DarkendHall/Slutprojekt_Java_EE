package org.darkend.slutprojekt_java_ee.jms.sender;

import org.darkend.slutprojekt_java_ee.entity.MailEntity;
import org.darkend.slutprojekt_java_ee.entity.RecipientEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.*;

class SenderTest {

    private Sender sender;

    private MailEntity mail;

    private JmsTemplate jmsTemplate;

    @BeforeEach
    void setUp() {
        jmsTemplate = mock(JmsTemplate.class);
        sender = new Sender(jmsTemplate);
        mail = new MailEntity().setId(1L)
                .setMsg("test")
                .setRecipients(List.of(new RecipientEntity().setId(2L)
                        .setEmail("email@email.com")));
    }

    @Test
    void sendMessageShouldPassCallForward() {
        assertThatNoException().isThrownBy(() -> sender.sendMessage(mail));
        verify(jmsTemplate, times(1)).convertAndSend("QUEUE", mail);
    }
}
