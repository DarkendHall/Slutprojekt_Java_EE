package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.entity.TeacherEntity;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.darkend.slutprojekt_java_ee.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceTest {

    private TeacherService service;
    private TeacherRepository repository;

    private final TeacherDto teacherDto = new TeacherDto().setId(1L)
            .setFullName("Teacher Name");

    private final TeacherEntity teacherEntity = new TeacherEntity().setId(1L)
            .setFirstName("Teacher")
            .setLastName("Name");

    @BeforeEach
    void setUp() {
        repository = mock(TeacherRepository.class);
        ModelMapper mapper = ModelMapperConfig.getModelMapper(mock(RoleRepository.class));
        service = new TeacherService(repository, mapper);
        when(repository.save(teacherEntity)).thenReturn(teacherEntity);
        when(repository.findById(1L)).thenReturn(Optional.of(teacherEntity));
        when(repository.findById(2L)).thenReturn(Optional.empty());
        when(repository.findAll()).thenReturn(List.of(teacherEntity));
    }

    @Test
    void createTeacherWithValidTeacherShouldReturnCreatedTeacherAsDto() {
        var result = service.createTeacher(teacherDto);

        assertThat(result).isEqualTo(teacherDto);
    }

    @Test
    void createTeacherWithInvalidTeacherShouldThrowException() {
        var invalidTeacherDto = new TeacherDto().setId(1L)
                .setFullName(null);

        var invalidTeacherEntity = new TeacherEntity().setId(1L)
                .setFirstName(null)
                .setLastName(null);

        doThrow(new ConstraintViolationException(null, null)).when(repository)
                .save(invalidTeacherEntity);

        assertThatThrownBy(() -> service.createTeacher(invalidTeacherDto)).isInstanceOfAny(
                ConstraintViolationException.class, MappingException.class);
    }

    @Test
    void deleteTeacherShouldCallDeleteTeacherInRepository() {
        service.deleteTeacher(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTeacherWithInvalidIdShouldThrowException() {
        doThrow(new EmptyResultDataAccessException(1)).when(repository)
                .deleteById(2L);

        assertThatThrownBy(() -> service.deleteTeacher(2L));
    }

    @Test
    void findTeacherByIdWithValidIdOne() {
        var result = service.findTeacherById(1L);

        assertThat(result).isEqualTo(teacherDto.setId(1L));
    }

    @Test
    void findTeacherByIdWithInvalidIdTwo() {
        assertThatThrownBy(() -> service.findTeacherById(2L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void findAllTeachersShouldReturnAllTeachers() {
        var result = service.findAllTeachers();

        assertThat(result).isEqualTo(List.of(teacherDto));
    }
}
