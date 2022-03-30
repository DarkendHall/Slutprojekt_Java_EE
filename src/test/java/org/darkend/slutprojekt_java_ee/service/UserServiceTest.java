package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.dto.UserDtoIn;
import org.darkend.slutprojekt_java_ee.dto.UserDtoOut;
import org.darkend.slutprojekt_java_ee.entity.RoleEntity;
import org.darkend.slutprojekt_java_ee.entity.UserEntity;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.darkend.slutprojekt_java_ee.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService service;
    private UserRepository userRepository;

    private final UserDtoIn userDtoIn = new UserDtoIn().setId(1L)
            .setUsername("username")
            .setPassword("password")
            .setRoles(List.of("USER"));

    private final UserDtoOut userDtoOut = new UserDtoOut().setId(1L)
            .setUsername("username")
            .setRoles(List.of("USER"));

    private final RoleEntity roleEntity = new RoleEntity().setId(1L)
            .setRole("USER");

    private final UserEntity userEntity = new UserEntity().setId(1L)
            .setUsername("username")
            .setRoles(List.of(roleEntity));

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);

        ModelMapper mapper = ModelMapperConfig.getModelMapper(roleRepository);
        service = new UserService(userRepository, mock(PasswordEncoder.class), mapper);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        when(userRepository.findAll()).thenReturn(List.of(userEntity));
        when(roleRepository.findByRole("USER")).thenReturn(roleEntity);
    }

    @Test
    void createUserWithValidUserShouldReturnCreatedUserAsDto() {
        var result = service.createUser(userDtoIn);

        assertThat(result).isEqualTo(userDtoOut);
    }

    @Test
    void createUserWithInvalidUserShouldThrowException() {
        var invalidUserDto = new UserDtoIn().setId(1L)
                .setUsername(null);

        var invalidUserEntity = new UserEntity().setId(1L)
                .setUsername(null)
                .setPassword(null);

        doThrow(new ConstraintViolationException(null, null)).when(userRepository)
                .save(invalidUserEntity);

        assertThatThrownBy(() -> service.createUser(invalidUserDto)).isInstanceOfAny(ConstraintViolationException.class,
                MappingException.class);
    }

    @Test
    void deleteUserShouldCallDeleteUserInRepository() {
        service.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUserWithInvalidIdShouldThrowException() {
        doThrow(new EmptyResultDataAccessException(1)).when(userRepository)
                .deleteById(2L);

        assertThatThrownBy(() -> service.deleteUser(2L));
    }

    @Test
    void findUserByIdWithValidIdOne() {
        var result = service.findUserById(1L);

        assertThat(result).isEqualTo(userDtoOut);
    }

    @Test
    void findUserByIdWithInvalidIdTwo() {
        assertThatThrownBy(() -> service.findUserById(2L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void findAllUsersShouldReturnAllUsers() {
        var result = service.findAllUsers();

        assertThat(result).isEqualTo(List.of(userDtoOut));
    }
}
