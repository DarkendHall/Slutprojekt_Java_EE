package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.dto.UserDtoIn;
import org.darkend.slutprojekt_java_ee.dto.UserDtoOut;
import org.darkend.slutprojekt_java_ee.entity.UserEntity;
import org.darkend.slutprojekt_java_ee.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    public UserDtoOut createUser(UserDtoIn userDtoIn) {
        userDtoIn.setPassword(passwordEncoder.encode(userDtoIn.getPassword()));
        var userEntity = mapper.map(userDtoIn, UserEntity.class);
        var savedUser = userRepository.save(userEntity);
        return mapper.map(savedUser, UserDtoOut.class);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDtoOut findUserById(Long id) {
        var entityOptional = userRepository.findById(id);
        var entity = entityOptional.orElseThrow(() -> new EntityNotFoundException("No course found with ID: " + id));
        return mapper.map(entity, UserDtoOut.class);
    }

    public List<UserDtoOut> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> mapper.map(user, UserDtoOut.class))
                .toList();
    }
}
