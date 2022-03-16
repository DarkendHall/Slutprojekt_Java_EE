package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.entity.RoleEntity;
import org.darkend.slutprojekt_java_ee.entity.UserEntity;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.darkend.slutprojekt_java_ee.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        RoleEntity roleToAdd = roleRepository.findByRole("ROLE_USER");
        userEntity.addRole(roleToAdd);
        return userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<UserEntity> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Iterable<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }
}
