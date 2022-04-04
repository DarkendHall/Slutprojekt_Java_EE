package org.darkend.slutprojekt_java_ee.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Id;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MailEntityTest {

    private MailEntity mail;
    private final RecipientEntity recipient = new RecipientEntity().setId(2L)
            .setEmail("email@email.com");

    @BeforeEach
    void setUp() {
        mail = new MailEntity();
    }

    @Test
    void setId() {
        var result = mail.setId(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void setMsg() {
        var result = mail.setMsg("msg");

        assertThat(result.getMsg()).isEqualTo("msg");
    }

    @Test
    void setRecipients() {
        var result = mail.setRecipients(List.of(recipient));

        assertThat(result.getRecipients()).isEqualTo(List.of(recipient));
    }

    @Test
    void testToString() {
        assertThat(mail).hasToString("MailEntity{id=null, msg='null', recipients=[]}");
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple()
                .forClass(MailEntity.class)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }
}
