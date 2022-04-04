package org.darkend.slutprojekt_java_ee.jms.receiver;

import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import org.darkend.slutprojekt_java_ee.entity.MailEntity;
import org.darkend.slutprojekt_java_ee.entity.RecipientEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReceiverTest {

    private Receiver receiver;

    private MailEntity message;

    private EmailSender sender;

    private RecipientEntity recipient;

    private MailjetResponse response;

    @BeforeEach
    void setUp() {
        recipient = new RecipientEntity().setId(1L)
                .setEmail("email@email.com");
        message = new MailEntity().setId(2L)
                .setMsg("Test")
                .setRecipients(List.of(recipient));
        sender = mock(EmailSender.class);
        receiver = new Receiver(sender);
        response = new MailjetResponse(200, "{\"msg\": msg}");
    }

    @Test
    void sendMailShouldReturnValidResponse() throws MailjetException {
        when(sender.sendMail(message.getMsg(), List.of(recipient.getEmail()))).thenReturn(response);

        assertThatNoException().isThrownBy(() -> receiver.listener(message));
        verify(sender, times(1)).sendMail(message.getMsg(), List.of(recipient.getEmail()));
    }

    @Test
    void shouldThrowException() throws MailjetException {
        when(sender.sendMail(message.getMsg(), List.of(recipient.getEmail()))).thenThrow(new MailjetException("error"));

        assertThatNoException().isThrownBy(() -> receiver.listener(message));
        verify(sender, times(1)).sendMail(message.getMsg(), List.of(recipient.getEmail()));
    }
}
