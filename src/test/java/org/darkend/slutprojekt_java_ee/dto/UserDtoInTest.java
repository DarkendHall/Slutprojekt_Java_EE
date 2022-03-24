package org.darkend.slutprojekt_java_ee.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserDtoInTest {

    private UserDtoIn user;

    @BeforeEach
    void setUp() {
        user = new UserDtoIn();
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
    void getPassword() {
        user.setPassword("Password");

        assertThat(user.getPassword()).isEqualTo("Password");
    }

    @Test
    void getRoles() {
        user.setRoles(List.of("role"));

        assertThat(user.getRoles()).isEqualTo(List.of("role"));
    }

    @Test
    void testToString() {
        assertThat(user.toString()).isEqualTo("UserDtoIn{id=null, username='null', password='null', roles=null}");
    }

    @Test
    void testEquals() {
        assertThat(user).isEqualTo(new UserDtoIn());
    }

    @Test
    void testHashCode() {
        assertThat(user.hashCode()).isEqualTo(new UserDtoIn().hashCode());
    }
}