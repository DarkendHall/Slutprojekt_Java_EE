package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.entity.CourseEntity;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.entity.TeacherEntity;
import org.darkend.slutprojekt_java_ee.repository.CourseRepository;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
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

class CourseServiceTest {

    private CourseService service;
    private CourseRepository repository;

    private final CourseDto courseDto = new CourseDto().setId(1L)
            .setName("Course Name")
            .setStudents(List.of(new StudentDto().setId(2L)
                    .setFullName("Student Name")
                    .setPhoneNumber("N/A")
                    .setEmail("email@email.com")))
            .setTeacher(new TeacherDto().setId(3L)
                    .setFullName("Teacher Name"));

    private final CourseEntity courseEntity = new CourseEntity().setId(1L)
            .setName("Course Name")
            .setStudents(List.of(new StudentEntity().setId(2L)
                    .setFirstName("Student")
                    .setLastName("Name")
                    .setPhoneNumber("N/A")
                    .setEmail("email@email.com")))
            .setTeacher(new TeacherEntity().setId(3L)
                    .setFirstName("Teacher")
                    .setLastName("Name"));

    @BeforeEach
    void setUp() {
        repository = mock(CourseRepository.class);
        ModelMapper mapper = ModelMapperConfig.getModelMapper(mock(RoleRepository.class));
        service = new CourseService(repository, mapper);
        when(repository.save(courseEntity)).thenReturn(courseEntity);
        when(repository.findById(1L)).thenReturn(Optional.of(courseEntity));
        when(repository.findById(2L)).thenReturn(Optional.empty());
        when(repository.findAll()).thenReturn(List.of(courseEntity));
    }

    @Test
    void createCourseWithValidCourseShouldReturnCreatedCourseAsDto() {
        var result = service.createCourse(courseDto);

        assertThat(result).isEqualTo(courseDto);
    }

    @Test
    void createCourseWithInvalidCourseShouldThrowException() {
        var invalidCourseDto = new CourseDto().setId(1L)
                .setName(null)
                .setStudents(List.of(new StudentDto().setId(2L)
                        .setFullName("Student Name")
                        .setPhoneNumber("N/A")
                        .setEmail("email@email.com")))
                .setTeacher(new TeacherDto().setId(3L)
                        .setFullName("Teacher"));

        var invalidCourseEntity = new CourseEntity().setId(1L)
                .setName(null)
                .setStudents(List.of(new StudentEntity().setId(2L)
                        .setFirstName("Student")
                        .setLastName("Name")
                        .setPhoneNumber("N/A")
                        .setEmail("email@email.com")))
                .setTeacher(new TeacherEntity().setId(3L)
                        .setFirstName("Teacher")
                        .setLastName("Name"));

        doThrow(new ConstraintViolationException(null, null)).when(repository)
                .save(invalidCourseEntity);

        assertThatThrownBy(() -> service.createCourse(invalidCourseDto)).isInstanceOfAny(
                ConstraintViolationException.class, MappingException.class);
    }

    @Test
    void deleteCourseShouldCallDeleteCourseInRepository() {
        service.deleteCourse(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCourseWithInvalidIdShouldThrowException() {
        doThrow(new EmptyResultDataAccessException(1)).when(repository)
                .deleteById(2L);

        assertThatThrownBy(() -> service.deleteCourse(2L)).isExactlyInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void findCourseByIdWithValidIdOne() {
        var result = service.findCourseById(1L);

        assertThat(result).isEqualTo(courseDto.setId(1L));
    }

    @Test
    void findCourseByIdWithInvalidIdTwo() {
        assertThatThrownBy(() -> service.findCourseById(2L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void findAllCoursesShouldReturnAllCourses() {
        var result = service.findAllCourses();

        assertThat(result).isEqualTo(List.of(courseDto));
    }

    @Test
    void updateStudentsShouldUpdateStudentsInCourse() {
        var course = new CourseDto().setId(1L)
                .setName("Course Name")
                .setStudents(List.of(new StudentDto().setFullName("Student Name")))
                .setTeacher(new TeacherDto().setId(3L)
                        .setFullName("Teacher Name"));

        var result = service.updateStudentsInCourse(List.of(new StudentDto().setFullName("Student Name")), 1L);

        assertThat(result).isEqualTo(course);
    }

    @Test
    void updateStudentWithInvalidIdShouldThrowException() {
        var students = List.of(new StudentDto().setFullName("Student Name"));

        assertThatThrownBy(() -> service.updateStudentsInCourse(students,
                2L)).isExactlyInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void updateTeacherShouldUpdateTeacherInCourse() {
        var course = new CourseDto().setId(1L)
                .setName("Course Name")
                .setStudents(List.of(new StudentDto().setId(2L)
                        .setFullName("Student Name")
                        .setPhoneNumber("N/A")
                        .setEmail("email@email.com")))
                .setTeacher(new TeacherDto().setFullName("Teacher Name"));

        var result = service.updateTeacherInCourse(new TeacherDto().setFullName("Teacher Name"), 1L);

        assertThat(result).isEqualTo(course);
    }

    @Test
    void updateTeacherWithInvalidIdShouldThrowException() {
        var teacher = new TeacherDto().setFullName("Teacher Name");

        assertThatThrownBy(() -> service.updateTeacherInCourse(teacher,
                2L)).isExactlyInstanceOf(
                EntityNotFoundException.class);
    }
}
