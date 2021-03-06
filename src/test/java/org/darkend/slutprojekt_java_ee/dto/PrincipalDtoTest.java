package org.darkend.slutprojekt_java_ee.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PrincipalDtoTest {

    private PrincipalDto principal;

    @BeforeEach
    void setUp() {
        principal = new PrincipalDto();
    }

    @Test
    void getId() {
        principal.setId(1L);

        assertThat(principal.getId()).isEqualTo(1L);
    }

    @Test
    void getFullName() {
        principal.setFullName("Name");

        assertThat(principal.getFullName()).isEqualTo("Name");
    }

    @Test
    void testToString() {
        assertThat(principal).hasToString("PrincipalDto{id=null, fullName='null'}");
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple()
                .forClass(PrincipalDto.class)
                .verify();
    }
}
