package org.darkend.slutprojekt_java_ee.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
        assertThat(user).isEqualTo(new UserDtoOut());
    }

    @Test
    void testHashCode() {
        assertThat(user.hashCode()).isEqualTo(new UserDtoOut().hashCode());
    }
}
