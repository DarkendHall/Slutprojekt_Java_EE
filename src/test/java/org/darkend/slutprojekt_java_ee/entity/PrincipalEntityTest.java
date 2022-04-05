package org.darkend.slutprojekt_java_ee.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Id;

import static org.assertj.core.api.Assertions.assertThat;

class PrincipalEntityTest {

    private PrincipalEntity principal;

    @BeforeEach
    void setUp() {
        principal = new PrincipalEntity();
    }

    @Test
    void getId() {
        principal.setId(1L);

        assertThat(principal.getId()).isEqualTo(1L);
    }

    @Test
    void getFirstName() {
        principal.setFirstName("First");

        assertThat(principal.getFirstName()).isEqualTo("First");
    }

    @Test
    void getLastName() {
        principal.setLastName("Last");

        assertThat(principal.getLastName()).isEqualTo("Last");
    }

    @Test
    void testToString() {
        assertThat(principal).hasToString("PrincipalEntity{id=null, firstName='null', lastName='null'}");
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple()
                .forClass(PrincipalEntity.class)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }
}
