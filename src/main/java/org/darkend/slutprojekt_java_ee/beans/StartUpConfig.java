package org.darkend.slutprojekt_java_ee.beans;

import org.darkend.slutprojekt_java_ee.entity.RoleEntity;
import org.darkend.slutprojekt_java_ee.entity.UserEntity;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.darkend.slutprojekt_java_ee.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

@Configuration
public class StartUpConfig {

    @Bean
    public CommandLineRunner setUpRoles(RoleRepository roleRepository, UserRepository userRepository,
                                        PasswordEncoder passwordEncoder) {
        return (args) -> {
            Map<String, String> env = System.getenv();
            String password = passwordEncoder.encode(env.get("PASSWORD"));
            UserEntity standardUser = new UserEntity().setId(1L)
                    .setUsername("user")
                    .setPassword(password);

            if (roleRepository.findByRole("ROLE_ADMIN") == null)
                roleRepository.save(new RoleEntity("ROLE_ADMIN"));
            if (roleRepository.findByRole("ROLE_USER") == null)
                roleRepository.save(new RoleEntity("ROLE_USER"));
            if (userRepository.findById(1L)
                    .equals(Optional.empty()) || !userRepository.findById(1L)
                    .equals(Optional.of(standardUser)))
                userRepository.save(standardUser);
        };
    }
}
