package org.darkend.slutprojekt_java_ee.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RoleDtoTest {

    private RoleDto role;

    @BeforeEach
    void setUp() {
        role = new RoleDto();
    }

    @Test
    void getId() {
        role.setId(1L);

        assertThat(role.getId()).isEqualTo(1L);
    }

    @Test
    void getRole() {
        role.setRole("Role");

        assertThat(role.getRole()).isEqualTo("Role");
    }

    @Test
    void testToString() {
        assertThat(role.toString()).isEqualTo("RoleDto{id=null, role='null'}");
    }

    @Test
    void testEquals() {
        assertThat(role).isEqualTo(new RoleDto());
    }

    @Test
    void testHashCode() {
        assertThat(role.hashCode()).isEqualTo(new RoleDto().hashCode());
    }
}
