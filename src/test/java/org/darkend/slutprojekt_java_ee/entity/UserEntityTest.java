package org.darkend.slutprojekt_java_ee.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Id;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityTest {

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
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
        RoleEntity role = new RoleEntity();

        user.setRoles(List.of(role));

        assertThat(user.getRoles()).isEqualTo(List.of(role));
    }

    @Test
    void addRole() {
        RoleEntity role = new RoleEntity();

        user.addRole(role);

        assertThat(user.getRoles()).isEqualTo(List.of(role));
    }

    @Test
    void removeRole() {
        RoleEntity role = new RoleEntity();

        user.addRole(role);
        var beforeRemove = List.copyOf(user.getRoles());
        user.removeRole(role);
        var afterRemove = List.copyOf(user.getRoles());

        assertThat(beforeRemove).isEqualTo(List.of(role));
        assertThat(afterRemove).isEqualTo(List.of());
    }

    @Test
    void testToString() {
        assertThat(user).hasToString("UserEntity{id=null, username='null', password='null', roles=[]}");
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple()
                .forClass(UserEntity.class)
                .withIgnoredAnnotations(Id.class)
                .withIgnoredFields("password")
                .verify();
    }
}
