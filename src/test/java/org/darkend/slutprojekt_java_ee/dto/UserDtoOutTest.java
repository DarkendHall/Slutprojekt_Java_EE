package org.darkend.slutprojekt_java_ee.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserDtoOutTest {

    private UserDtoOut user;

    @BeforeEach
    void setUp() {
        user = new UserDtoOut();
    }

    @Test
    void getId() {
        user.setId(1L);

        assertThat(user.getId()).isEqualTo(1L);
    }

    @Test
    void getUsername() {
        user.setUsername("Username");

        assertThat(user.getUsername()).isEqualTo("Username");
    }

    @Test
    void getRoles() {
        user.setRoles(List.of("role"));

        assertThat(user.getRoles()).isEqualTo(List.of("role"));
    }

    @Test
    void testToString() {
        assertThat(user).hasToString("UserDtoOut{id=null, username='null', roles=null}");
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple()
                .forClass(UserDtoOut.class)
                .verify();
    }
}
