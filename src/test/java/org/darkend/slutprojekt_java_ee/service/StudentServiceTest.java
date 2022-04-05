package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.darkend.slutprojekt_java_ee.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentServiceTest {

    private StudentService service;
    private StudentRepository repository;

    private final StudentDto studentDto = new StudentDto().setId(1L)
            .setFullName("Student Name")
            .setEmail("email@email.com")
            .setPhoneNumber("N/A");

    private final StudentEntity studentEntity = new StudentEntity().setId(1L)
            .setFirstName("Student")
            .setLastName("Name")
            .setEmail("email@email.com")
            .setPhoneNumber("N/A");

    @BeforeEach
    void setUp() {
        repository = mock(StudentRepository.class);
        ModelMapper mapper = ModelMapperConfig.getModelMapper(mock(RoleRepository.class));
        service = new StudentService(repository, mapper);
        when(repository.findById(1L)).thenReturn(Optional.of(studentEntity));
        when(repository.findById(2L)).thenReturn(Optional.empty());
        when(repository.findAll()).thenReturn(List.of(studentEntity));
        when(repository.save(any(StudentEntity.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return args[0];
        });
    }

    @Test
    void createStudentWithValidStudentShouldReturnCreatedStudentAsDto() {
        var result = service.createStudent(studentDto);

        assertThat(result).isEqualTo(studentDto);
    }

    @Test
    void createStudentWithInvalidStudentShouldThrowException() {
        var invalidStudentDto = new StudentDto().setId(1L)
                .setFullName(null);

        var invalidStudentEntity = new StudentEntity().setId(1L)
                .setFirstName(null)
                .setLastName(null);

        doThrow(new ConstraintViolationException(null, null)).when(repository)
                .save(invalidStudentEntity);

        assertThatThrownBy(() -> service.createStudent(invalidStudentDto)).isInstanceOfAny(
                ConstraintViolationException.class, MappingException.class);
    }

    @Test
    void deleteStudentShouldCallDeleteStudentInRepository() {
        service.deleteStudent(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteStudentWithInvalidIdShouldThrowException() {
        doThrow(new EmptyResultDataAccessException(1)).when(repository)
                .deleteById(2L);

        assertThatThrownBy(() -> service.deleteStudent(2L)).isExactlyInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void findStudentByIdWithValidIdOne() {
        var result = service.findStudentById(1L);

        assertThat(result).isEqualTo(studentDto.setId(1L));
    }

    @Test
    void findStudentByIdWithInvalidIdTwo() {
        assertThatThrownBy(() -> service.findStudentById(2L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void findAllStudentsShouldReturnAllStudents() {
        var result = service.findAllStudents();

        assertThat(result).isEqualTo(List.of(studentDto));
    }

    @Test
    void setEmailShouldUpdateEmailInStudent() {
        var studentDto = new StudentDto().setId(1L)
                .setFullName("Student Name")
                .setPhoneNumber("N/A")
                .setEmail("test@test.test");

        var result = service.setEmail("test@test.test", 1L);

        assertThat(result).isEqualTo(studentDto);
    }

    @Test
    void setEmailWithInvalidIdShouldThrowException() {
        assertThatThrownBy(() -> service.setEmail("test@test.test", 2L)).isExactlyInstanceOf(
                EntityNotFoundException.class);
    }

    @Test
    void setPhoneNumberShouldUpdatePhoneNumberInStudent() {
        var studentDto = new StudentDto().setId(1L)
                .setFullName("Student Name")
                .setPhoneNumber("phoneNumber")
                .setEmail("email@email.com");

        var result = service.setPhoneNumber("phoneNumber", 1L);

        assertThat(result).isEqualTo(studentDto);
    }

    @Test
    void setPhoneNumberWithInvalidIdShouldThrowException() {
        assertThatThrownBy(() -> service.setPhoneNumber("phoneNumber", 2L)).isExactlyInstanceOf(
                EntityNotFoundException.class);
    }
}
