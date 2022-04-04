package org.darkend.slutprojekt_java_ee.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertThat(role).isEqualTo(new RoleEntity());
    }

    @Test
    void testHashCode() {
        assertThat(role).hasSameHashCodeAs(new RoleEntity());
    }

    @Test
    void stringConstructor() {
        RoleEntity otherRole = new RoleEntity("USER");

        assertThat(otherRole.getRole()).isEqualTo("USER");
    }
}
