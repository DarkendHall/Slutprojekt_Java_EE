package org.darkend.slutprojekt_java_ee.beans;

import org.darkend.slutprojekt_java_ee.entity.UserEntity;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.darkend.slutprojekt_java_ee.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

class StartUpConfigTest {

    private final StartUpConfig startUpConfig = new StartUpConfig();

    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @BeforeEach
    void setUp() {
        when(passwordEncoder.encode(any())).thenReturn("Password");
        when(userRepository.findById(1L)).thenReturn(Optional.of(new UserEntity()));
    }

    @Test
    void testCommandLineRunner() throws Exception {
        var clr = startUpConfig.setUpRoles(roleRepository, userRepository, passwordEncoder);

        clr.run();

        verify(roleRepository, times(1)).findByRole("ROLE_ADMIN");
        verify(roleRepository, times(1)).findByRole("ROLE_USER");
        verify(passwordEncoder, times(1)).encode(any());
        verify(userRepository, times(2)).findById(1L);
    }
}
