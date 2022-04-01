package org.darkend.slutprojekt_java_ee.beans;

import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class StartUpConfigTest {

    private final StartUpConfig startUpConfig = new StartUpConfig();

    private final RoleRepository roleRepository = mock(RoleRepository.class);

    @Test
    void testCommandLineRunner() throws Exception {
        var clr = startUpConfig.setUpRoles(roleRepository);

        clr.run();

        verify(roleRepository, times(1)).findByRole("ROLE_ADMIN");
        verify(roleRepository, times(1)).findByRole("ROLE_USER");
    }
}
