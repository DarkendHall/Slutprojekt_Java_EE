package org.darkend.slutprojekt_java_ee.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Id;

import static org.assertj.core.api.Assertions.assertThat;

class RoleEntityTest {

    private RoleEntity role;

    @BeforeEach
    void setUp() {
        role = new RoleEntity();
    }

    @Test
    void getId() {
        role.setId(1L);

        assertThat(role.getId()).isEqualTo(1L);
    }

    @Test
    void getFirstName() {
        role.setRole("Role");

        assertThat(role.getRole()).isEqualTo("Role");
    }

    @Test
    void testToString() {
        assertThat(role).hasToString("RoleEntity{id=null, role='null'}");
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple()
                .forClass(RoleEntity.class)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }

    @Test
    void stringConstructor() {
        RoleEntity otherRole = new RoleEntity("USER");

        assertThat(otherRole.getRole()).isEqualTo("USER");
    }
}
