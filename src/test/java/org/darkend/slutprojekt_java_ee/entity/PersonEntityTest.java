package org.darkend.slutprojekt_java_ee.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PersonEntityTest {

    private PersonEntity principal;

    @BeforeEach
    void setUp() {
        principal = new PersonEntity();
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
        assertThat(principal).hasToString("PersonEntity{id=null, firstName='null', lastName='null'}");
    }

    @Test
    void testEquals() {
        assertThat(principal).isEqualTo(new PersonEntity());
    }

    @Test
    void testHashCode() {
        assertThat(principal.hashCode()).isEqualTo(new PersonEntity().hashCode());
    }
}
