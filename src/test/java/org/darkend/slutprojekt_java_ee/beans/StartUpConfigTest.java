package org.darkend.slutprojekt_java_ee.beans;

import org.darkend.slutprojekt_java_ee.entity.RoleEntity;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StartUpConfigTest {

    private StartUpConfig startUpConfig;
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        startUpConfig = new StartUpConfig();
        roleRepository = mock(RoleRepository.class);
    }

    @Test
    void testCommandLineRunnerWithNull() throws Exception {
        var clr = startUpConfig.setUpRoles(roleRepository);

        clr.run();

        verify(roleRepository, times(1)).findByRole("ROLE_ADMIN");
        verify(roleRepository, times(1)).findByRole("ROLE_USER");
    }

    @Test
    void testCommandLineRunnerWithNonNull() throws Exception {
        when(roleRepository.findByRole("ROLE_USER")).thenReturn(new RoleEntity().setRole("ROLE_USER")
                .setId(1L));
        when(roleRepository.findByRole("ROLE_ADMIN")).thenReturn(new RoleEntity().setRole("ROLE_ADMIN")
                .setId(2L));

        var clr = startUpConfig.setUpRoles(roleRepository);

        clr.run();

        verify(roleRepository, times(1)).findByRole("ROLE_ADMIN");
        verify(roleRepository, times(1)).findByRole("ROLE_USER");
    }
}
