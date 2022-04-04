package org.darkend.slutprojekt_java_ee.security;

import org.darkend.slutprojekt_java_ee.entity.RoleEntity;
import org.darkend.slutprojekt_java_ee.entity.UserEntity;
import org.darkend.slutprojekt_java_ee.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApplicationUserDetailsServiceTest {

    private ApplicationUserDetailsService service;

    private final UserEntity userEntity = new UserEntity().setId(1L)
            .setUsername("user")
            .setPassword("password")
            .setRoles(List.of(new RoleEntity().setId(2L)
                    .setRole("USER")));
    private final UserPrincipal user = new UserPrincipal(userEntity);

    @BeforeEach
    void setUp() {
        UserRepository repository = mock(UserRepository.class);
        service = new ApplicationUserDetailsService(repository);
        when(repository.findByUsername("user")).thenReturn(userEntity);
    }

    @Test
    void loadUserByUsernameShouldReturnUser() {
        var result = service.loadUserByUsername("user");

        assertThat(result).isEqualTo(user);
    }

    @Test
    void loadUserByUsernameWithInvalidUsernameShouldThrowException() {
        assertThatThrownBy(() -> service.loadUserByUsername("")).isExactlyInstanceOf(UsernameNotFoundException.class);
    }
}
