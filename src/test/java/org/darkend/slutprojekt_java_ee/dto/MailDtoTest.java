package org.darkend.slutprojekt_java_ee.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MailDtoTest {

    private MailDto mail;

    @BeforeEach
    void setUp() {
        mail = new MailDto();
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
    void testToString() {
        assertThat(mail).hasToString("MailDto{id=null, msg='null'}");
    }

    @Test
    void testEquals() {
        assertThat(mail).isEqualTo(new MailDto());
    }

    @Test
    void testHashCode() {
        assertThat(mail).hasSameHashCodeAs(new MailDto());
    }
}
