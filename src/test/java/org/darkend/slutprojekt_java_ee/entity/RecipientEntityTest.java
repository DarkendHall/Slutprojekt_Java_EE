package org.darkend.slutprojekt_java_ee.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Id;

import static org.assertj.core.api.Assertions.assertThat;

class RecipientEntityTest {

    private RecipientEntity recipient;

    @BeforeEach
    void setUp() {
        recipient = new RecipientEntity();
    }

    @Test
    void setId() {
        var result = recipient.setId(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void setEmail() {
        var result = recipient.setEmail("email@email.com");

        assertThat(result.getEmail()).isEqualTo("email@email.com");
    }

    @Test
    void testToString() {
        assertThat(recipient).hasToString("RecipientEntity{id=null, email='null'}");
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple()
                .forClass(RecipientEntity.class)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }
}
