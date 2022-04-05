package org.darkend.slutprojekt_java_ee.security;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.darkend.slutprojekt_java_ee.entity.RoleEntity;
import org.darkend.slutprojekt_java_ee.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserPrincipalTest {

    private final UserEntity userEntity =
            new UserEntity().setId(1L)
                    .setUsername("user")
                    .setPassword("password")
                    .setRoles(List.of(new RoleEntity().setId(2L)
                            .setRole("user")));

    private UserPrincipal user;

    @BeforeEach
    void setUp() {
        user = new UserPrincipal(userEntity);
    }

    @Test
    void getUsernameShouldReturnUsername() {
        var result = user.getUsername();

        assertThat(result).isEqualTo("user");
    }

    @Test
    void getPasswordShouldReturnPassword() {
        var result = user.getPassword();

        assertThat(result).isEqualTo("password");
    }

    @Test
    void getAuthoritiesShouldReturnGrantedAuthorities() {
        var result = user.getAuthorities();

        assertThat(result).hasSize(1);
        assertThat(result.toArray()).contains(new SimpleGrantedAuthority("USER"));
    }

    @Test
    void isAccountNonExpiredShouldReturnTrue() {
        var result = user.isAccountNonExpired();

        assertThat(result).isTrue();
    }

    @Test
    void isAccountNonLockedShouldReturnTrue() {
        var result = user.isAccountNonLocked();

        assertThat(result).isTrue();
    }

    @Test
    void isCredentialsNonExpiredShouldReturnTrue() {
        var result = user.isCredentialsNonExpired();

        assertThat(result).isTrue();
    }

    @Test
    void isEnabled() {
        var result = user.isEnabled();

        assertThat(result).isTrue();
    }

    @Test
    void testToString() {
        assertThat(user.toString()).hasToString("UserPrincipal{username='user', password='password', roles=[USER]}");
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple()
                .forClass(UserPrincipal.class)
                .verify();
    }
}
