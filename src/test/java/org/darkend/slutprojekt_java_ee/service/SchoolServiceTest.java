package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.dto.SchoolDto;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.entity.CourseEntity;
import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.darkend.slutprojekt_java_ee.entity.SchoolEntity;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.entity.TeacherEntity;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.darkend.slutprojekt_java_ee.repository.SchoolRepository;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SchoolServiceTest {

    private SchoolService service;
    private SchoolRepository repository;

    private final StudentDto studentDto = new StudentDto().setId(2L)
            .setFullName("Student Name")
            .setPhoneNumber("N/A")
            .setEmail("email@email.com");

    private final TeacherDto teacherDto = new TeacherDto().setId(3L)
            .setFullName("Teacher Name");

    private final SchoolDto schoolDto = new SchoolDto().setId(1L)
            .setName("School Name")
            .setAddress("Address")
            .setCity("City")
            .setStudents(List.of(studentDto))
            .setTeachers(List.of(teacherDto))
            .setPrincipal(new PrincipalDto().setId(4L)
                    .setFullName("Principal Name"))
            .setCourses(List.of(new CourseDto().setId(5L)
                    .setName("Course Name")
                    .setStudents(List.of(studentDto))
                    .setTeacher(teacherDto)));

    private final StudentEntity studentEntity = new StudentEntity().setId(2L)
            .setFirstName("Student")
            .setLastName("Name")
            .setPhoneNumber("N/A")
            .setEmail("email@email.com");

    private final TeacherEntity teacherEntity = new TeacherEntity().setId(3L)
            .setFirstName("Teacher")
            .setLastName("Name");

    private final SchoolEntity schoolEntity = new SchoolEntity().setId(1L)
            .setName("School Name")
            .setAddress("Address")
            .setCity("City")
            .setStudents(List.of(studentEntity))
            .setTeachers(List.of(teacherEntity))
            .setPrincipal(new PrincipalEntity().setId(4L)
                    .setFirstName("Principal")
                    .setLastName("Name"))
            .setCourses(List.of(new CourseEntity().setId(5L)
                    .setName("Course Name")
                    .setStudents(List.of(studentEntity))
                    .setTeacher(teacherEntity)));

    @BeforeEach
    void setUp() {
        repository = mock(SchoolRepository.class);
        ModelMapper mapper = ModelMapperConfig.getModelMapper(mock(RoleRepository.class));
        service = new SchoolService(repository, mapper);
        when(repository.save(schoolEntity)).thenReturn(schoolEntity);
        when(repository.findById(1L)).thenReturn(Optional.of(schoolEntity));
        when(repository.findById(2L)).thenReturn(Optional.empty());
        when(repository.findAll()).thenReturn(List.of(schoolEntity));
    }

    @Test
    void createSchoolWithValidSchoolShouldReturnCreatedSchoolAsDto() {
        var result = service.createSchool(schoolDto);

        assertThat(result).isEqualTo(schoolDto);
    }

    @Test
    void createSchoolWithInvalidSchoolShouldThrowException() {
        var invalidSchoolDto = new SchoolDto().setId(1L)
                .setName(null)
                .setAddress("Address")
                .setCity("City")
                .setStudents(null)
                .setTeachers(List.of(teacherDto))
                .setPrincipal(new PrincipalDto().setId(4L)
                        .setFullName("Principal Name"))
                .setCourses(List.of(new CourseDto().setId(5L)
                        .setName("Course Name")
                        .setStudents(List.of(studentDto))
                        .setTeacher(teacherDto)));

        var invalidSchoolEntity = new SchoolEntity().setId(1L)
                .setName(null)
                .setAddress("Address")
                .setCity("City")
                .setStudents(null)
                .setTeachers(List.of(teacherEntity))
                .setPrincipal(new PrincipalEntity().setId(4L)
                        .setFirstName("Principal")
                        .setLastName("Name"))
                .setCourses(List.of(new CourseEntity().setId(5L)
                        .setName("Course Name")
                        .setStudents(List.of(studentEntity))
                        .setTeacher(teacherEntity)));

        doThrow(new ConstraintViolationException(null, null)).when(repository)
                .save(invalidSchoolEntity);

        assertThatThrownBy(() -> service.createSchool(invalidSchoolDto)).isInstanceOfAny(
                ConstraintViolationException.class, MappingException.class);
    }

    @Test
    void deleteSchoolShouldCallDeleteSchoolInRepository() {
        service.deleteSchool(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteSchoolWithInvalidIdShouldThrowException() {
        doThrow(new EmptyResultDataAccessException(1)).when(repository)
                .deleteById(2L);

        assertThatThrownBy(() -> service.deleteSchool(2L));
    }

    @Test
    void findSchoolByIdWithValidIdOne() {
        var result = service.findSchoolById(1L);

        assertThat(result).isEqualTo(schoolDto.setId(1L));
    }

    @Test
    void findSchoolByIdWithInvalidIdTwo() {
        assertThatThrownBy(() -> service.findSchoolById(2L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void findAllSchoolsShouldReturnAllSchools() {
        var result = service.findAllSchools();

        assertThat(result).isEqualTo(List.of(schoolDto));
    }
}
