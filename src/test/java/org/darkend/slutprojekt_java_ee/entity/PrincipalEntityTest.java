package org.darkend.slutprojekt_java_ee.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

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
        assertThat(principal.toString()).isEqualTo("PrincipalEntity{id=null, firstName='null', lastName='null'}");
    }

    @Test
    void testEquals() {
        assertThat(principal).isEqualTo(new PrincipalEntity());
    }

    @Test
    void testHashCode() {
        assertThat(principal.hashCode()).isEqualTo(new PrincipalEntity().hashCode());
    }
}
