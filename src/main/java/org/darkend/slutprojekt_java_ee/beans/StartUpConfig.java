package org.darkend.slutprojekt_java_ee.beans;

import org.darkend.slutprojekt_java_ee.entity.RoleEntity;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartUpConfig {

    @Bean
    public CommandLineRunner setUpRoles(RoleRepository roleRepository) {
        return (args) -> {
            if (roleRepository.findByRole("ROLE_USER") == null)
                roleRepository.save(new RoleEntity("ROLE_USER"));
            if (roleRepository.findByRole("ROLE_ADMIN") == null)
                roleRepository.save(new RoleEntity("ROLE_ADMIN"));
        };
    }
}
